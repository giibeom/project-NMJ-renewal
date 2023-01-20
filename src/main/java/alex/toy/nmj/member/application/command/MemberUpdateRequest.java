package alex.toy.nmj.member.application.command;

import alex.toy.nmj.member.domain.Member;

public interface MemberUpdateRequest {

    String getPassword();

    String getName();

    String getPhone();

    Member toEntity();
}
