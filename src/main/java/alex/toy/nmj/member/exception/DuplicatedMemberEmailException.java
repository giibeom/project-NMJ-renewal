package alex.toy.nmj.member.exception;

/**
 * 회원의 이메일이 중복될 때 던지는 예외입니다. <br>
 * 기본적으로 RuntimeException을 상속받기에, 예외 발생 시 roll-back을 수행합니다.
 */
public class DuplicatedMemberEmailException extends RuntimeException {
    private static final String MESSAGE = "회원의 아이디가 중복됩니다.";

    public DuplicatedMemberEmailException() {
        super(MESSAGE);
    }
}
