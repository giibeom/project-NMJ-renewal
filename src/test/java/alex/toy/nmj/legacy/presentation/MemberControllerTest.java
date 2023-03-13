package alex.toy.nmj.legacy.presentation;

import alex.toy.nmj.common.util.JsonUtil;
import alex.toy.nmj.legacy.fixture.MemberFixture;
import alex.toy.nmj.member.application.MemberCommandService;
import alex.toy.nmj.member.domain.MemberStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MemberController 테스트")
class MemberControllerTest extends PresentationTest {

    @Autowired
    private MemberCommandService memberCommandService;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_등록_API는 {

        @Nested
        @DisplayName("유효한 일반 회원 정보가 주어지면")
        class Context_with_valid_normal_member {

            @Test
            @DisplayName("회원 상태를 일반으로 저장하고 201 상태 코드와 회원 정보를 리턴한다")
            void it_returns_member() throws Exception {
                ResultActions perform = mockMvc.perform(
                        회원_등록_API_요청(MemberFixture.일반_회원_gibeom)
                );

                perform.andExpect(status().isCreated())
                        .andExpect(content().string(not(containsString("password"))))
                        .andExpect(content().string(containsString(MemberStatus.NORMAL.toString())));
            }
        }

        @Nested
        @DisplayName("유효한 매장 회원 정보가 주어지면")
        class Context_with_valid_store_member {

            @Test
            @DisplayName("회원 상태를 대기로 저장하고 201 상태 코드와 회원 정보를 리턴한다")
            void it_returns_member() throws Exception {
                ResultActions perform = mockMvc.perform(
                        회원_등록_API_요청(MemberFixture.매장_회원_Alex)
                );

                perform.andExpect(status().isCreated())
                        .andExpect(content().string(not(containsString("password"))))
                        .andExpect(content().string(containsString(MemberStatus.WAIT.toString())));
            }
        }

        @Nested
        @DisplayName("유효한 회원 타입이 소문자로 주어지면")
        class Context_with_type_small_letter {

            @Test
            @DisplayName("201 상태 코드와 회원 정보를 리턴한다")
            void it_returns_member() throws Exception {
                ResultActions perform = mockMvc.perform(
                        회원_등록_API_요청(MemberFixture.회원_타입_소문자)
                );

                perform.andExpect(status().isCreated());
                perform.andExpect(content().string(containsString(MemberFixture.회원_타입_소문자.이메일())));
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_수정_API는 {

        @Nested
        @DisplayName("유효한 회원 수정 정보가 주어지면")
        class Context_with_valid_update_data {

            private Long 기존_회원_정보;

            @BeforeEach
            void setUp() {
                기존_회원_정보 = memberCommandService.save(MemberFixture.일반_회원_gibeom.등록_요청_DTO_생성());
            }

            @Test
            @DisplayName("회원 정보를 수정하고 200 상태 코드와 회원 정보를 리턴한다")
            void it_returns_member() throws Exception {
                ResultActions perform = mockMvc.perform(
                        회원_수정_API_요청(기존_회원_정보, MemberFixture.일반_회원_beom)
                );

                perform.andExpect(status().isOk());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_삭제_API는 {

        @Nested
        @DisplayName("회원 상태가 정상인 찾을 수 있는 회원 id가 주어지면")
        class Context_with_valid_member_id {

            private Long 기존_회원_정보;

            @BeforeEach
            void setUp() {
                기존_회원_정보 = memberCommandService.save(MemberFixture.일반_회원_gibeom.등록_요청_DTO_생성());
            }

            @Test
            @DisplayName("204 코드로 응답한다")
            void it_responses_204() throws Exception {
                ResultActions perform = mockMvc.perform(
                        delete(REQUEST_MEMBER_URL + "/" + 기존_회원_정보)
                );

                perform.andExpect(status().isNoContent());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_상세_조회_API는 {

        @Nested
        @DisplayName("찾을 수 있는 id가 주어지면")
        class Context_with_valid_id {

            private Long 기존_회원_정보;

            @BeforeEach
            void setUp() {
                기존_회원_정보 = memberCommandService.save(MemberFixture.일반_회원_gibeom.등록_요청_DTO_생성());
            }

            @Test
            @DisplayName("200 상태 코드와 회원 정보를 리턴한다")
            void it_returns_member() throws Exception {
                ResultActions perform = mockMvc.perform(
                        get(REQUEST_MEMBER_URL + "/" + 기존_회원_정보)
                );

                perform.andExpect(status().isOk())
                        .andExpect(content().string(not(containsString("password"))));
                Member_이메일_이름_전화번호_회원타입_검증(perform, MemberFixture.일반_회원_gibeom);
            }
        }
    }


    private MockHttpServletRequestBuilder 회원_등록_API_요청(MemberFixture memberFixture) throws IOException {
        return post(REQUEST_MEMBER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValueAsString(memberFixture.등록_요청_데이터_생성()));
    }

    private MockHttpServletRequestBuilder 회원_수정_API_요청(Long memberId, MemberFixture memberFixture) throws IOException {
        return patch(REQUEST_MEMBER_URL + "/" + memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValueAsString(memberFixture.수정_요청_데이터_생성()));
    }

    private void Member_이메일_이름_전화번호_회원타입_검증(ResultActions perform, MemberFixture memberFixture) throws Exception {
        perform.andExpect(content().string(containsString(memberFixture.이메일())));
        perform.andExpect(content().string(containsString(memberFixture.이름())));
        perform.andExpect(content().string(containsString(memberFixture.전화번호())));
        perform.andExpect(content().string(containsString(memberFixture.회원타입())));
    }
}
