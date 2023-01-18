package alex.toy.nmj.presentation;

import alex.toy.nmj.common.util.JsonUtil;
import alex.toy.nmj.fixture.MemberFixture;
import alex.toy.nmj.member.application.MemberService;
import alex.toy.nmj.member.domain.MemberStatus;
import alex.toy.nmj.member.presentation.dto.request.MemberCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static alex.toy.nmj.fixture.MemberFixture.매장_회원_Alex;
import static alex.toy.nmj.fixture.MemberFixture.일반_회원_giibeom;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MemberController 테스트")
class MemberControllerTest extends PresentationTest {

    @Autowired
    private MemberService memberService;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_등록_API는 {

            @Nested
            @DisplayName("유효한 일반 회원 정보가 주어지면")
            class Context_with_valid_normal_member {

                private MemberCreateRequestDto 유효한_일반_회원_멤버_정보;

                @BeforeEach
                void setUp() {
                    유효한_일반_회원_멤버_정보 = 일반_회원_giibeom.등록_요청_데이터_생성();
                }

                @Test
                @DisplayName("회원 상태를 일반으로 저장하고 201 상태 코드와 회원 정보를 리턴한다")
                void it_returns_member() throws Exception {
                    ResultActions perform = mockMvc.perform(
                            post(REQUEST_MEMBER_URL)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(JsonUtil.writeValueAsString(유효한_일반_회원_멤버_정보))
                    );

                    perform.andExpect(status().isCreated());
                    perform.andExpect(content().string(not(containsString("password"))));
                    Member_이메일_이름_전화번호_회원타입_검증(perform, 일반_회원_giibeom);
                    perform.andExpect(content().string(containsString(MemberStatus.NORMAL.toString())));

                    perform.andDo(print());
                }
            }

            @Nested
            @DisplayName("유효한 매장 회원 정보가 주어지면")
            class Context_with_valid_store_member {

                private MemberCreateRequestDto 유효한_매장_회원_멤버_정보;

                @BeforeEach
                void setUp() {
                    유효한_매장_회원_멤버_정보 = 매장_회원_Alex.등록_요청_데이터_생성();
                }

                @Test
                @DisplayName("회원 상태를 대기로 저장하고 201 상태 코드와 회원 정보를 리턴한다")
                void it_returns_member() throws Exception {
                    ResultActions perform = mockMvc.perform(
                            post(REQUEST_MEMBER_URL)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(JsonUtil.writeValueAsString(유효한_매장_회원_멤버_정보))
                    );

                    perform.andExpect(status().isCreated());
                    perform.andExpect(content().string(not(containsString("password"))));
                    Member_이메일_이름_전화번호_회원타입_검증(perform, 매장_회원_Alex);
                    perform.andExpect(content().string(containsString(MemberStatus.WAIT.toString())));

                    perform.andDo(print());
                }
            }

            @Nested
            @DisplayName("중복된 아이디 계정이 주어지면")
            class Context_with_duplicate_email {

                @BeforeEach
                void setUp() {
                    memberService.saveMember(일반_회원_giibeom.등록_요청_데이터_생성());
                }

                @Test
                @DisplayName("409 코드로 응답한다")
                void it_responses_409() throws Exception {
                    ResultActions perform = mockMvc.perform(
                            post(REQUEST_MEMBER_URL)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(JsonUtil.writeValueAsString(일반_회원_giibeom.등록_요청_데이터_생성()))
                    );

                    perform.andExpect(status().isConflict());
                }
            }
    }

    private void Member_이메일_이름_전화번호_회원타입_검증(ResultActions perform, MemberFixture memberFixture) throws Exception {
        perform.andExpect(content().string(containsString(memberFixture.이메일())));
        perform.andExpect(content().string(containsString(memberFixture.이름())));
        perform.andExpect(content().string(containsString(memberFixture.전화번호())));
        perform.andExpect(content().string(containsString(memberFixture.회원타입().toString())));
    }
}
