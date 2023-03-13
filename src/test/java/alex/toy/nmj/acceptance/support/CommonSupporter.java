package alex.toy.nmj.acceptance.support;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonSupporter {

    public static void 등록에_성공한다(ExtractableResponse<Response> 요청_결과) {
        assertThat(요청_결과.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 수정에_성공한다(ExtractableResponse<Response> 요청_결과) {
        assertThat(요청_결과.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 삭제에_성공한다(ExtractableResponse<Response> 요청_결과) {
        assertThat(요청_결과.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 조회에_성공한다(ExtractableResponse<Response> 요청_결과) {
        assertThat(요청_결과.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 비즈니스_정책으로_인해_요청에_실패한다(ExtractableResponse<Response> 요청_결과) {
        assertThat(요청_결과.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    public static void 잘못된_요청으로_인해_요청에_실패한다(ExtractableResponse<Response> 요청_결과) {
        assertThat(요청_결과.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public static void 찾을_수_없는_정보로_인해_요청에_실패한다(ExtractableResponse<Response> 요청_결과) {
        assertThat(요청_결과.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
