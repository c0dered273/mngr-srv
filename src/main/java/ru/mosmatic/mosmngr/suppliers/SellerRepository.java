package ru.mosmatic.mosmngr.suppliers;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * .
 */
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByName(String name);

}
