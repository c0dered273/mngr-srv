package ru.mosmatic.mngrsrv.projects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.mosmatic.mngrsrv.parts.Part;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Место размещения комплектующих (шкаф управления, установка, цех и т.д.).
 * Позволяет групиировать комплектующие внутри одного проекта.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "Location")
@Table(name ="locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projects_gen")
    @SequenceGenerator(name = "projects_gen", sequenceName = "projects_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "locations_parts_quantity",
            joinColumns = {@JoinColumn(name = "location_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "part_quantity_id",
                    referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "part_id")
    private Map<Part, PartQuantity> parts = new HashMap<>();

    /**
     * Возвращает место размещения.
     *
     * @param name название размещения
     * @return Location
     */
    public static Location of(String name) {
        var location = new Location();
        location.setName(name);
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location location)) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
