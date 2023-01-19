package alex.toy.nmj.member.exception;

import alex.toy.nmj.common.exception.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Member 도메인 관련 예외를 핸들링합니다.
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MemberExceptionHandler {

    /**
     * 회원 이메일이 중복될 때 던지는 예외를 핸들링합니다.
     *
     * @param exception 회원 이메일이 중복일 경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicatedMemberEmailException.class)
    public static ErrorResponse handleUserEmailIsAlreadyExisted(final DuplicatedMemberEmailException exception) {
        return ErrorResponse.from(exception);
    }
}
