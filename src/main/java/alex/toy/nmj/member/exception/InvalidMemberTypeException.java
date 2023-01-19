package alex.toy.nmj.member.exception;

public class InvalidMemberTypeException extends RuntimeException {
    private static final String MESSAGE = "회원 타입이 유효하지 않습니다.";

    public InvalidMemberTypeException() {
        super(MESSAGE);
    }
}
