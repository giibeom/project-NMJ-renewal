package alex.toy.nmj.store.domain;

import alex.toy.nmj.common.domain.BaseTimeEntity;
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
public class StoreImage extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_name",
            length = 50)
    private String name;

    @Column(name = "image_path",
            nullable = false,
            length = 200)
    private String path;

    @Column(name = "image_representative_status",
            nullable = false)
    @ColumnDefault("false")
    private boolean representativeStatus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}
