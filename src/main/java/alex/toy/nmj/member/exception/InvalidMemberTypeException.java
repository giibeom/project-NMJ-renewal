package alex.toy.nmj.member.exception;

/**
 * 회원 타입이 유효하지 않을 때 던지는 예외입니다. <br>
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class InvalidMemberTypeException extends RuntimeException {
    private static final String MESSAGE = "회원 타입이 유효하지 않습니다.";

    public InvalidMemberTypeException() {
        super(MESSAGE);
    }
}
