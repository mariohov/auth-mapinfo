package br.com.mapinfo.authservice.security.web;

import br.com.mapinfo.authservice.company.CompanyService;
import br.com.mapinfo.authservice.company.Organization;
import br.com.mapinfo.authservice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static br.com.mapinfo.authservice.multitenant.MultiTenantConstants.DEFAULT_TENANT_ID;
import static br.com.mapinfo.authservice.multitenant.MultiTenantConstants.TENANT_KEY;

@Controller
public class LoginController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/")
    public String root(Model model) {
        return "redirect:/index";
    }

    @RequestMapping(value = "/index")
    public String index(Model model) {
        getOrganization().ifPresent(d -> {
            model.addAttribute("organization", d);
        });
        return "index";
    }

    @RequestMapping(value = "/user/index")
    public String userIndex(Model model) {
        getOrganization().ifPresent(d -> {
            model.addAttribute("organization", d);
        });
        return "user/index";
    }

    @RequestMapping(value = "/login")
    public String login(ServletRequest request, Model model) {

        String tenant;

        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getHeader(TENANT_KEY) != null) {
            tenant = (String)req.getHeader(TENANT_KEY);
        } else {
            tenant = DEFAULT_TENANT_ID;
        }

        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth != null && !auth.getClass().equals(AnonymousAuthenticationToken.class)) {
            User user = (User) auth.getPrincipal();
            tenant = user.getOrganizationLoggedIn().getCompany().getTenant();
        }

        if (tenant != null) {
            getOrganizations(tenant).ifPresent(o -> {
                model.addAttribute("organizations", o);
            });
        }

        return "login";
    }

    private Optional<List<Organization>> getOrganizations(String tenant) {
        return Optional.ofNullable(companyService.findByTenant(tenant).getOrganizations());
    }

    private Optional<String> getOrganization() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        String domain = null;
        if (auth != null && !auth.getClass().equals(AnonymousAuthenticationToken.class)) {
            User user = (User) auth.getPrincipal();
            domain = user.getOrganizationLoggedIn().getName();
        }
        return Optional.ofNullable(domain);
    }
}
