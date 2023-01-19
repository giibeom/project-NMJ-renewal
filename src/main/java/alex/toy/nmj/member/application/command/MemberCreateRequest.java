package alex.toy.nmj.member.application.command;

import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberType;

public interface MemberCreateRequest {
    String getEmail();

    String getPassword();

    String getName();

    String getPhone();

    MemberType getType();

    Member toEntity();
}
