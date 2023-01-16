package com.nicholasung.easyBook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class WebSecurityConfig {
	
	private UserDetailsService userDetailsService;
	
	// Add BCrypt Bean
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.
			// .authorizeRequests(): Allows restricting access based upon the HttpServletRequest
	        authorizeRequests()
	        	// .antMatchers("/css/**", "/js/**, "/registration"): PathMatcher implementation for Ant-style path patterns.
	            .antMatchers("/css/**", "/js/**", "/registration").permitAll()
	            // Any URL that starts with "/admin" requires the user to have ROLE_ADMIN
	            .antMatchers("/admin/**").access("hasRole('ADMIN')")
	            // .anyRequest(): Maps any request
	            // .authenticated(): Specify URLs that are allowed by authenticated users only
	            .anyRequest().authenticated()
	            .and()
	        // .formLogin(): Specifies to support form-based authentication. Now, our users are going to be authenticated via a FORM
	        .formLogin()
	        	// .loginPage("/login"): Specifies the URL to send users to if login is required
	            .loginPage("/login")
	            .permitAll()
	            .and()
	        // logout(): Provides logout support. The default is that accessing the URL "/logout" will log the user out by 
	        // invalidating the HTTP Session,cleaning up any rememberMe() authentication that was configured, clearing the 
	        // SecurityContextHolder, and then redirect to "/login?success".
	        .logout()
	            .permitAll();
		
		return http.build();
	}
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
}