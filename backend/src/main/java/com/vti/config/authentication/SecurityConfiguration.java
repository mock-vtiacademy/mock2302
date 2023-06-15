package com.vti.config.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;
import com.vti.service.IUserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {	
	@Autowired
	private IUserService service;	
		
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public AuthenticationManager authenticationManager() {
		 return new ProviderManager(authenticationProvider());
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(service);
        auth.setPasswordEncoder(passwordEncoder());
        
        return auth;
    }
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
        return http.csrf().disable()
        		.authorizeHttpRequests(auth -> {
					try {
						auth
							.requestMatchers("/api/v1/login").permitAll()
							.requestMatchers("/api/v1/users/profile").authenticated()
							.requestMatchers("/api/v1/users/**").permitAll()
							
							//APIs --------------------------------------------							
							
							
							///////////////////////////////////////////////////
							.anyRequest().authenticated()
							//.anyRequest().permitAll()
							.and()
							.httpBasic()
							.and()
							.cors();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}        				        				
        		)
        		.addFilterBefore(
        				new JWTAuthenticationFilter("/api/v1/login", authenticationManager(), service), 
        				UsernamePasswordAuthenticationFilter.class) 
        		.addFilterBefore(
        				new JWTAuthorizationFilter(), 
        				UsernamePasswordAuthenticationFilter.class)
        		.exceptionHandling(exceptionHandling ->
                		exceptionHandling.accessDeniedPage("/403"))
        		.build();
    }	
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
	    configuration.applyPermitDefaultValues();
	    
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
