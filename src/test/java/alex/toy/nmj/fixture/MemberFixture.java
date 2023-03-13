package alex.toy.nmj.fixture;

import java.util.HashMap;
import java.util.Map;

import static alex.toy.nmj.fixture.FieldFixture.*;

public enum MemberFixture {
    // ----- 정상 값 -----
    일반_회원_기범("dev.gibeom@gmail.com", "범쓰비밀번호77", "giibeom", "010-7777-7777", "USER"),
    일반_회원_기범에서_이름과_전화번호_수정("dev.gibeom@gmail.com", "범쓰비밀번호77", "철수", "010-1111-1111", "USER"),
    매장_회원_Alex("kpmyung@gmail.com", "매장486", "Alex", "010-1234-5678", "STORE"),
    매장_회원_Alex에서_이름과_전화번호_수정("kpmyung@gmail.com", "매장486", "맹구", "010-0000-0000", "STORE"),
    관리자_회원_짱구("admin@gmail.com", "관리자486", "짱구관리자", "010-0707-0707", "ADMIN"),
    회원_타입_소문자("small.type@nmj.com", "이메일공백", "이메일공백", "010-4444-4444", "user"),

    // ----- 비정상 값 -----
    회원_이메일_비정상("", "이메일공백", "이메일공백", "010-4444-4444", "USER"),
    회원_비밀번호_비정상("invalid.psw@nmj.com", "", "비번공백", "010-4444-4444", "USER"),
    회원_전화번호_비정상("invalid.phone@nmj.com", "번호공백", "번호공백", "", "USER"),
    회원_이름_비정상("invalid.name@nmj.com", "이름공백", "", "010-4444-4444", "USER"),
    회원_타입_비정상("invalid.type@nmj.com", "타입이상", "타입이상", "010-4444-4444", "우웩"),
    ;

    private final String email;
    private final String password;
    private final String name;
    private final String phone;
    private final String type;

    MemberFixture(String email, String password, String name, String phone, String type) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.type = type;
    }

    public String 이메일() {
        return email;
    }

    public String 비밀번호() {
        return password;
    }

    public String 이름() {
        return name;
    }

    public String 전화번호() {
        return phone;
    }

    public String 회원타입() {
        return type;
    }


    public Map<String, String> 회원_등록_요청_데이터_생성() {
        Map<String, String> params = new HashMap<>();
        params.put(사용자_이메일.필드명(), 이메일());
        params.put(사용자_비밀번호.필드명(), 비밀번호());
        params.put(사용자_이름.필드명(), 이름());
        params.put(사용자_전화번호.필드명(), 전화번호());
        params.put(사용자_타입.필드명(), 회원타입());
        return params;
    }

    public Map<String, String> 회원_수정_요청_데이터_생성() {
        Map<String, String> params = new HashMap<>();
        params.put(사용자_비밀번호.필드명(), 비밀번호());
        params.put(사용자_이름.필드명(), 이름());
        params.put(사용자_전화번호.필드명(), 전화번호());
        return params;
    }
}
