package br.com.mapinfo.authservice.user;

import br.com.mapinfo.authservice.company.Organization;
import br.com.mapinfo.authservice.module.Module;
import br.com.mapinfo.authservice.module.ModuleRole;
import br.com.mapinfo.authservice.person.Person;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
@Table(name = "EUSER")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = -9050521730023430029L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(optional = false)
    private Person person;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Organization> organizations;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private Boolean credentialsNonExpired;

    @Column(nullable = false)
    private Boolean accountNonLocked;

    @Column(nullable = false)
    private Boolean accountNonExpired;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "accesstoken_expiredate")
    private Date accessTokenExpireDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "lastlogin")
    private Date lastLogin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="EUSERROLES", joinColumns=
            {@JoinColumn(name="user_id")},  inverseJoinColumns=
            {@JoinColumn(name="module_id"), @JoinColumn(name = "role_id")})
    private List<ModuleRole> moduleRoles;

    @Transient
    private Organization organizationLoggedIn;

    @Transient
    private Module moduleLoggedIn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (moduleLoggedIn != null) {
            authorities.addAll(moduleLoggedIn.getRoles());
            authorities.addAll(getPermissions());
        }

        return authorities;
    }

    public Set<Privilege> getPermissions() {
        Set<Privilege> perms = new HashSet<>();
        moduleLoggedIn.getRoles().parallelStream().forEach(r -> perms.addAll(r.getPrivileges()));
        return perms;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
