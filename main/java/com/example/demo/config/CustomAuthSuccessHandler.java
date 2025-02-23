package com.example.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String redirectUrl = (String) request.getSession().getAttribute("REDIRECT_URL");
        if (redirectUrl != null) {
            request.getSession().removeAttribute("REDIRECT_URL");
            response.sendRedirect(redirectUrl);
        } else {
            response.sendRedirect("/chat/continue"); // Default fallback after login
        }
    }
}
