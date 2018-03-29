package br.com.mapinfo.authservice.company;

import br.com.mapinfo.authservice.user.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "ECOMPANY")
public class Company implements Serializable {

    private static final long serialVersionUID = -6987003292033054963L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String registration;

    @Column(nullable = false)
    private String tenant;

    @OneToMany(mappedBy = "company")
    private List<Organization> organizations;

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
}
