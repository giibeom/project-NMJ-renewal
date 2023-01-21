package alex.toy.nmj.member.domain;

import alex.toy.nmj.common.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "NMJ_MEMBER")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 70)
    private String password;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 13)
    private String phone;

    @Enumerated(STRING)
    @Column(nullable = false, length = 15)
    private MemberType type;

    @Enumerated(STRING)
    @Column(nullable = false, length = 15)
    private MemberStatus status;

    @Builder
    private Member(final Long id, final String email, final String password,
                   final String name, final String phone, final MemberType type) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.type = type;
        createStatus();
    }

    /**
     * 회원 타입이 일반 회원인지 확인합니다.
     * 
     * @return 일반 회원 여부
     */
    public boolean isUser() {
        return MemberType.USER == this.type;
    }

    /**
     * 회원 타입이 매장 회원인지 확입합니다.
     * 
     * @return 매장 회원 여부
     */
    public boolean isStore() {
        return MemberType.STORE == this.type;
    }

    /**
     * 회원 타입이 관리자 회원인지 확인합니다.
     * 
     * @return 관리자 회원 여부
     */
    public boolean isAdmin() {
        return MemberType.ADMIN == this.type;
    }

    /**
     * 회원 상태가 가입 대기 상태인지 확인합니다.
     * 
     * @return 가입 대기 상태 여부
     */
    public boolean isWaitingJoin() {
        return MemberStatus.WAIT == this.status;
    }

    /**
     * 회원 상태가 삭제된 상태인지 확인합니다.
     * 
     * @return 삭제 상태 여부
     */
    public boolean isDeleted() {
        return MemberStatus.DELETED == this.status;
    }

    /**
     * 회원 상태가 수정 가능한 상태인지 확인합니다.
     *
     * @return 수정 가능 여부
     */
    public boolean isUnmodifiableMemberStatus() {
        return this.isWaitingJoin() || this.isDeleted();
    }

    /**
     * 회원 상태가 삭제 가능한 상태인지 확인합니다.
     *
     * @return 삭제 가능 여부
     */
    public boolean isUndeletableMemberStatus() {
        return this.isWaitingJoin() || this.isDeleted();
    }

    /**
     * 회원 정보를 수정합니다.
     *
     * @param member 수정할 회원 정보
     */
    public void update(final Member member) {
        updatePassword(member.getPassword());
        updateName(member.getName());
        updatePhone(member.getPhone());
    }

    /**
     * 회원 정보를 삭제 처리 합니다.
     */
    public void delete() {
        this.status = MemberStatus.DELETED;
    }


    private void updatePassword(final String password) {
        if (password != null && !password.isBlank()) {
            this.password = password;
        }
    }

    private void updateName(final String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
    }

    private void updatePhone(final String phone) {
        if (phone != null && !phone.isBlank()) {
            this.phone = phone;
        }
    }

    private void createStatus() {
        if (isUser()) {
            this.status = MemberStatus.NORMAL;
            return;
        }

        if (isStore()) {
            this.status = MemberStatus.WAIT;
            return;
        }

        if (isAdmin()) {
            this.status = MemberStatus.NORMAL;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(getId(), member.getId()) && Objects.equals(getEmail(), member.getEmail()) && Objects.equals(getPassword(), member.getPassword()) && Objects.equals(getName(), member.getName()) && Objects.equals(getPhone(), member.getPhone()) && getType() == member.getType() && getStatus() == member.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getName(), getPhone(), getType(), getStatus());
    }
}
