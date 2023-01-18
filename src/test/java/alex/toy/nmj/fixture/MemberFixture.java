package alex.toy.nmj.fixture;

import alex.toy.nmj.dto.TestMemberCreateRequestDto;
import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberType;
import alex.toy.nmj.member.presentation.dto.request.MemberCreateRequestDto;

public enum MemberFixture {
    // ----- 정상 값 -----
    일반_회원_gibeom("dev.gibeom@gmail.com", "일반77", "giibeom", "010-7777-7777", "USER"),
    매장_회원_Alex("kpmyung@gmail.com", "매장486", "Alex", "010-1234-5678", "STORE"),
    관리자_회원_기범("admin@gmail.com", "관리자486", "기범관리자", "010-0707-0707", "ADMIN"),
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

    public Member 엔티티_생성() {
        return Member.builder()
                .email(this.email)
                .password(this.password) // TODO : 암호화 진행 (시큐리티)
                .name(this.name)
                .phone(this.phone)
                .type(MemberType.valueOf(this.type))
                .build();
    }

    public MemberCreateRequestDto 등록_요청_DTO_생성() {
        return MemberCreateRequestDto.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .phone(this.phone)
                .type(MemberType.valueOf(this.type))
                .build();
    }


    public TestMemberCreateRequestDto 등록_요청_데이터_생성() {
        return new TestMemberCreateRequestDto(
                this.email,
                this.password,
                this.name,
                this.phone,
                this.type);
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
}
