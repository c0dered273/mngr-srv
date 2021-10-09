package ru.mosmatic.mngrsrv.suppliers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * .
 */
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByName(String name);

}
