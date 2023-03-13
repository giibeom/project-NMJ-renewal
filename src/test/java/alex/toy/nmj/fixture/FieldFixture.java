package alex.toy.nmj.fixture;


public enum FieldFixture {
    식별자_아이디("id"),

    사용자_이메일("email"),
    사용자_비밀번호("password"),
    사용자_이름("name"),
    사용자_전화번호("phone"),
    사용자_타입("type"),
    사용자_상태("status")
    ;

    private final String value;

    FieldFixture(String value) {
        this.value = value;
    }

    public String 필드명() {
        return value;
    }
}
