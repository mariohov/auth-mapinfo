package br.com.mapinfo.authservice.user.web.dto;

import br.com.mapinfo.authservice.user.Privilege;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrivilegeMapper {

    Privilege toPrivilege(PrivilegeDTO privilegeDTO);

    PrivilegeDTO fromPrivilege(Privilege privilege);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<PrivilegeDTO> fromPrivileges(List<Privilege> privileges);

}
