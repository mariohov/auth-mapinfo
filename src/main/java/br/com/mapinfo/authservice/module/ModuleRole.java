package br.com.mapinfo.authservice.module;

import br.com.mapinfo.authservice.user.Role;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "EMODULEROLES")
public class ModuleRole implements Serializable {

    private static final long serialVersionUID = 3058988147864950088L;

    @EmbeddedId
    private ModuleRoleId id;

    @ManyToOne
    @JoinColumn(name = "module_id", insertable = false, updatable = false)
    private Module module;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;


}
