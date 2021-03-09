package com.ynsdrnks.simplejpaonetoone.security.config;

import com.ynsdrnks.simplejpaonetoone.exception.CustomAccessDeniedHandler;
//import com.ynsdrnks.simplejpaonetoone.security.filter.JWTAuthenticationFilter;
//import com.ynsdrnks.simplejpaonetoone.security.filter.JWTAuthorizationFilter;
import com.ynsdrnks.simplejpaonetoone.security.filter.JwtRequestFilter;
import com.ynsdrnks.simplejpaonetoone.security.services.UserDetailsServiceImpl;
import com.ynsdrnks.simplejpaonetoone.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public final static String AUTHORIZATION_HEADER = "Authorization";
	@Autowired
	private JwtUtil tokenProvider;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;


	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests().antMatchers("/css/**").permitAll()
				.antMatchers("/images/**", "/css/**",
		 				"/js/**",
						"/img/**",
						"/**/favicon.ico",
						"/webjars/**",
						"/register").permitAll()
				.antMatchers("/login", "/register").permitAll()
//				.antMatchers("/admin-panel").hasRole("ADMIN").anyRequest().authenticated()
				.and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll().defaultSuccessUrl("/employee/list").permitAll()
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login").permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/accessdenied")
 				.and()
				.httpBasic();
		http.csrf().disable();

	}

	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
		return new CustomAccessDeniedHandler();
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}




}