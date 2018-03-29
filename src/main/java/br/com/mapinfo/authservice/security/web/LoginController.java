package br.com.mapinfo.authservice.security.web;

import br.com.mapinfo.authservice.company.CompanyService;
import br.com.mapinfo.authservice.company.Organization;
import br.com.mapinfo.authservice.user.User;
import br.com.mapinfo.authservice.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController extends BaseController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/")
    public String root(@PathVariable(name = "tenantid") String tenant, Model model) {
        model.addAttribute("tenantid", tenant);
        return "redirect:/"+tenant+"/index";
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
    public String login(@PathVariable(name = "tenantid") String tenant, ServletRequest request, Model model) {

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
