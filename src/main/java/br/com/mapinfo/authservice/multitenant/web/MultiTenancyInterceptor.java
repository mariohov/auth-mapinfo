package br.com.mapinfo.authservice.multitenant.web;

import br.com.mapinfo.authservice.multitenant.MultiTenantConstants;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {
        Map<String, Object> pathVars = (Map<String, Object>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (pathVars.containsKey("tenantid")) {
            req.setAttribute(MultiTenantConstants.TENANT_KEY, pathVars.get("tenantid"));
        } else {
            req.setAttribute(MultiTenantConstants.TENANT_KEY, MultiTenantConstants.DEFAULT_TENANT_ID);
        }

        return true;
    }

}
