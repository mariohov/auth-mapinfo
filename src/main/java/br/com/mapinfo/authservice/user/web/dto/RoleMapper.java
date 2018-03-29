package br.com.mapinfo.authservice.user.web.dto;

import br.com.mapinfo.authservice.user.Role;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = PrivilegeMapper.class)
public interface RoleMapper {

    Role toRole(RoleDTO roleDTO);

    RoleDTO fromRole(Role role);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<RoleDTO> fromRoles(List<Role> roles);
}
