package com.mosbach.ld.config;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSecretKeyConfigurer {

	private final JwtConfigurer jwtConfigurer;

    @Autowired
    public JwtSecretKeyConfigurer(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtConfigurer.getSecretKey().getBytes());
    }
	
}
