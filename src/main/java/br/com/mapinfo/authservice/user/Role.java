package br.com.mapinfo.authservice.user;

import br.com.mapinfo.authservice.company.Organization;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "EROLE")
@FilterDef(name = "organizationFilter", parameters = {@ParamDef(name = "organizationId", type = "long")})
@Filter(name = "organizationFilter", condition = "organization_id = :organizationId")
public class Role implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 8430812047921297058L;

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name="EROLEPRIVILEGES", joinColumns=
            {@JoinColumn(name="role_id")}, inverseJoinColumns=
            {@JoinColumn(name="privilege_id")})
    private Set<Privilege> privileges;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Override
    public String getAuthority() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id);
    }
}
