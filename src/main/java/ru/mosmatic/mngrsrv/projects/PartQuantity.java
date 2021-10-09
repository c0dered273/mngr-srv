package ru.mosmatic.mngrsrv.projects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.mosmatic.mngrsrv.parts.Part;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Хранит колличество для каждого изделия привязанного к месту размещения.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "PartQuantity")
@Table(name = "part_quantity")
public class PartQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projects_gen")
    @SequenceGenerator(name = "projects_gen", sequenceName = "projects_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @ToString.Exclude
    @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private Part part;

    /**
     * Возвращает сущность хранения количества комплектующих.
     *
     * @param quantity количество
     * @return PartQuantity
     */
    public static PartQuantity of(Double quantity) {
        var partQuantity = new PartQuantity();
        partQuantity.setQuantity(quantity);
        return partQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartQuantity partQuantity)) {
            return false;
        }
        return Objects.equals(getId(), partQuantity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
