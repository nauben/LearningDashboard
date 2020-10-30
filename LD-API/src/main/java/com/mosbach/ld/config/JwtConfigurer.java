package com.mosbach.ld.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.net.HttpHeaders;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfigurer {

	private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;

    public JwtConfigurer() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Integer getTokenExpirationAfterDays() {
        return tokenExpirationAfterDays;
    }

    public void setTokenExpirationAfterDays(Integer tokenExpirationAfterDays) {
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
    }

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
	
//    @Bean
//    public static JwtConfigurer getJwtConfig() {
//    	return new JwtConfigurer();
//    }
}
