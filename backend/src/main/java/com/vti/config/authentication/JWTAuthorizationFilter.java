package com.vti.config.authentication;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.vti.service.JWTTokenService;

public class JWTAuthorizationFilter extends GenericFilterBean {
	
	@Override
	public void doFilter(
			ServletRequest servletRequest, 
			ServletResponse servletResponse, 
			FilterChain filterChain) throws IOException, ServletException {
	
		Authentication authentication = JWTTokenService.parseTokenToUserInformation((HttpServletRequest) servletRequest);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(servletRequest, servletResponse);
	}
}

