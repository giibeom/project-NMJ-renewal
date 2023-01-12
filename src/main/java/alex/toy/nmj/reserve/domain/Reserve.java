package alex.toy.nmj.reserve.domain;

import alex.toy.nmj.common.domain.BaseTimeEntity;
import alex.toy.nmj.common.domain.StartEndTime;
import alex.toy.nmj.store.domain.Store;
import alex.toy.nmj.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "NMJ_RESERVE")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Reserve extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "reserve_id")
    private Long id;

    @Column(name = "reserve_people_count",
            nullable = false)
    private int peopleCount;

    @Column(name = "reserve_approve_status",
            nullable = false)
    @ColumnDefault("false")
    private boolean approveStatus;

    @Embedded
    @AttributeOverride(name = "startDate",
            column = @Column(name = "reserve_start_date", nullable = false))
    @AttributeOverride(name = "endDate",
            column = @Column(name = "reserve_end_date", nullable = false))
    private StartEndTime startEndTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    private Reserve(final Long id, final int peopleCount, final StartEndTime startEndTime,
                    final Member member, final Store store) {
        this.id = id;
        this.peopleCount = peopleCount;
        this.startEndTime = startEndTime;
        this.member = member;
        this.store = store;
    }
}
