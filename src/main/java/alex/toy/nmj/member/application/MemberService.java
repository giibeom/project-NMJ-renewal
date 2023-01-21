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

    /**
     * 회원 상세 정보를 조회합니다.
     *
     * @param memberId 회원 고유 번호
     * @return 회원 상세 정보
     */
    public Member findById(final Long memberId) {
        return findMemberById(memberId);
    }

    /**
     * 회원 정보를 등록합니다.
     *
     * @param memberCreateRequest 등록할 회원 정보
     * @return 등록한 회원 정보
     */
    @Transactional
    public Member save(final MemberCreateRequest memberCreateRequest) {
        Member member = memberCreateRequest.toEntity();

        if (isAlreadyExistEmail(member)) {
            throw new DuplicatedMemberEmailException();
        }

        return memberRepository.save(member);
    }

    /**
     * 회원 정보를 수정합니다.
     *
     * @param memberId            수정할 회원 고유 번호
     * @param memberUpdateRequest 수정할 회원 정보
     * @return 수정한 회원 정보
     */
    @Transactional
    public Member update(final Long memberId, final MemberUpdateRequest memberUpdateRequest) {
        Member member = findMemberById(memberId);

        if (member.isUnmodifiableMemberStatus()) {
            throw new MemberNotFoundException();
        }

        member.update(memberUpdateRequest.toEntity());

        return member;
    }

    /**
     * 회원 정보를 삭제합니다.
     *
     * @param memberId 삭제할 회원 고유 번호
     */
    @Transactional
    public void delete(final Long memberId) {
        Member member = findMemberById(memberId);

        if (member.isUndeletableMemberStatus()) {
            throw new MemberNotFoundException();
        }

        member.delete();
    }


    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    private boolean isAlreadyExistEmail(final Member member) {
        return memberRepository.existsByEmail(member.getEmail());
    }
}
