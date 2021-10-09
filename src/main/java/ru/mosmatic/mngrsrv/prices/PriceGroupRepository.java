package ru.mosmatic.mngrsrv.prices;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * .
 */
public interface PriceGroupRepository extends JpaRepository<PriceGroup, Long> {

    Optional<PriceGroup> findByName(String name);

}
