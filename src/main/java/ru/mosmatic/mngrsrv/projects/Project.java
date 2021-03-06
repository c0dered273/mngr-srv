package ru.mosmatic.mngrsrv.projects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.mosmatic.mngrsrv.customers.Customer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Проект подбора комплектующих, относится к конкретному запросу от заказчика.
 * Позволяет группировать комплектующие по расположению (ШУ, Установки, и т.д.)
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "Project")
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projects_gen")
    @SequenceGenerator(name = "projects_gen", sequenceName = "projects_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime lastModified;

    @ToString.Exclude
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations = new ArrayList<>();

    @Column(name = "username", nullable = false, length = 36)
    private String username;

    /**
     * Возвращает проект.
     *
     * @param name название проекта
     * @param username UUID пользователя, создавшего проект
     * @param customer заказчик проекта
     * @return Project
     */
    public static Project of(String name, String username, Customer customer) {
        var project = new Project();
        project.setName(name);
        project.setUsername(username);
        project.setCustomer(customer);
        project.setCreated(LocalDateTime.now(ZoneId.of("UTC")));
        project.setLastModified(LocalDateTime.now(ZoneId.of("UTC")));
        return project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project project)) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
