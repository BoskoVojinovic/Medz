package com.skenons.med;

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
//@Order(1)
public class SecurityPatient extends WebSecurityConfigurerAdapter //PATIENT LOGIN!
{	
	
	@Autowired
	private DataSource ds;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication().dataSource(ds)
		//.usersByUsernameQuery("select patient.profile_idnum as principal, profile.password as credentials, true from patient, profile where patient.profile_idnum=profile.idnum and profile_idnum=?")
		.authoritiesByUsernameQuery("select idnum as principal, 'NONE' as role from profile where idnum=?")
		.usersByUsernameQuery("select idnum as principal, password as credentials, true from profile where idnum=?")
		//.authoritiesByUsernameQuery("select ifnull((select profile_idnum as principal, type as role from employee where profile_idnum=?),(select idnum as principal, 'NONE' as role from profile where idnum=?))")
		//.authoritiesByUsernameQuery("select idnum as principal, ifnull((select type as role from employee where profile_idnum=?,'NONE') as role, from profile, where idnum=?")
		//.authoritiesByUsernameQuery("select profile.idnum as principal, ifnull(employee.type,'NONE') as role from profile,employee where employee.profile_idnum=profile.idnum and profile.idnum=?")
		//.authoritiesByUsernameQuery("select profile.idnum as principal, ifnull(employee.type,'NONE') as role from profile,employee where employee.profile_idnum=profile.idnum and profile.idnum=?")
		.passwordEncoder(passEnc()).rolePrefix("ROLE_");
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
				.and().formLogin().loginPage("/plogin").permitAll().defaultSuccessUrl("/profile")
				.and().logout().logoutSuccessUrl("/");
	}
	
	@Bean
	public PasswordEncoder passEnc()
	{
		return new BCryptPasswordEncoder();
	}
}
