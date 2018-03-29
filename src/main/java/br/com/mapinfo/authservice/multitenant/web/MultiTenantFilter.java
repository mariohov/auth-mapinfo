package br.com.mapinfo.authservice.multitenant.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static br.com.mapinfo.authservice.multitenant.MultiTenantConstants.*;

public class MultiTenantFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getHeader(TENANT_KEY) != null) {
            req.setAttribute(CURRENT_TENANT_IDENTIFIER, req.getHeader(TENANT_KEY));
        } else {
            req.setAttribute(CURRENT_TENANT_IDENTIFIER, DEFAULT_TENANT_ID);
        }

//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        String tenantHeader = request.getHeader(TENANT_KEY);
//        if (tenantHeader != null && !tenantHeader.isEmpty()) {
//            request.setAttribute(CURRENT_TENANT_IDENTIFIER, request.getHeader(TENANT_KEY));
//        } else {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.getWriter().write("{\"error\": \"No tenant header supplied\"}");
//            response.getWriter().flush();
//            return;
//        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}

}
