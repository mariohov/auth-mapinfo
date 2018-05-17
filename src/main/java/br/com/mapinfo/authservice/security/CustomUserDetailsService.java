package br.com.mapinfo.authservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService {

    UserDetails loadUserByUsernameAndOrganization(String username, String organization) throws UsernameNotFoundException;

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
