package br.com.mapinfo.authservice.company.web.dto;

import br.com.mapinfo.authservice.company.Company;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company toCompany(CompanyDTO companyDTO);

    CompanyDTO fromCompany(Company company);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<CompanyDTO> fromCompanies(List<Company> companies);
}
