package alex.toy.nmj.presentation;

import alex.toy.nmj.common.util.JsonUtil;
import alex.toy.nmj.fixture.MemberFixture;
import alex.toy.nmj.member.application.MemberService;
import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import static alex.toy.nmj.fixture.MemberFixture.매장_회원_Alex;
import static alex.toy.nmj.fixture.MemberFixture.일반_회원_beom;
import static alex.toy.nmj.fixture.MemberFixture.일반_회원_gibeom;
import static alex.toy.nmj.fixture.MemberFixture.회원_비밀번호_비정상;
import static alex.toy.nmj.fixture.MemberFixture.회원_이름_비정상;
import static alex.toy.nmj.fixture.MemberFixture.회원_이메일_비정상;
import static alex.toy.nmj.fixture.MemberFixture.회원_전화번호_비정상;
import static alex.toy.nmj.fixture.MemberFixture.회원_타입_비정상;
import static alex.toy.nmj.fixture.MemberFixture.회원_타입_소문자;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

            @Test
            @DisplayName("회원 상태를 일반으로 저장하고 201 상태 코드와 회원 정보를 리턴한다")
            void it_returns_member() throws Exception {
                ResultActions perform = mockMvc.perform(
                        회원_등록_API_요청(일반_회원_gibeom)
                );

                perform.andExpect(status().isCreated());
                perform.andExpect(content().string(not(containsString("password"))));
                Member_이메일_이름_전화번호_회원타입_검증(perform, 일반_회원_gibeom);
                perform.andExpect(content().string(containsString(MemberStatus.NORMAL.toString())));

                perform.andDo(print());
            }
        }

        @Nested
        @DisplayName("유효한 매장 회원 정보가 주어지면")
        class Context_with_valid_store_member {

            @Test
            @DisplayName("회원 상태를 대기로 저장하고 201 상태 코드와 회원 정보를 리턴한다")
            void it_returns_member() throws Exception {
                ResultActions perform = mockMvc.perform(
                        회원_등록_API_요청(매장_회원_Alex)
                );

                perform.andExpect(status().isCreated());
                perform.andExpect(content().string(not(containsString("password"))));
                Member_이메일_이름_전화번호_회원타입_검증(perform, 매장_회원_Alex);
                perform.andExpect(content().string(containsString(MemberStatus.WAIT.toString())));

                perform.andDo(print());
            }
        }

        @Nested
        @DisplayName("유효한 회원 타입이 소문자로 주어지면")
        class Context_with_type_small_letter {

            @Test
            @DisplayName("201 상태 코드와 회원 정보를 리턴한다")
            void it_returns_member() throws Exception {
                ResultActions perform = mockMvc.perform(
                        회원_등록_API_요청(회원_타입_소문자)
                );

                perform.andExpect(status().isCreated());
                perform.andExpect(content().string(containsString(회원_타입_소문자.이메일())));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 회원 정보가 주어지면")
        class Context_with_request_null_data {

            @Nested
            @DisplayName("중복된 아이디 계정일 경우")
            class Context_with_duplicate_email {

                @BeforeEach
                void setUp() {
                    memberService.save(일반_회원_gibeom.등록_요청_DTO_생성());
                }

                @Test
                @DisplayName("409 코드로 응답한다")
                void it_responses_409() throws Exception {
                    ResultActions perform = mockMvc.perform(
                            회원_등록_API_요청(일반_회원_gibeom)
                    );

                    perform.andExpect(status().isConflict());
                }
            }

            @Nested
            @DisplayName("이메일이 공백일 경우")
            class Context_with_empty_email {

                @Test
                @DisplayName("400 코드로 응답한다")
                void it_responses_400() throws Exception {
                    ResultActions perform = mockMvc.perform(
                            회원_등록_API_요청(회원_이메일_비정상)
                    );

                    perform.andExpect(status().isBadRequest());
                }
            }

            @Nested
            @DisplayName("비밀번호가 공백일 경우")
            class Context_with_empty_password {

                @Test
                @DisplayName("400 코드로 응답한다")
                void it_responses_400() throws Exception {
                    ResultActions perform = mockMvc.perform(
                            회원_등록_API_요청(회원_비밀번호_비정상)
                    );

                    perform.andExpect(status().isBadRequest());
                }
            }

            @Nested
            @DisplayName("전화번호가 공백일 경우")
            class Context_with_empty_phone {

                @Test
                @DisplayName("400 코드로 응답한다")
                void it_responses_400() throws Exception {
                    ResultActions perform = mockMvc.perform(
                            회원_등록_API_요청(회원_전화번호_비정상)
                    );

                    perform.andExpect(status().isBadRequest());
                }
            }

            @Nested
            @DisplayName("이름이 공백일 경우")
            class Context_with_empty_name {

                @Test
                @DisplayName("400 코드로 응답한다")
                void it_responses_400() throws Exception {
                    ResultActions perform = mockMvc.perform(
                            회원_등록_API_요청(회원_이름_비정상)
                    );

                    perform.andExpect(status().isBadRequest());
                }
            }

            @Nested
            @DisplayName("회원 타입이 유효하지 않을 경우")
            class Context_with_invalid_type {

                @Test
                @DisplayName("400 코드로 응답한다")
                void it_responses_400() throws Exception {
                    ResultActions perform = mockMvc.perform(
                            회원_등록_API_요청(회원_타입_비정상)
                    );

                    perform.andExpect(status().isBadRequest());
                }
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_수정_API는 {

        @Nested
        @DisplayName("유효한 회원 수정 정보가 주어지면")
        class Context_with_valid_update_data {

            private Member 기존_회원_정보;

            @BeforeEach
            void setUp() {
                기존_회원_정보 = memberService.save(일반_회원_gibeom.등록_요청_DTO_생성());
            }

            @Test
            @DisplayName("회원 정보를 수정하고 200 상태 코드와 회원 정보를 리턴한다")
            void it_returns_member() throws Exception {
                ResultActions perform = mockMvc.perform(
                        회원_수정_API_요청(기존_회원_정보.getId(), 일반_회원_beom)
                );

                perform.andExpect(status().isOk());
                perform.andExpect(content().string(not(containsString("password"))));
                perform.andExpect(content().string(containsString(일반_회원_gibeom.이메일())));
                perform.andExpect(content().string(containsString(일반_회원_beom.이름())));
                perform.andExpect(content().string(containsString(일반_회원_beom.전화번호())));

                perform.andDo(print());
            }
        }

        @Nested
        @DisplayName("찾을 수 없는 회원 id가 주어지면")
        class Context_with_not_found_member_id {

            @Test
            @DisplayName("404 코드로 응답한다")
            void it_responses_404() throws Exception {
                ResultActions perform = mockMvc.perform(
                        회원_수정_API_요청(Long.MAX_VALUE, 일반_회원_beom)
                );

                perform.andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("회원 상태가 가입 대기일 경우")
        class Context_with_member_status_wait {

            private Member 매장_가입_대기_회원_정보;

            @BeforeEach
            void setUp() {
                매장_가입_대기_회원_정보 = memberService.save(매장_회원_Alex.등록_요청_DTO_생성());
            }

            @Test
            @DisplayName("404 코드로 응답한다")
            void it_responses_404() throws Exception {
                ResultActions perform = mockMvc.perform(
                        회원_수정_API_요청(매장_가입_대기_회원_정보.getId(), 일반_회원_beom)
                );

                perform.andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("삭제된 회원일 경우")
        class Context_with_member_status_deleted {

            private Member 삭제된_회원_정보;

            @BeforeEach
            void setUp() {
                삭제된_회원_정보 = memberService.save(일반_회원_gibeom.등록_요청_DTO_생성());

                memberService.delete(삭제된_회원_정보.getId());
            }

            @Test
            @DisplayName("404 코드로 응답한다")
            void it_responses_404() throws Exception {
                ResultActions perform = mockMvc.perform(
                        회원_수정_API_요청(삭제된_회원_정보.getId(), 일반_회원_beom)
                );

                perform.andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_삭제_API는 {

        @Nested
        @DisplayName("회원 상태가 정상인 찾을 수 있는 회원 id가 주어지면")
        class Context_with_valid_member_id {

            private Member 기존_회원_정보;

            @BeforeEach
            void setUp() {
                기존_회원_정보 = memberService.save(일반_회원_gibeom.등록_요청_DTO_생성());
            }

            @Test
            @DisplayName("204 코드로 응답한다")
            void it_responses_204() throws Exception {
                ResultActions perform = mockMvc.perform(
                        delete(REQUEST_MEMBER_URL + "/" + 기존_회원_정보.getId())
                );

                perform.andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("찾을 수 없는 회원 id가 주어지면")
        class Context_with_not_found_member_id {

            @Test
            @DisplayName("404 코드로 응답한다")
            void it_responses_404() throws Exception {
                ResultActions perform = mockMvc.perform(
                        delete(REQUEST_MEMBER_URL + "/" + Long.MAX_VALUE)
                );

                perform.andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("회원 상태가 가입 대기일 경우")
        class Context_with_member_status_wait {

            private Member 매장_가입_대기_회원_정보;

            @BeforeEach
            void setUp() {
                매장_가입_대기_회원_정보 = memberService.save(매장_회원_Alex.등록_요청_DTO_생성());
            }

            @Test
            @DisplayName("404 코드로 응답한다")
            void it_responses_404() throws Exception {
                ResultActions perform = mockMvc.perform(
                        delete(REQUEST_MEMBER_URL + "/" + 매장_가입_대기_회원_정보.getId())
                );

                perform.andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("삭제된 회원일 경우")
        class Context_with_member_status_deleted {

            private Member 삭제된_회원_정보;

            @BeforeEach
            void setUp() {
                삭제된_회원_정보 = memberService.save(일반_회원_gibeom.등록_요청_DTO_생성());

                memberService.delete(삭제된_회원_정보.getId());
            }

            @Test
            @DisplayName("404 코드로 응답한다")
            void it_responses_404() throws Exception {
                ResultActions perform = mockMvc.perform(
                        delete(REQUEST_MEMBER_URL + "/" + 삭제된_회원_정보.getId())
                );

                perform.andExpect(status().isNotFound());
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
