package alex.toy.nmj.member.presentation.dto.response;

import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberStatus;
import alex.toy.nmj.member.domain.MemberType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class MemberUpdateResponse {

    @JsonIgnore
    private Member member;

    public MemberUpdateResponse(final Member member) {
        this.member = member;
    }

    public Long getId() {
        return this.member.getId();
    }

    public String getEmail() {
        return this.member.getEmail();
    }

    public String getName() {
        return this.member.getName();
    }

    public String getPhone() {
        return this.member.getPhone();
    }

    public MemberType getType() {
        return this.member.getType();
    }

    public MemberStatus getStatus() {
        return this.member.getStatus();
    }

    public LocalDateTime getCreatedDate() {
        return this.member.getCreatedDate();
    }

    public LocalDateTime getModifiedDate() {
        return this.member.getModifiedDate();
    }
}
