package ru.mosmatic.mngrsrv.customers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Заказчик проекта.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "Customer")
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * Возвращает заказчика проекта.
     *
     * @param name имя заказчика
     * @return Customer
     */
    public static Customer of(String name) {
        var customer = new Customer();
        customer.setName(name);
        return customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer customer)) {
            return false;
        }
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
