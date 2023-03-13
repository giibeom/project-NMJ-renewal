package alex.toy.nmj.member.domain;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    boolean existsByEmail(String email);
    Optional<Member> findById(Long memberId);
    Optional<Member> findByIdAndStatusNot(Long id, MemberStatus memberStatus);
}
