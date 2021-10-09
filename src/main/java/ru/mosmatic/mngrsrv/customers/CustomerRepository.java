package ru.mosmatic.mngrsrv.customers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * .
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByName(String name);

}
