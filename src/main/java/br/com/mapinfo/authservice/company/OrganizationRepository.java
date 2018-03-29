package br.com.mapinfo.authservice.company;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
