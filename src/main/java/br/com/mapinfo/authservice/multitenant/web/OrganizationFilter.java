package br.com.mapinfo.authservice.multitenant.web;

import br.com.mapinfo.authservice.user.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Collection;

@Aspect
@Component
public class OrganizationFilter {

    Logger log = LoggerFactory.getLogger(OrganizationFilter.class);

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
                Long organizationId = ((User) auth.getPrincipal()).getOrganizationLoggedIn().getId();
                hibernateSession.enableFilter("organizationFilter").setParameter("organizationId", organizationId);
            }
        }
    }

}
