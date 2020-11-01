package com.mosbach.ld.config;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mosbach.ld.auth.ApplicationUserRole;
import com.mosbach.ld.filters.JwtRequestFilter;
import com.mosbach.ld.services.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	/*
	private final PasswordEncoder passwordEncoder;
    private final UserService applicationUserService;
    private final SecretKey secretKey;
    private final JwtConfigurer jwtConfig;
	
    @Autowired
    public SecurityConfigurer(PasswordEncoder passwordEncoder,
                                     UserService applicationUserService,
                                     SecretKey secretKey,
                                     JwtConfigurer jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
         .csrf().disable()
         .sessionManagement()
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         .addFilter(new JwtEmailAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
         .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig),JwtEmailAndPasswordAuthenticationFilter.class)
         .authorizeRequests()
         .antMatchers(
        		 	"/login",
        		 	"/api/v0.1a/test",
        		 	"/api/v0.1a/authenticate",
					"/api/v0.1a/dhbw-schedule/courses/all",
					"/api/v0.1a/dhbw-schedule/{course}",
					"/api/v0.1a/dhbw-schedule/{course}/upcomingdays"
				).permitAll()
         .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
         .anyRequest()
         .authenticated();
		
		 /*
		  * .and()
				.httpBasic(); //?
		  * 
		  *
		 
		//http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setPasswordEncoder(passwordEncoder);
	    provider.setUserDetailsService(applicationUserService);
	    return provider;
	}	
	*/
	
	 @Autowired
	private UserService userCredentialService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userCredentialService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.cors().disable()
			.authorizeRequests()
				.antMatchers(
						"/api/v0.1a/login",
						"/api/v0.1a/register",
						"/api/v0.1a/test",
						"/api/v0.1a/alexa/endpoint",
						"/api/v0.1a/tasks"
						).permitAll()
				.anyRequest().authenticated()
			.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.httpBasic();
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


}
