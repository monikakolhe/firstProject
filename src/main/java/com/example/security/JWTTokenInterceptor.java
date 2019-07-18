package com.example.security;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.customException.ApplicationException;

@Component
public class JWTTokenInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private GenerateJWTToken generateJWTToken;

	@SuppressWarnings("null")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// final HttpServletRequest req = (HttpServletRequest) request;
		final String authHeader = request.getHeader("authorization");
		final String authCT = request.getHeader("content-type");
		Enumeration<String> headers = request.getHeaderNames();
		System.out.println("Value of header "+headers);
		System.out.println("req.getHeaderNames()=>"+request.getHeaderNames());
		System.out.println("req.getHeaderNames()=>"+authCT);
		boolean result = false;

		if (null != authHeader || authHeader.startsWith("Bearer ")) {

			final String token = authHeader.substring(7);
			if (!StringUtils.isEmpty(token) && generateJWTToken.isValidToken(token.trim())) {
				result = true;
				response.setStatus(HttpStatus.OK.value());
			}
		} else {
			throw new ApplicationException("Missing or invalid Authorization header", 401);
		}
		return result;
	}

}
