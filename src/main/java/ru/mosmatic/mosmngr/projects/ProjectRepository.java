package ru.mosmatic.mosmngr.projects;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * .
 */
public interface ProjectRepository extends JpaRepository<Project, Location> {
}
