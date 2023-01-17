package alex.toy.nmj.member.infra;

import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaMemberRepository
        extends MemberRepository, JpaRepository<Member, Long> {
}
