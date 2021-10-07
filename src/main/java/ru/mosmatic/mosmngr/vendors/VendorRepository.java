package ru.mosmatic.mosmngr.vendors;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * .
 */
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByName(String name);

}
