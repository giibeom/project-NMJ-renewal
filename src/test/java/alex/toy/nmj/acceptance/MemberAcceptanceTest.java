package alex.toy.nmj.acceptance;

import alex.toy.nmj.acceptance.support.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static alex.toy.nmj.acceptance.support.CommonSupporter.등록에_성공한다;
import static alex.toy.nmj.acceptance.support.CommonSupporter.비즈니스_정책으로_인해_요청에_실패한다;
import static alex.toy.nmj.acceptance.support.CommonSupporter.삭제에_성공한다;
import static alex.toy.nmj.acceptance.support.CommonSupporter.수정에_성공한다;
import static alex.toy.nmj.acceptance.support.CommonSupporter.잘못된_요청으로_인해_요청에_실패한다;
import static alex.toy.nmj.acceptance.support.CommonSupporter.조회에_성공한다;
import static alex.toy.nmj.acceptance.support.CommonSupporter.찾을_수_없는_정보로_인해_요청에_실패한다;
import static alex.toy.nmj.acceptance.support.MemberSupporter.가입대기_상태인_매장_회원으로_조회된다;
import static alex.toy.nmj.acceptance.support.MemberSupporter.정상_상태인_일반_회원으로_조회된다;
import static alex.toy.nmj.acceptance.support.MemberSupporter.회원_등록_요청;
import static alex.toy.nmj.acceptance.support.MemberSupporter.회원_삭제_요청;
import static alex.toy.nmj.acceptance.support.MemberSupporter.회원_상세조회_요청;
import static alex.toy.nmj.acceptance.support.MemberSupporter.회원_수정_요청;
import static alex.toy.nmj.acceptance.support.MemberSupporter.회원_정보가_조회된다;
import static alex.toy.nmj.fixture.MemberFixture.매장_회원_Alex;
import static alex.toy.nmj.fixture.MemberFixture.매장_회원_Alex에서_이름과_전화번호_수정;
import static alex.toy.nmj.fixture.MemberFixture.일반_회원_기범;
import static alex.toy.nmj.fixture.MemberFixture.일반_회원_기범에서_이름과_전화번호_수정;
import static alex.toy.nmj.fixture.MemberFixture.회원_비밀번호_비정상;
import static alex.toy.nmj.fixture.MemberFixture.회원_이름_비정상;
import static alex.toy.nmj.fixture.MemberFixture.회원_이메일_비정상;
import static alex.toy.nmj.fixture.MemberFixture.회원_전화번호_비정상;
import static alex.toy.nmj.fixture.MemberFixture.회원_타입_비정상;
import static alex.toy.nmj.util.JsonPathUtil.식별자_ID_추출;

