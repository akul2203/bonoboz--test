package com.user.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.user.management.model.RoleName;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private SecurityFilter filter;

	@Bean
	 AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {

		return authConfig.getAuthenticationManager();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		;
		return provider;
	}

	// Authorization
		@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		    http
		        .csrf(csrf -> csrf.disable())
		        .cors(cors -> cors.disable())
		        .authorizeHttpRequests(authorize -> authorize
		            .requestMatchers("/user/login").permitAll()
		            .requestMatchers("/user/admin/**").hasAuthority(RoleName.ADMIN.name())
		            .anyRequest().authenticated()
		        )
		        .exceptionHandling(exception -> exception
		            .authenticationEntryPoint(authenticationEntryPoint)
		        )
		        .sessionManagement(session -> session
		            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		        )
		        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

		    return http.build();
		}

}
