package alex.toy.nmj.fixture;

import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberType;
import alex.toy.nmj.member.presentation.dto.request.MemberCreateRequestDto;

public enum MemberFixture {
    일반_회원_giibeom("dev.gibeom@gmail.com", "비밀번호486",
            "giibeom", "010-7777-7777", MemberType.USER),
    매장_회원_Alex("kpmyung@gmail.com", "비밀번호486",
            "Alex", "010-1234-5678", MemberType.STORE),
    관리자_회원_기범("admin@gmail.com", "그대여아무걱정하지말아요",
            "기범관리자", "010-0707-0707", MemberType.ADMIN);

    private final String email;
    private final String password;
    private final String name;
    private final String phone;
    private final MemberType type;

    MemberFixture(String email, String password, String name, String phone, MemberType type) {
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
                .type(this.type)
                .build();
    }

    public MemberCreateRequestDto 등록_요청_데이터_생성() {
        return MemberCreateRequestDto.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .phone(this.phone)
                .type(this.type)
                .build();
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

    public MemberType 회원타입() {
        return type;
    }
}
