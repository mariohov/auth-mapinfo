package br.com.mapinfo.authservice.security;

import br.com.mapinfo.authservice.user.User;
import br.com.mapinfo.authservice.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("userDetailsService")
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsernameAndOrganization(String username, String organization) throws UsernameNotFoundException {
        if (StringUtils.isAnyBlank(username, organization)) {
            throw new UsernameNotFoundException("Username and domain must be provided");
        }

        User user = userRepository.findByUsernameAndOrganizationsId(username, Long.valueOf(organization));

        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("Username not found for module, username=%s, module=%s",
                            username, organization));
        }

        user.setOrganizationLoggedIn(user.getOrganizations().iterator().next());

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isAnyBlank(username)) {
            throw new UsernameNotFoundException("Username must be provided");
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("Username not found, username=%s",
                            username));
        }

        return user;
    }
}
