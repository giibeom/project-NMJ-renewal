package alex.toy.nmj.member.application;

import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberQueryRepository;
import alex.toy.nmj.member.domain.MemberStatus;
import alex.toy.nmj.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    public MemberQueryService(final MemberQueryRepository memberQueryRepository) {
        this.memberQueryRepository = memberQueryRepository;
    }


    public Member findById(final Long memberId) {
        return findNotDeletedMember(memberId);
    }


    private Member findNotDeletedMember(final Long memberId) {
        return memberQueryRepository.findByIdAndStatusNot(memberId, MemberStatus.DELETED)
                .orElseThrow(MemberNotFoundException::new);
    }
}
