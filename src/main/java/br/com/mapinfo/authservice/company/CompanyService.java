package br.com.mapinfo.authservice.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findByTenant(String tenant) {
        return companyRepository.findByTenant(tenant);
    }

}
