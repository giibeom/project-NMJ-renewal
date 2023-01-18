package alex.toy.nmj.member.domain;

public interface MemberRepository {
    Member save(Member member);
    boolean existsByEmail(String email);
}
