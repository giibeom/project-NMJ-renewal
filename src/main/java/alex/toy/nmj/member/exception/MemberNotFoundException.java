package alex.toy.nmj.member.exception;

/**
 * 회원을 찾지 못했을 때 던지는 예외입니다. <br>
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class MemberNotFoundException extends RuntimeException {
    private static final String MESSAGE = "회원이 존재하지 않습니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
