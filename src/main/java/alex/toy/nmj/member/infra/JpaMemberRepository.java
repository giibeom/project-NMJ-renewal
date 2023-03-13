package alex.toy.nmj.member.infra;

import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberCommandRepository;
import alex.toy.nmj.member.domain.MemberQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository
        extends MemberCommandRepository, MemberQueryRepository, JpaRepository<Member, Long> {
}
