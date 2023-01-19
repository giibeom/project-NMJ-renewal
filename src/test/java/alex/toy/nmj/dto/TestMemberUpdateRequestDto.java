package alex.toy.nmj.dto;

public class TestMemberUpdateRequestDto {

    private String password;

    private String name;

    private String phone;

    public TestMemberUpdateRequestDto(String password, String name, String phone) {
        this.password = password;
        this.name = name;
        this.phone = phone;
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
}
