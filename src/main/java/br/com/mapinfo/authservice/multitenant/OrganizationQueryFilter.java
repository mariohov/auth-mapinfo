package br.com.mapinfo.authservice.multitenant;

import br.com.mapinfo.authservice.user.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Collection;

@Aspect
@Component
public class OrganizationQueryFilter {

    @AfterReturning(pointcut = "execution(* javax.persistence.EntityManagerFactory.createEntityManager(..))", returning = "entityManager")
    public void forceFilter(JoinPoint joinPoint, Object entityManager) {
        EntityManager em = (EntityManager) entityManager;
        Session hibernateSession = (Session) em.getDelegate();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
            boolean isAdmin = false;
            for (GrantedAuthority grantedAuthority : roles) {
                if (grantedAuthority.getAuthority().equals("ADMIN")) {
                    isAdmin = true;
                }
            }

            if (!isAdmin && !auth.getClass().equals(AnonymousAuthenticationToken.class)) {
                User user = (User) auth.getPrincipal();
                if (user.getOrganizationLoggedIn() != null) {
                    Long organizationId = user.getOrganizationLoggedIn().getId();
                    hibernateSession.enableFilter("organizationFilter").setParameter("organizationId", organizationId);
                }
            }
        }
    }

}
