package ru.mosmatic.mngrsrv.parts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * .
 */
public interface PartRepository extends JpaRepository<Part, Long> {

    Optional<Part> findByPartNo(String partNo);

}
