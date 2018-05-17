package br.com.mapinfo.authservice.security;

import br.com.mapinfo.authservice.user.User;
import br.com.mapinfo.authservice.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class CustomSuccessAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {

    private UserService userService;

    public CustomSuccessAuthenticationHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        user.setLastLogin(Date.from(Instant.now()));
        userService.save(user);

        super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
    }
}
