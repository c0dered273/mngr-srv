package ru.mosmatic.mosmngr.suppliers;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Поставщик компонентов.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "Seller")
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seller_gen",
            sequenceName = "seller_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * Возвращает поствщика компонентов.
     *
     * @param name название поставщика
     * @return Seller
     */
    public static Seller of(String name) {
        var seller = new Seller();
        seller.setName(name);
        return seller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seller seller)) {
            return false;
        }
        return Objects.equals(getId(), seller.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
