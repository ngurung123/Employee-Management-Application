package com.example.springbootbackend.jwtSecurity;

import java.util.Date;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;

@Component
public class JWTGenerator {

	public String generateToken(Authentication authentication) throws InvalidKeyException {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

		@SuppressWarnings("deprecation")
		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET).compact();

		return token;
	}

	public String getUsernameFromJWT(String token) {
		@SuppressWarnings("deprecation")
		Claims claims = Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	@SuppressWarnings("deprecation")
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect" + e.getMessage());
		}
	}

}
