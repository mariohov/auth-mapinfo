package br.com.mapinfo.authservice.company.web;

import br.com.mapinfo.authservice.company.Company;
import br.com.mapinfo.authservice.company.CompanyService;
import br.com.mapinfo.authservice.company.web.dto.CompanyDTO;
import br.com.mapinfo.authservice.company.web.dto.CompanyMapper;
import br.com.mapinfo.authservice.multitenant.MultiTenantConstants;
import br.com.mapinfo.authservice.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController extends BaseController {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyService companyService;

    @PostMapping(value = "/companies")
    public CompanyDTO create(@RequestHeader(MultiTenantConstants.TENANT_KEY) String tenant, CompanyDTO companyDTO) {
        Company c = companyService.save(companyMapper.toCompany(companyDTO));
        return companyMapper.fromCompany(c);
    }

    @GetMapping(value = "/companies")
    public List<CompanyDTO> findAll() {
        return companyMapper.fromCompanies(companyService.findAll());
    }

}
