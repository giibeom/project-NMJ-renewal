package alex.toy.nmj.review.domain;

import alex.toy.nmj.common.domain.BaseTimeEntity;
import alex.toy.nmj.store.domain.Store;
import alex.toy.nmj.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "NMJ_REVIEW")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column(name = "review_rate",
            nullable = false)
    private double rate;

    @Column(name = "review_description",
            columnDefinition = "TEXT")
    private String description;

    @Column(name = "review_report_status",
            nullable = false)
    @ColumnDefault("false")
    private boolean reportStatus;

    @Column(name = "review_star_count",
            nullable = false)
    @ColumnDefault("0")
    private int starCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    private Review(final Long id, final double rate, final String description,
                   final Member member, final Store store) {
        this.id = id;
        this.rate = rate;
        this.description = description;
        this.member = member;
        this.store = store;
    }
}
