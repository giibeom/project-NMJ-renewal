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

    @Column(unique = true, nullable = false, length = 100)
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
    private Member(final Long id, final String email, final String password, final String name,
                   final String phone, final MemberType type, final MemberStatus status) {

        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.type = type;
        changeStatus();
    }

    public boolean isUser() {
        return MemberType.USER == this.type;
    }

    public boolean isStore() {
        return MemberType.STORE == this.type;
    }

    public boolean isAdmin() {
        return MemberType.ADMIN == this.type;
    }

    private void changeStatus() {
        if (isUser()) {
            this.status = MemberStatus.NORMAL;
        }

        if (isStore()) {
            this.status = MemberStatus.WAIT;
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
