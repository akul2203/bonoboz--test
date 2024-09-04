package com.user.management.config;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class InvalideUserAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		String r= "{\r\n"
				+ "    \"status\": 401,\r\n"
				+ "    \"error\": \"Unauthorized\",\r\n"
				+ "    \"message\": \"Not Authorized\"\r\n"
				+ "}";
		PrintWriter writer = response.getWriter();
		writer.write(r);
		writer.close();
	}

}