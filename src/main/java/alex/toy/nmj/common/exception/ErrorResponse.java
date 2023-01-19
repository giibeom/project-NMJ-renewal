package alex.toy.nmj.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * 에러의 응답 body를 공통 포맷에 맞게 변경하여 리턴합니다.
 */
@Getter
@Slf4j
public class ErrorResponse {
    private final String message;

    @Builder
    public ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse from(final MethodArgumentNotValidException exception) {
        return ErrorResponse.builder()
                .message(exception.getBindingResult().getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()).toString()
                )
                .build();
    }

    public static <T extends Exception> ErrorResponse from(final T exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }

    public static ErrorResponse from(final HttpServletRequest request,
                                     final Exception exception) {
        log.error("[ERROR-]\t{}\t{}\t{}", request.getMethod(), request.getRequestURI(), exception.getMessage());
        log.error("{}", (Object) exception.getStackTrace());

        return ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }
}
