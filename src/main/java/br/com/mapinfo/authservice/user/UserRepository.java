package br.com.mapinfo.authservice.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndOrganizationsId(String username, Long organizationId);
}
