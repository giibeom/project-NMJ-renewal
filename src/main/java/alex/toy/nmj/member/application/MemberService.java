package alex.toy.nmj.member.application;

import alex.toy.nmj.member.application.command.MemberCreateRequest;
import alex.toy.nmj.member.application.command.MemberUpdateRequest;
import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberRepository;
import alex.toy.nmj.member.exception.DuplicatedMemberEmailException;
import alex.toy.nmj.member.exception.MemberNotFoundException;
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
    public Member save(final MemberCreateRequest memberCreateRequest) {
        Member member = memberCreateRequest.toEntity();

        if (isAlreadyExistEmail(member)) {
            throw new DuplicatedMemberEmailException();
        }

        return memberRepository.save(member);
    }

    public Member update(final Long memberId, final MemberUpdateRequest memberUpdateRequest) {
        Member member = findMemberById(memberId);

        if (isUnmodifiableMemberStatus(member)) {
            throw new MemberNotFoundException();
        }

        member.update(memberUpdateRequest.toEntity());

        return member;
    }

    private boolean isUnmodifiableMemberStatus(Member member) {
        return member.isWaitingJoin() || member.isDeleted();
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    private boolean isAlreadyExistEmail(final Member member) {
        return memberRepository.existsByEmail(member.getEmail());
    }
}
