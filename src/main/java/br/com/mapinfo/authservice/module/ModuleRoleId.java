package br.com.mapinfo.authservice.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRoleId implements Serializable {

    private static final long serialVersionUID = -3352425561602290956L;

    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "role_id")
    private String roleId;

}