@DisplayName("회원 기능 인수 테스트")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_등록_시 {

        @Nested
        @DisplayName("유효한 일반 회원 정보로 회원을 등록하면")
        class Context_with_valid_create_data_of_normal_member {

            @Test
            @DisplayName("회원 상세조회 시 가입 완료된 일반 회원 정보가 조회된다")
            void it_returns_normal_member_info() {
                ExtractableResponse<Response> 회원_등록_결과 = 회원_등록_요청(일반_회원_기범);

                등록에_성공한다(회원_등록_결과);

                ExtractableResponse<Response> 회원_상세조회_결과 = 회원_상세조회_요청(식별자_ID_추출(회원_등록_결과));
                정상_상태인_일반_회원으로_조회된다(회원_상세조회_결과);
                회원_정보가_조회된다(회원_상세조회_결과, 일반_회원_기범);
            }
        }

        @Nested
        @DisplayName("유효한 매장 회원 정보로 회원을 등록하면")
        class Context_with_valid_create_data_of_store_member {

            @Test
            @DisplayName("회원 상세조회 시 가입대기 상태의 매장 회원 정보가 조회된다")
            void it_returns_wait_store_member_info() {
                ExtractableResponse<Response> 회원_등록_결과 = 회원_등록_요청(매장_회원_Alex);

                등록에_성공한다(회원_등록_결과);

                ExtractableResponse<Response> 회원_상세조회_결과 = 회원_상세조회_요청(식별자_ID_추출(회원_등록_결과));
                가입대기_상태인_매장_회원으로_조회된다(회원_상세조회_결과);
                회원_정보가_조회된다(회원_상세조회_결과, 매장_회원_Alex);
            }
        }

        @Nested
        @DisplayName("이미 가입된 이메일 계정으로 회원을 등록하면")
        class Context_with_duplicate_email {

            @BeforeEach
            void setUp() {
                회원_등록_요청(일반_회원_기범);
            }

            @Test
            @DisplayName("비즈니스 정책으로 인해 회원 등록에 실패한다")
            void it_responses_409() {
                ExtractableResponse<Response> 회원_등록_결과 = 회원_등록_요청(일반_회원_기범);

                비즈니스_정책으로_인해_요청에_실패한다(회원_등록_결과);
            }
        }

        @Nested
        @DisplayName("이메일을 공백으로 회원을 등록하면")
        class Context_with_empty_email {

            @Test
            @DisplayName("잘못된 요청으로 인해 회원 등록에 실패한다")
            void it_responses_400() {
                ExtractableResponse<Response> 회원_등록_결과 = 회원_등록_요청(회원_이메일_비정상);

                잘못된_요청으로_인해_요청에_실패한다(회원_등록_결과);
            }
        }

        @Nested
        @DisplayName("비밀번호를 공백으로 회원을 등록하면")
        class Context_with_empty_password {

            @Test
            @DisplayName("잘못된 요청으로 인해 회원 등록에 실패한다")
            void it_responses_400() {
                ExtractableResponse<Response> 회원_등록_결과 = 회원_등록_요청(회원_비밀번호_비정상);

                잘못된_요청으로_인해_요청에_실패한다(회원_등록_결과);
            }
        }

        @Nested
        @DisplayName("전화번호를 공백으로 회원을 등록하면")
        class Context_with_empty_phone {

            @Test
            @DisplayName("잘못된 요청으로 인해 회원 등록에 실패한다")
            void it_responses_400() {
                ExtractableResponse<Response> 회원_등록_결과 = 회원_등록_요청(회원_전화번호_비정상);

                잘못된_요청으로_인해_요청에_실패한다(회원_등록_결과);
            }
        }

        @Nested
        @DisplayName("이름을 공백으로 회원을 등록하면")
        class Context_with_empty_name {

            @Test
            @DisplayName("잘못된 요청으로 인해 회원 등록에 실패한다")
            void it_responses_400() {
                ExtractableResponse<Response> 회원_등록_결과 = 회원_등록_요청(회원_이름_비정상);

                잘못된_요청으로_인해_요청에_실패한다(회원_등록_결과);
            }
        }

        @Nested
        @DisplayName("유효하지 않은 회원 타입으로 회원을 등록하면")
        class Context_with_invalid_type {

            @Test
            @DisplayName("잘못된 요청으로 인해 회원 등록에 실패한다")
            void it_responses_400() {
                ExtractableResponse<Response> 회원_등록_결과 = 회원_등록_요청(회원_타입_비정상);

                잘못된_요청으로_인해_요청에_실패한다(회원_등록_결과);
            }
        }
    }


    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_수정_시 {

        private Long 등록된_회원_ID;

        @BeforeEach
        void setUp() {
            등록된_회원_ID = 식별자_ID_추출(회원_등록_요청(일반_회원_기범));
        }

        @Nested
        @DisplayName("유효한 회원 수정 정보로 가입 완료된 회원을 수정하면")
        class Context_with_valid_update_data_of_normal_member {

            @Test
            @DisplayName("회원 상세조회 시 수정된 회원 정보가 조회된다")
            void it_returns_updated_member_info() {
                ExtractableResponse<Response> 회원_수정_결과 = 회원_수정_요청(등록된_회원_ID, 일반_회원_기범에서_이름과_전화번호_수정);

                수정에_성공한다(회원_수정_결과);

                ExtractableResponse<Response> 회원_상세조회_결과 = 회원_상세조회_요청(등록된_회원_ID);
                회원_정보가_조회된다(회원_상세조회_결과, 일반_회원_기범에서_이름과_전화번호_수정);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 회원을 수정하면")
        class Context_with_not_registered_member_id {

            @Test
            @DisplayName("찾을 수 없는 정보로 인해 회원 수정에 실패한다")
            void it_responses_404() {
                ExtractableResponse<Response> 회원_수정_결과 = 회원_수정_요청(Long.MAX_VALUE, 일반_회원_기범에서_이름과_전화번호_수정);

                찾을_수_없는_정보로_인해_요청에_실패한다(회원_수정_결과);
            }
        }

        @Nested
        @DisplayName("가입 대기 상태인 회원을 수정하면")
        class Context_with_member_status_wait {

            private Long 가입대기_상태인_매장_회원_ID;

            @BeforeEach
            void setUp() {
                가입대기_상태인_매장_회원_ID = 식별자_ID_추출(회원_등록_요청(매장_회원_Alex));
            }

            @Test
            @DisplayName("찾을 수 없는 정보로 인해 회원 수정에 실패한다")
            void it_responses_404() {
                ExtractableResponse<Response> 회원_수정_결과 = 회원_수정_요청(가입대기_상태인_매장_회원_ID, 매장_회원_Alex에서_이름과_전화번호_수정);

                찾을_수_없는_정보로_인해_요청에_실패한다(회원_수정_결과);
            }
        }

        @Nested
        @DisplayName("삭제 처리된 회원을 수정하면")
        class Context_with_member_status_deleted {

            @BeforeEach
            void setUp() {
                회원_삭제_요청(등록된_회원_ID);
            }

            @Test
            @DisplayName("찾을 수 없는 정보로 인해 회원 수정에 실패한다")
            void it_responses_404() {
                ExtractableResponse<Response> 회원_수정_요청 = 회원_수정_요청(등록된_회원_ID, 매장_회원_Alex에서_이름과_전화번호_수정);

                찾을_수_없는_정보로_인해_요청에_실패한다(회원_수정_요청);
            }
        }
    }


    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_삭제_시 {

        private Long 등록된_회원_ID;

        @BeforeEach
        void setUp() {
            등록된_회원_ID = 식별자_ID_추출(회원_등록_요청(일반_회원_기범));
        }

        @Nested
        @DisplayName("가입되어 있는 회원을 삭제하면")
        class Context_with_delete_registered_member {

            @Test
            @DisplayName("회원 삭제에 성공하고 회원 상세 조회 시 정보를 찾을 수 없다")
            void it_success_delete() {
                ExtractableResponse<Response> 회원_삭제_결과 = 회원_삭제_요청(등록된_회원_ID);

                삭제에_성공한다(회원_삭제_결과);

                ExtractableResponse<Response> 회원_상세조회_결과 = 회원_상세조회_요청(등록된_회원_ID);
                찾을_수_없는_정보로_인해_요청에_실패한다(회원_상세조회_결과);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 회원을 삭제하면")
        class Context_with_not_registered_member_id {

            @Test
            @DisplayName("찾을 수 없는 정보로 인해 회원 삭제에 실패한다")
            void it_responses_404() {
                ExtractableResponse<Response> 회원_삭제_결과 = 회원_삭제_요청(Long.MAX_VALUE);

                찾을_수_없는_정보로_인해_요청에_실패한다(회원_삭제_결과);
            }
        }

        @Nested
        @DisplayName("가입 대기 상태인 회원을 삭제하면")
        class Context_with_member_status_wait {

            private Long 가입대기_상태인_매장_회원_ID;

            @BeforeEach
            void setUp() {
                가입대기_상태인_매장_회원_ID = 식별자_ID_추출(회원_등록_요청(매장_회원_Alex));
            }

            @Test
            @DisplayName("찾을 수 없는 정보로 인해 회원 삭제에 실패한다")
            void it_responses_404() {
                ExtractableResponse<Response> 회원_삭제_결과 = 회원_삭제_요청(가입대기_상태인_매장_회원_ID);

                찾을_수_없는_정보로_인해_요청에_실패한다(회원_삭제_결과);
            }
        }

        @Nested
        @DisplayName("삭제 처리된 회원을 삭제하면")
        class Context_with_member_status_deleted {

            @BeforeEach
            void setUp() {
                회원_삭제_요청(등록된_회원_ID);
            }

            @Test
            @DisplayName("찾을 수 없는 정보로 인해 회원 삭제에 실패한다")
            void it_responses_404() {
                ExtractableResponse<Response> 회원_삭제_결과 = 회원_삭제_요청(등록된_회원_ID);

                찾을_수_없는_정보로_인해_요청에_실패한다(회원_삭제_결과);
            }
        }
    }


    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_상세조회_시 {

        private Long 등록된_회원_ID;

        @BeforeEach
        void setUp() {
            등록된_회원_ID = 식별자_ID_추출(회원_등록_요청(일반_회원_기범));
        }

        @Nested
        @DisplayName("가입되어 있는 회원을 조회하면")
        class Context_with_select_registered_member {

            @Test
            @DisplayName("회원의 상세 정보가 조회된다")
            void it_returns_member_info() {
                ExtractableResponse<Response> 회원_상세조회_결과 = 회원_상세조회_요청(등록된_회원_ID);

                조회에_성공한다(회원_상세조회_결과);
                회원_정보가_조회된다(회원_상세조회_결과, 일반_회원_기범);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 회원을 삭제하면")
        class Context_with_not_registered_member_id {

            @Test
            @DisplayName("찾을 수 없는 정보로 인해 회원 삭제에 실패한다")
            void it_responses_404() {
                ExtractableResponse<Response> 회원_상세조회_결과 = 회원_상세조회_요청(Long.MAX_VALUE);

                찾을_수_없는_정보로_인해_요청에_실패한다(회원_상세조회_결과);
            }
        }

        @Nested
        @DisplayName("가입 대기 상태인 회원을 조회하면")
        class Context_with_member_status_wait {

            private Long 가입대기_상태인_매장_회원_ID;

            @BeforeEach
            void setUp() {
                가입대기_상태인_매장_회원_ID = 식별자_ID_추출(회원_등록_요청(매장_회원_Alex));
            }

            @Test
            @DisplayName("회원의 상세 정보가 조회된다")
            void it_returns_member_info() {
                ExtractableResponse<Response> 회원_상세조회_결과 = 회원_상세조회_요청(가입대기_상태인_매장_회원_ID);

                조회에_성공한다(회원_상세조회_결과);
                회원_정보가_조회된다(회원_상세조회_결과, 매장_회원_Alex);
            }
        }

        @Nested
        @DisplayName("삭제 처리된 회원을 조회하면")
        class Context_with_member_status_deleted {

            @BeforeEach
            void setUp() {
                회원_삭제_요청(등록된_회원_ID);
            }

            @Test
            @DisplayName("찾을 수 없는 정보로 인해 회원 조회에 실패한다")
            void it_responses_404() {
                ExtractableResponse<Response> 회원_상세조회_결과 = 회원_상세조회_요청(등록된_회원_ID);

                찾을_수_없는_정보로_인해_요청에_실패한다(회원_상세조회_결과);
            }
        }
    }
}
