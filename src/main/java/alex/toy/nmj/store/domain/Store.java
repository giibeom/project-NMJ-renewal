package alex.toy.nmj.store.domain;

import alex.toy.nmj.common.domain.Address;
import alex.toy.nmj.common.domain.BaseTimeEntity;
import alex.toy.nmj.common.domain.LatLng;
import alex.toy.nmj.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "NMJ_STORE")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @Column(name = "store_name",
            nullable = false,
            length = 45)
    private String name;

    @Column(name = "store_phone",
            nullable = false,
            length = 45)
    private String phone;

    @Column(name = "store_hours",
            nullable = false,
            length = 50)
    private String hours;

    @Column(name = "store_description",
            columnDefinition = "TEXT")
    private String description;

    @Enumerated(STRING)
    @Column(name = "store_type",
            nullable = false,
            length = 30)
    private StoreType type;

    @Column(name = "store_ratings",
            nullable = false)
    @ColumnDefault("0.0")
    private double ratings;

    @Column(name = "store_rate_count",
            nullable = false)
    @ColumnDefault("0")
    private int rateCount;

    @Embedded
    private Address address;

    @Embedded
    private LatLng latLng;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Store(final Long id, final String name, final String phone,
                  final String hours, final String description, final StoreType type,
                  final Address address, final LatLng latLng, final Member member) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.hours = hours;
        this.description = description;
        this.type = type;
        this.address = address;
        this.latLng = latLng;
        this.member = member;
    }
}
