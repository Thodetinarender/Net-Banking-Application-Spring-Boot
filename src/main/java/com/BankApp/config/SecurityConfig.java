package com.BankApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
//@EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private UserDetailsService detailsService;

    @Autowired
	CustomSuccessHandler customSuccessHandler;
    
    @Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider daoAuthenticationProvider =new DaoAuthenticationProvider();
    	daoAuthenticationProvider.setUserDetailsService(detailsService);
    	daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    	
    	return daoAuthenticationProvider;
    }
    

   
    
    @Bean
    public SecurityFilterChain filterChains(HttpSecurity httpSecurity) throws Exception {
    	
    	 httpSecurity.csrf(c -> c.disable())
    	
    	.authorizeHttpRequests(request -> request
    			.requestMatchers("/manager").hasAuthority("MGR")
    			.requestMatchers("/user").hasAuthority("CLERK")
				.requestMatchers("/api/loginmgt/registration").permitAll()
				.requestMatchers("/api/accountmgt/**").hasAnyRole("MGR")
		    	.requestMatchers("/api/usermgt/**").hasAnyRole("MGR")
		    	.requestMatchers("/api/transactions/**").hasAnyRole("MGR", "CLERK")
		    	.requestMatchers("/api/loginmgt/**").hasAnyRole("MGR", "CLERK")
				.anyRequest()
				.authenticated())
    	
    	
    	.formLogin(form -> form
    			.loginPage("/api/loginmgt/login")
    			.loginProcessingUrl("/signinpage")
    			.successHandler(customSuccessHandler)
				.permitAll())
    	
    	.logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/api/loginmgt/login?logout").permitAll());
    	 
    		return httpSecurity.build();
    	
    }
    
	@Autowired
	public void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(detailsService);
	}
	
	

    


    
}
