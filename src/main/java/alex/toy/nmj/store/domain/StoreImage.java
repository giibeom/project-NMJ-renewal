package alex.toy.nmj.store.domain;

import alex.toy.nmj.common.domain.BaseEntity;
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
@Table(name = "NMJ_STORE_IMAGE")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class StoreImage extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(nullable = false, length = 200)
    private String path;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    private StoreImage(final Long id, final String name,
                       final String path, final Store store) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.store = store;
    }
}
