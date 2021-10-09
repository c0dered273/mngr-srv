package ru.mosmatic.mngrsrv.prices;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.mosmatic.mngrsrv.suppliers.Seller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Модификатор скидки.
 * Хранит величину скидки на ценовую группу у соответствующего поставщика.
 * Вычисляется по формуле: 1 - [Скидка в процентах] / 100
 * т.е. для скидки 27% значение value должно быть равно 0,73.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "Discount")
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "discount_value", nullable = false)
    private Double discountValue;

    @ToString.Exclude
    @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    /**
     *  Возвращает сущность со скидкой.
     *
     * @param value значение скидки
     * @param seller продавец, которому принадлежит скидка
     * @return Discount
     */
    public static Discount of(Double value, Seller seller) {
        var discount = new Discount();
        discount.setDiscountValue(value);
        discount.setSeller(seller);
        return discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Discount discount)) {
            return false;
        }
        return Objects.equals(getId(), discount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
