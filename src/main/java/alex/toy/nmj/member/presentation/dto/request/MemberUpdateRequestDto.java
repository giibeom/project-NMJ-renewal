package alex.toy.nmj.member.presentation.dto.request;

import alex.toy.nmj.member.application.command.MemberUpdateRequest;
import alex.toy.nmj.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberUpdateRequestDto implements MemberUpdateRequest {

    private String password;

    private String name;

    private String phone;

    @Builder
    private MemberUpdateRequestDto(final String password, final String name, final String phone) {
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public Member toEntity() {
        return Member.builder()
                .password(this.password) // TODO : 암호화 진행 (시큐리티)
                .name(this.name)
                .phone(this.phone)
                .build();
    }
}
