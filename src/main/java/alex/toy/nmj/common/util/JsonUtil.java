package alex.toy.nmj.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 주어진 JSON 콘텐츠 문자열에서 JSON 콘텐츠를 역직렬화하여 객체에 담아 리턴합니다.
     *
     * @param json      역직렬화 할 JSON 문자열
     * @param classType 역직렬화 한 문자열을 담을 객체 타입
     * @param <T>       element의 타입
     * @return Json 문자열을 역직렬화하여 객체로 리턴
     * @throws JsonProcessingException JSON 콘텐츠 문자열을 파싱, 생성할 때 문제가 발생할 경우
     */
    public static <T> T readValue(final String json, Class<T> classType) throws JsonProcessingException {
        return objectMapper.readValue(json, classType);
    }

    /**
     * 객체의 값을 문자열로 직렬화하여 리턴합니다,
     *
     * @param object 직렬화 할 객체
     * @param <T>    element의 타입
     * @return 객체의 값을 문자열로 직렬화 하여 리턴
     * @throws IOException 새로운 출력 스트림을 생성할 때 문제가 발생할 경우
     */
    public static <T> String writeValueAsString(final T object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }
}
