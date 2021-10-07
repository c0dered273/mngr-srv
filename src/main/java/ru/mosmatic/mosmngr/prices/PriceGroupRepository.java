package ru.mosmatic.mosmngr.prices;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * .
 */
public interface PriceGroupRepository extends JpaRepository<PriceGroup, Long> {

    Optional<PriceGroup> findByName(String name);

}
