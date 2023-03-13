package alex.toy.nmj.member.application;

import alex.toy.nmj.member.application.command.MemberCreateRequest;
import alex.toy.nmj.member.application.command.MemberUpdateRequest;
import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberCommandRepository;
import alex.toy.nmj.member.domain.MemberQueryRepository;
import alex.toy.nmj.member.exception.DuplicatedMemberEmailException;
import alex.toy.nmj.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberCommandService {

    private final MemberCommandRepository memberCommandRepository;
    private final MemberQueryRepository memberQueryRepository;

    public MemberCommandService(
            final MemberCommandRepository memberCommandRepository,
            final MemberQueryRepository memberQueryRepository
    ) {
        this.memberCommandRepository = memberCommandRepository;
        this.memberQueryRepository = memberQueryRepository;
    }

    public Long save(final MemberCreateRequest memberCreateRequest) {
        Member member = memberCreateRequest.toEntity();

        if (isAlreadyExistEmail(member)) {
            throw new DuplicatedMemberEmailException();
        }

        memberCommandRepository.save(member);

        return member.getId();
    }

    public void update(final Long memberId, final MemberUpdateRequest memberUpdateRequest) {
        Member member = findMemberById(memberId);
        member.update(memberUpdateRequest.toEntity());
    }

    public void delete(final Long memberId) {
        Member member = findMemberById(memberId);
        member.delete();
    }


    private boolean isAlreadyExistEmail(final Member member) {
        return memberQueryRepository.existsByEmail(member.getEmail());
    }

    private Member findMemberById(Long memberId) {
        return memberQueryRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
