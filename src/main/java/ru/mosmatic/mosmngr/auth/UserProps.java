package ru.mosmatic.mosmngr.auth;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Настройки индивидуальные для каждого пользователя.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor()
@Entity(name = "UserProps")
@Table(name = "user_props")
public class UserProps {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "last_project_id")
    private Long lastProjectId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProps userProps)) {
            return false;
        }
        return getId().equals(userProps.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
