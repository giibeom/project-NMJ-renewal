package alex.toy.nmj.common.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {

    @Column(name = "road_name_address",
            nullable = false,
            length = 200)
    private String roadName;

    @Column(name = "detail_address",
            nullable = false,
            length = 200)
    private String detail;

    public Address(final String roadName, final String detail) {
        this.roadName = roadName;
        this.detail = detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address that = (Address) o;
        return Objects.equals(getRoadName(), that.getRoadName()) && Objects.equals(getDetail(), that.getDetail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoadName(), getDetail());
    }
}
