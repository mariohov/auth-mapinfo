package br.com.mapinfo.authservice.company;

import br.com.mapinfo.authservice.module.Module;
import br.com.mapinfo.authservice.user.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "EORGANIZATION")
public class Organization implements Serializable {

    private static final long serialVersionUID = 2775845472948094722L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToMany
    @JoinTable(name="EORGANIZATIONMODULES", joinColumns=
            {@JoinColumn(name="organization_id")}, inverseJoinColumns=
            {@JoinColumn(name="module_id")})
    private Set<Module> modules;

    @ManyToMany
    @JoinTable(name="EORGANIZATIONUSERS", joinColumns=
            {@JoinColumn(name="organization_id")},  inverseJoinColumns=
            {@JoinColumn(name="user_id")})
    private Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id);
    }

}
