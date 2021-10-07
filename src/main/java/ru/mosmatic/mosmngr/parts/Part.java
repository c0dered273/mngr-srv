package ru.mosmatic.mosmngr.parts;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.mosmatic.mosmngr.prices.Currency;
import ru.mosmatic.mosmngr.prices.PriceGroup;
import ru.mosmatic.mosmngr.vendors.Vendor;

/**
 * Изделие.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor()
@Entity(name = "Part")
@Table(name = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parts_gen")
    @SequenceGenerator(name = "parts_gen", sequenceName = "parts_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Заказной номер изделия.
     */
    @Column(name = "part_no", nullable = false)
    private String partNo;

    /**
     * Наименование.
     */
    @Column(name = "name")
    private String name;

    /**
     * Описание и краткие харрактерристики.
     */
    @Column(name = "description")
    private String description;

    /**
     * Дополнительная строка описания.
     */
    @Column(name = "additional_desc")
    private String additionalDesc;

    /**
     * Прайсовая цена без НДС.
     */
    @Column(name = "list_price", nullable = false)
    private Double listPrice;

    /**
     * Колличество изделий в упаковке. Обычно минимальное колличество для заказа.
     */
    @Column(name = "pack_quantity")
    private Integer packQuantity;

    /**
     * Название единицы измерения для изделия (штуки, метры, и т.д.).
     */
    @Column(name = "unit")
    private String unit;

    /**
     * Колличество изделий, поставляемых за указанную цену.
     */
    @Column(name = "price_unit")
    private Integer priceUnit;

    /**
     * Вес.
     */
    @Column(name = "weight")
    private Double weight;

    /**
     * Время последнего изменения записи.
     */
    @Column(name = "last_modified", nullable = false)
    private LocalDateTime lastModified;

    /**
     * Дата и время, после которой цена ни изделие может считаться недействительной.
     * Обычно дата выхода нового прайса.
     */
    @Column(name = "expired")
    private LocalDateTime expired;

    /**
     * Производитель к которому принадлежит изделие.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    /**
     * Валюта цены.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    /**
     * Ценовая группа для изделия.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "price_group_id", nullable = false)
    private PriceGroup priceGroup;

    /**
     * Возвращает изделие с минимально необходимыми полями.
     *
     * @param partNo заказзной номер
     * @param listPrice прайсовая цена без НДС
     * @param vendor производитель
     * @param currency валюта
     * @param priceGroup ценовая группа
     * @return Part
     */
    public static Part of(String partNo,
                          Double listPrice,
                          Vendor vendor,
                          Currency currency,
                          PriceGroup priceGroup) {
        var part = new Part();
        part.setPartNo(partNo);
        part.setListPrice(listPrice);
        part.setVendor(vendor);
        part.setCurrency(currency);
        part.setPriceGroup(priceGroup);
        part.setLastModified(LocalDateTime.now(ZoneId.of("UTC")));
        return part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Part part)) {
            return false;
        }
        return Objects.equals(getId(), part.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
