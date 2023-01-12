package alex.toy.nmj.member.domain;

import alex.toy.nmj.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "NMJ_MEMBER")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email",
            nullable = false,
            length = 100)
    private String email;

    @Column(name = "member_password",
            nullable = false,
            length = 70)
    private String password;

    @Column(name = "member_name",
            nullable = false,
            length = 45)
    private String name;

    @Column(name = "member_phone",
            nullable = false,
            length = 20)
    private String phone;

    @Enumerated(STRING)
    @Column(name = "member_type",
            nullable = false,
            length = 30)
    private MemberType type;

    @Builder
    private Member(final Long id, final String email, final String password,
                   final String name, final String phone, final MemberType type) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.type = type;
    }
}
