package alex.toy.nmj.store.domain;

import alex.toy.nmj.common.domain.Address;
import alex.toy.nmj.common.domain.BaseEntity;
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
public class Store extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 13)
    private String phone;

    @Column(nullable = false, length = 11)
    private String businessHours;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(STRING)
    @Column(nullable = false, length = 10)
    private StoreType type;

    @Column(nullable = false)
    @ColumnDefault("0.0")
    private double ratings;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int rateCount;

    private Long mainImageId;

    @Embedded
    private Address address;

    @Embedded
    private LatLng latLng;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Store(final Long id, final String name, final String phone,
                  final String businessHours, final String description, final StoreType type,
                  final Long mainImageId, final Address address, final LatLng latLng, final Member member) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.businessHours = businessHours;
        this.description = description;
        this.type = type;
        this.mainImageId = mainImageId;
        this.address = address;
        this.latLng = latLng;
        this.member = member;
        this.rateCount = 0;
        this.ratings = 0.0;
    }
}
