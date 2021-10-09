package ru.mosmatic.mngrsrv.vendors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
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
 * Производитель компонентов.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "Vendor")
@Table(name = "vendors")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "vendor_gen",
            sequenceName = "vendor_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "import_props_id", nullable = false)
    private ImportProps importProps;

    /**
     * Возвращает производителя компонентов.
     *
     * @param name название производителя компонентов
     * @return Vendor
     */
    public static Vendor of(String name, ImportProps importProps) {
        var vendor = new Vendor();
        vendor.setName(name);
        vendor.setImportProps(importProps);
        return vendor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vendor vendor)) {
            return false;
        }
        return Objects.equals(getId(), vendor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
