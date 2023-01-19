package alex.toy.nmj.dto;

public class TestMemberCreateRequestDto {

    private String email;

    private String password;

    private String name;

    private String phone;

    private String type;

    public TestMemberCreateRequestDto(final String email, final String password,
                                      final String name, final String phone, final String type) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }
}
