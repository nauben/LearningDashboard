package com.mosbach.ld.filters;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;
import com.mosbach.ld.config.JwtConfigurer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTokenVerifier extends OncePerRequestFilter {

	private final SecretKey secretKey;
    private final JwtConfigurer jwtConfig;

    public JwtTokenVerifier(SecretKey secretKey,
                            JwtConfigurer jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
		
        
        try {
        	/**
        	 * TODO Wird hier auch tatsächlich der User geprüft?
        	 */

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();

            UUID id = UUID.fromString(body.getSubject());
            Date expirationDate = body.getExpiration();

            if(id != null 
            		&& SecurityContextHolder.getContext().getAuthentication() == null
            		&& expirationDate.before(new Date(System.currentTimeMillis()))) {
            	
            	
	            @SuppressWarnings("unchecked")
				List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
	
	            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
	                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
	                    .collect(Collectors.toSet());
	
	            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
	                    id,
	                    null,
	                    simpleGrantedAuthorities
	            );
	            authentication.setDetails(
	            		new WebAuthenticationDetailsSource().buildDetails(request)
					);
	            
	            SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }

        filterChain.doFilter(request, response);
    }
	
}
