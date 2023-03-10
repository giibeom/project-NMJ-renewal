package alex.toy.nmj.member.presentation.dto.request;

import alex.toy.nmj.member.application.command.MemberCreateRequest;
import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberCreateRequestDto implements MemberCreateRequest {

    @NotBlank(message = "이메일을 입력하세요")
    private final String email;

    @NotBlank(message = "비밀번호를 입력하세요")
    private final String password;

    @NotBlank(message = "이름을 입력하세요")
    private final String name;

    @NotBlank(message = "전화번호를 입력하세요")
    private final String phone;

    private final MemberType type;

    @Builder
    private MemberCreateRequestDto(final String email, final String password,
                                   final String name, final String phone, final MemberType type) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.type = type;
    }

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .password(this.password) // TODO : 암호화 진행 (시큐리티)
                .name(this.name)
                .phone(this.phone)
                .type(this.type)
                .build();
    }
}
