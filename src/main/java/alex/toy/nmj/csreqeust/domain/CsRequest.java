package alex.toy.nmj.csreqeust.domain;

import alex.toy.nmj.common.domain.BaseEntity;
import alex.toy.nmj.review.domain.Review;
import alex.toy.nmj.store.domain.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "NMJ_CS_REQUEST")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class CsRequest extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cs_id")
    private Long id;

    @Enumerated(STRING)
    @Column(nullable = false, length = 20)
    private CsType type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(STRING)
    @Column(nullable = false, length = 20)
    private CsStatus status;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    private CsRequest(final Long id, final CsType type, final String description,
                      final CsStatus status, final Store store, final Review review) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.status = status;
        this.store = store;
        this.review = review;
    }
}
