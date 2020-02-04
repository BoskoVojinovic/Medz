/*package com.skenons.med;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityStaff extends WebSecurityConfigurerAdapter //STAFF LOGIN!
{	
	
	@Autowired
	private DataSource ds;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication().dataSource(ds)
		.usersByUsernameQuery("select employee.profile_idnum as principal, profile.password as credentials, true from employee, profile where employee.profile_idnum=profile.idnum and profile_idnum=?")
		.authoritiesByUsernameQuery("select profile_idnum as principal, type as role from employee where profile_idnum=?")
		.passwordEncoder(passEnc2());
		;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception //config authentication for every http request
	{
		http.authorizeRequests().antMatchers(
				"/",
				"/register",
				"/plogin",
				"/slogin",
				"/about",
				"/css/**",
				"/webjars/**"
				).permitAll().anyRequest().authenticated()
				.and().formLogin().loginPage("/slogin").permitAll().defaultSuccessUrl("/profile")
				.and().logout().logoutSuccessUrl("/");
	}
	
	@Bean
	public PasswordEncoder passEnc2()
	{
		return new BCryptPasswordEncoder();
	}
}*/
