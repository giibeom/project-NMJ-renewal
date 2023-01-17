package alex.toy.nmj.member.application;

import alex.toy.nmj.member.application.command.MemberCreateRequest;
import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member saveMember(MemberCreateRequest memberCreateRequest) {
        Member member = memberCreateRequest.toEntity();

        // TODO: 중복된 EMAIL인지 검증

        return memberRepository.save(member);
    }
}
