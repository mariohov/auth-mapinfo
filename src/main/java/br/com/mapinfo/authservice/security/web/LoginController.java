package br.com.mapinfo.authservice.security.web;

import br.com.mapinfo.authservice.company.Organization;
import br.com.mapinfo.authservice.company.OrganizationService;
import br.com.mapinfo.authservice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

@Controller
public class LoginController {

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "/")
    public String root(Model model) {
        return "redirect:/logincompany";
    }

    @GetMapping(value = "/logincompany")
    public String getLoginCompanies(Model model) {
        getOrganizations().ifPresent(d -> {
            model.addAttribute("organizations", d);
        });
        return "login_company";
    }

    @PostMapping(value = "/logincompany")
    public String setLoginCompany(HttpServletRequest request, Model model) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth != null && !auth.getClass().equals(AnonymousAuthenticationToken.class)) {
            User user = (User) auth.getPrincipal();
            Long organizationId = Long.valueOf(obtainOrganization(request));
            Optional<Organization> organization = organizationService.findById(organizationId);
            user.setOrganizationLoggedIn(organization.orElse(null));
        }


        return "redirect:/index";
    }

    @RequestMapping(value = "index")
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
    public String login() {
        return "login";
    }

    private Optional<Set<Organization>> getOrganizations() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        Set<Organization> organizations = null;
        if (auth != null && !auth.getClass().equals(AnonymousAuthenticationToken.class)) {
            User user = (User) auth.getPrincipal();
            organizations = user.getOrganizations();
        }

        return Optional.ofNullable(organizations);
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

    private String obtainOrganization(HttpServletRequest request) {
        return request.getParameter(CustomAuthenticationFilter.SPRING_SECURITY_FORM_DOMAIN_KEY);
    }
}
