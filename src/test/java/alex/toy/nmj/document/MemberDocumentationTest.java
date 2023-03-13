package alex.toy.nmj.document;

import alex.toy.nmj.common.util.JsonUtil;
import alex.toy.nmj.document.support.DocumentationTest;
import alex.toy.nmj.fixture.MemberFixture;
import alex.toy.nmj.member.application.MemberCommandService;
import alex.toy.nmj.member.application.MemberQueryService;
import alex.toy.nmj.member.presentation.dto.request.MemberCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static alex.toy.nmj.acceptance.support.MemberSupporter.MEMBER_BASE_URL;
import static alex.toy.nmj.acceptance.support.MemberSupporter.MEMBER_COMMAND_URL;
import static alex.toy.nmj.fixture.MemberFixture.일반_회원_기범;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("회원 기능 문서화")
class MemberDocumentationTest extends DocumentationTest {

    @MockBean
    private MemberCommandService memberCommandService;

    @MockBean
    private MemberQueryService memberQueryService;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_등록_API는 {

        @Nested
        @DisplayName("유효한 회원 정보가 주어지면")
        class Context_with_valid_normal_member {

            @BeforeEach
            void setUp() {
                Long memberId = 1L;
                when(memberCommandService.save(any(MemberCreateRequestDto.class)))
                        .thenReturn(memberId);
                when(memberQueryService.findById(memberId))
                        .thenReturn(일반_회원_기범.회원_엔티티_생성(memberId));
            }

            @Test
            @DisplayName("201 응답 코드를 반환한다")
            void it_responses_201() throws Exception {
                ResultActions perform = 회원_등록_API_요청(일반_회원_기범);

                perform.andExpect(status().isCreated());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_수정_API는 {

        @Nested
        @DisplayName("유효한 회원 수정 정보가 주어지면")
        class Context_with_valid_update_data {

            private Long 등록된_회원_ID = 1L;

            @BeforeEach
            void setUp() {
                when(memberQueryService.findById(등록된_회원_ID))
                        .thenReturn(일반_회원_기범.회원_엔티티_생성(등록된_회원_ID));
            }

            @Test
            @DisplayName("200 응답 코드를 반환한다")
            void it_responses_200() throws Exception {
                ResultActions perform = 회원_수정_API_요청(등록된_회원_ID, 일반_회원_기범);

                perform.andExpect(status().isOk());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_삭제_API는 {

        @Nested
        @DisplayName("가입되어 있는 회원을 삭제하면")
        class Context_with_delete_registered_member {

            private Long 등록된_회원_ID = 1L;

            @Test
            @DisplayName("204 응답 코드로 응답한다")
            void it_responses_204() throws Exception {
                ResultActions perform = 회원_삭제_API_요청(등록된_회원_ID);

                perform.andExpect(status().isNoContent());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_상세_조회_API는 {

        @Nested
        @DisplayName("가입되어 있는 회원을 조회하면")
        class Context_with_select_registered_member {
            private Long 등록된_회원_ID = 1L;

            @BeforeEach
            void setUp() {
                when(memberQueryService.findById(등록된_회원_ID))
                        .thenReturn(일반_회원_기범.회원_엔티티_생성(등록된_회원_ID));
            }

            @Test
            @DisplayName("200 응답 코드로 응답한다")
            void it_responses_200() throws Exception {
                ResultActions perform = 회원_조회_API_요청(등록된_회원_ID);

                perform.andExpect(status().isOk());
            }
        }
    }


    private ResultActions 회원_등록_API_요청(MemberFixture 회원_정보) throws Exception {
        return mockMvc.perform(
                post(MEMBER_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueAsString(회원_정보.회원_등록_요청_DTO_생성()))
        );
    }

    private ResultActions 회원_수정_API_요청(Long 회원_ID, MemberFixture 회원_정보) throws Exception {
        return mockMvc.perform(
                patch(MEMBER_COMMAND_URL, 회원_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueAsString(회원_정보.회원_수정_요청_DTO_생성()))
        );
    }

    private ResultActions 회원_삭제_API_요청(Long 회원_ID) throws Exception {
        return mockMvc.perform(
                delete(MEMBER_COMMAND_URL, 회원_ID)
        );
    }

    private ResultActions 회원_조회_API_요청(Long 회원_ID) throws Exception {
        return mockMvc.perform(
                get(MEMBER_COMMAND_URL, 회원_ID)
        );
    }
}
