package com.ynsdrnks.simplejpaonetoone.security.config;

import com.ynsdrnks.simplejpaonetoone.exception.CustomAccessDeniedHandler;
//import com.ynsdrnks.simplejpaonetoone.security.filter.JWTAuthenticationFilter;
//import com.ynsdrnks.simplejpaonetoone.security.filter.JWTAuthorizationFilter;
import com.ynsdrnks.simplejpaonetoone.security.filter.JwtRequestFilter;
import com.ynsdrnks.simplejpaonetoone.security.services.User_DetailsService;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import static com.ynsdrnks.simplejpaonetoone.security.filter.SecurityConstants.SIGN_UP_URL;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private User_DetailsService userDetailsService;


	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
				.authorizeRequests().antMatchers("/authenticate").permitAll().anyRequest().authenticated().
				and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

//		http.cors().and().csrf().disable().authorizeRequests()
//        .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll().anyRequest().authenticated().and()
//                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
//                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
//                // this disables session creation on Spring Security
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http
//				.authorizeRequests()
//				.antMatchers("/").permitAll()
//				.antMatchers("/login").permitAll()
//				.antMatchers("/register").permitAll()
//				.antMatchers("/resources/**", "/static/**", "/static.css/**", "/js/**", "/static.images/**").permitAll()
//				.antMatchers(HttpMethod.POST,SIGN_UP_URL).permitAll()
//				.anyRequest().authenticated()
//				.and()
//				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
//				.addFilter(new JWTAuthorizationFilter(authenticationManager()))
//				.formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/employee/list")
//				.and()
//				.logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//				.logoutSuccessUrl("/login").permitAll().permitAll()
//				.and()
//				.exceptionHandling().accessDeniedPage("/accessdenied")
//				.and()
//				.httpBasic();;
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
