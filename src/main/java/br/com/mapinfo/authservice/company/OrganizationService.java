package br.com.mapinfo.authservice.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public Optional<Organization> findById(Long id) {
        return organizationRepository.findById(id);
    }
}
