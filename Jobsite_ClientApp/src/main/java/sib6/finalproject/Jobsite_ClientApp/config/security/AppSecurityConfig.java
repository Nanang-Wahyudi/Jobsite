package sib6.finalproject.Jobsite_ClientApp.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class AppSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.authorizeRequests(
						auth -> auth
								.antMatchers("/css/**", "/img/**", "/js/**")
								.permitAll()
								.antMatchers("/login/**", "/register/**", "/register-company/**", "/register-admin/**",
										"/forgot-password/**")
								.permitAll()
								.anyRequest()
								.authenticated())
				.formLogin(
						login -> login
								.loginPage("/login")
								.loginProcessingUrl("/login")
								.successForwardUrl("/")
								.failureForwardUrl("/login?error=true")
								.permitAll())
				.logout(
						logout -> logout
								.logoutUrl("/logout")
								.permitAll())
				.build();
	}

}
