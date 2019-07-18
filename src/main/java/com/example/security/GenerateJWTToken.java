package com.example.security;
import java.util.Date;
import org.springframework.stereotype.Component;

import com.example.customException.ApplicationException;
import com.example.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class GenerateJWTToken {

	private long EXPIRATIONTIME = 60*10000;
	  static final String SECRET = "ThisIsASecret";
	  static final String TOKEN_PREFIX = "Bearer";
	  static final String HEADER_STRING = "Authorization";

	public String getToken(User databaseUser) {
		String jwtToken = "";

		jwtToken = Jwts.builder().setSubject(databaseUser.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, SECRET).compact();

		return jwtToken;
	}
	
	public boolean isValidToken(String token) {
		boolean result = false;
		Date cusrrentDate = new Date(System.currentTimeMillis());
		try {
			final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
			if (claims.getExpiration().compareTo(cusrrentDate) > 0 || claims.getExpiration().compareTo(cusrrentDate) == 0) {
				   result = true;
	            }
		   } catch (Exception e) {
			   throw new ApplicationException("Token is not valid", 408);//request timeout
		}
		return result;
	}
	
}
