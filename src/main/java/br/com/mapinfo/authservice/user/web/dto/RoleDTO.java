package br.com.mapinfo.authservice.user.web.dto;

import br.com.mapinfo.authservice.user.Privilege;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class RoleDTO implements Serializable {


    private static final long serialVersionUID = -3346708322623748458L;

    private String id;

    private String name;

    private Set<Privilege> privileges;
}
