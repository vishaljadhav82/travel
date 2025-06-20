package com.travel.web.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    Authentication authentication) throws IOException, ServletException {

	    // Get the user's roles
	    boolean isAdmin = authentication.getAuthorities().stream()
	                                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
	    boolean isUser = authentication.getAuthorities().stream()
	                                   .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));

	    // Get context path ("" if ROOT.war, "/busapp" if deployed as /busapp)
	    String contextPath = request.getContextPath();

	    // Redirect based on the role
	    if (isAdmin) {
	        response.sendRedirect(contextPath + "/admin");
	    } else if (isUser) {
	        response.sendRedirect(contextPath + "/user");
	    } else {
	        response.sendRedirect(contextPath + "/public");
	    }
	}
}
