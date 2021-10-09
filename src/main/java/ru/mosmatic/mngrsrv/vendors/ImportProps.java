package ru.mosmatic.mngrsrv.vendors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.mosmatic.mngrsrv.prices.Currency;
import ru.mosmatic.mngrsrv.prices.PriceGroup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Настройки импорта из excel, индивидуальные для каждого производителя.
 * Необходимо для импорта данный о комплектующих из прайс листа производителя.
 * Содержит имена колонок из excel файла, которые соответсвуют полям сущности Part,
 * а также имя листа электронной таблицы, в которой хранятся цены.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "ImportProps")
@Table(name = "import_props")
public class ImportProps {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "vendors_gen",
            sequenceName = "vendors_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "part_no", nullable = false)
    private String partNo;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "additional_desc")
    private String additionalDesc;

    @Column(name = "list_price", nullable = false)
    private String listPrice;

    @Column(name = "currency")
    private String currency;

    @Column(name = "price_group")
    private String priceGroup;

    @Column(name = "unit")
    private String unit;

    @Column(name = "price_unit")
    private String priceUnit;

    @Column(name = "weight")
    private String weight;

    @Column(name = "tab_name", nullable = false)
    private String tabName;

    @OneToOne
    @JoinColumn(name = "default_currency_id")
    private Currency defaultCurrency;

    @OneToOne
    @JoinColumn(name = "default_price_group_id")
    private PriceGroup defaultPriceGroup;

    /**
     * Возвращает минимально необходимые настройки импорта.
     *
     * @param partNo .
     * @param listPrice .
     * @param tabName .
     * @return ImportProps
     */
    public static ImportProps of(String partNo,
                                 String listPrice,
                                 String tabName) {
        var importProps = new ImportProps();
        importProps.setPartNo(partNo);
        importProps.setListPrice(listPrice);
        importProps.setTabName(tabName);
        return importProps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImportProps that)) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
