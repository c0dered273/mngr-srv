package ru.mosmatic.mosmngr.prices;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Валюта.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "Currency")
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Currency currency)) {
            return false;
        }
        return Objects.equals(getId(), currency.getId());
    }

    /**
     *  Возвращает валюту.
     *
     * @param code код валюты в формате ISO
     * @return Currency
     */
    public static Currency of(String code) {
        var currency = new Currency();
        currency.setCode(code);
        return currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
