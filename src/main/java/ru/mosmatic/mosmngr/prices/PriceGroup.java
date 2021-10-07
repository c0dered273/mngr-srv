package ru.mosmatic.mosmngr.prices;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.mosmatic.mosmngr.suppliers.Seller;
import ru.mosmatic.mosmngr.vendors.Vendor;

/**
 * Ценовая группа.
 * Применяется для вычисления скидки на изделия.
 * У каждого производителя все комплектующие относятся к одной или нескольким ценовым группам.
 * На каждую ценовую группу своя величина скидки, таким образом мы можем вычислить закупочную
 * цену изделия, умножив прйсовую цену на величину скидки.
 * Также содержит соответствие поставщика и величины скидки
 * для соответствующей ценовой группы.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "PriceGroup")
@Table(name = "price_groups")
public class PriceGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ToString.Exclude
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "pricegroups_sellers_discount",
            joinColumns = {@JoinColumn(name = "price_group_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "discount_id",
                    referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "seller_id")
    private Map<Seller, Discount> discounts = new HashMap<>();

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    /**
     * Возвращает ценовую группу.
     *
     * @param name название группы
     * @param vendor производитель оборудования, которому принадлежит ценовая группа
     * @return PriceGroup
     */
    public static PriceGroup of(String name, Vendor vendor) {
        var priceGroup = new PriceGroup();
        priceGroup.setName(name);
        priceGroup.setVendor(vendor);
        return priceGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PriceGroup that)) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
