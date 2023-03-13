package alex.toy.nmj.acceptance.support;

import alex.toy.nmj.fixture.MemberFixture;
import alex.toy.nmj.member.domain.MemberStatus;
import alex.toy.nmj.member.domain.MemberType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static alex.toy.nmj.fixture.FieldFixture.사용자_비밀번호;
import static alex.toy.nmj.fixture.FieldFixture.사용자_상태;
import static alex.toy.nmj.fixture.FieldFixture.사용자_이름;
import static alex.toy.nmj.fixture.FieldFixture.사용자_이메일;
import static alex.toy.nmj.fixture.FieldFixture.사용자_전화번호;
import static alex.toy.nmj.fixture.FieldFixture.사용자_타입;
import static alex.toy.nmj.util.JsonPathUtil.문자열로_추출;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MemberSupporter {

    public static final String MEMBER_BASE_URL = "/api/members";
    public static final String MEMBER_COMMAND_URL = "/api/members/{memberId}";

    // =========== 요청 메서드 ===========
    public static ExtractableResponse<Response> 회원_등록_요청(MemberFixture 회원_정보) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(회원_정보.회원_등록_요청_데이터_생성())
                .when().post(MEMBER_BASE_URL)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원_상세조회_요청(Long 회원_id) {
        return given().log().all()
                .when().get(MEMBER_COMMAND_URL, 회원_id)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원_수정_요청(Long 수정할_회원_id, MemberFixture 회원_정보) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(회원_정보.회원_수정_요청_데이터_생성())
                .when().patch(MEMBER_COMMAND_URL, 수정할_회원_id)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원_삭제_요청(Long 삭제할_회원_id) {
        return given().log().all()
                .when().delete(MEMBER_COMMAND_URL, 삭제할_회원_id)
                .then().log().all().extract();
    }


    // =========== 겸증 메서드 ===========
    public static void 회원_정보가_조회된다(ExtractableResponse<Response> 회원_상세조회_요청, MemberFixture 회원_정보) {
        assertAll(
                () -> assertThat(문자열로_추출(회원_상세조회_요청, 사용자_비밀번호)).isNull(),
                () -> assertThat(문자열로_추출(회원_상세조회_요청, 사용자_이메일)).isEqualTo(회원_정보.이메일()),
                () -> assertThat(문자열로_추출(회원_상세조회_요청, 사용자_이름)).isEqualTo(회원_정보.이름()),
                () -> assertThat(문자열로_추출(회원_상세조회_요청, 사용자_전화번호)).isEqualTo(회원_정보.전화번호())
        );
    }

    public static void 정상_상태인_일반_회원으로_조회된다(ExtractableResponse<Response> 회원_상세조회_요청) {
        assertAll(
                () -> assertThat(문자열로_추출(회원_상세조회_요청, 사용자_타입)).isEqualTo(MemberType.USER.name()),
                () -> assertThat(문자열로_추출(회원_상세조회_요청, 사용자_상태)).isEqualTo(MemberStatus.NORMAL.name())
        );
    }

    public static void 가입대기_상태인_매장_회원으로_조회된다(ExtractableResponse<Response> 회원_상세조회_요청) {
        assertAll(
                () -> assertThat(문자열로_추출(회원_상세조회_요청, 사용자_타입)).isEqualTo(MemberType.STORE.name()),
                () -> assertThat(문자열로_추출(회원_상세조회_요청, 사용자_상태)).isEqualTo(MemberStatus.WAIT.name())
        );
    }
}
