package alex.toy.nmj.util;

import alex.toy.nmj.fixture.FieldFixture;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;

import static alex.toy.nmj.fixture.FieldFixture.식별자_아이디;

public class JsonPathUtil {

    public static Long Long로_추출(ExtractableResponse<Response> API_응답_결과, FieldFixture 추출할_데이터_필드) {
        return API_응답_결과.jsonPath().getLong(추출할_데이터_필드.필드명());
    }

    public static Long 식별자_ID_추출(ExtractableResponse<Response> API_응답_결과) {
        return Long로_추출(API_응답_결과, 식별자_아이디);
    }

    public static Integer Integer로_추출(ExtractableResponse<Response> API_응답_결과, FieldFixture 추출할_데이터_필드) {
        return API_응답_결과.jsonPath().getInt(추출할_데이터_필드.필드명());
    }

    public static String 문자열로_추출(ExtractableResponse<Response> API_응답_결과, FieldFixture 추출할_데이터_필드) {
        return API_응답_결과.jsonPath().getString(추출할_데이터_필드.필드명());
    }

    public static List<Object> List로_추출(ExtractableResponse<Response> API_응답_결과, FieldFixture 추출할_데이터_필드) {
        return API_응답_결과.jsonPath().getList(추출할_데이터_필드.필드명());
    }
}
