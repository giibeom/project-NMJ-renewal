package alex.toy.nmj.member.application;

import alex.toy.nmj.member.application.command.MemberCreateRequest;
import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberRepository;
import alex.toy.nmj.member.exception.DuplicatedMemberEmailException;
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
    public Member saveMember(final MemberCreateRequest memberCreateRequest) {
        Member member = memberCreateRequest.toEntity();

        if (isAlreadyExistEmail(member)) {
            throw new DuplicatedMemberEmailException();
        }

        return memberRepository.save(member);
    }

    private boolean isAlreadyExistEmail(final Member member) {
        return memberRepository.existsByEmail(member.getEmail());
    }
}
