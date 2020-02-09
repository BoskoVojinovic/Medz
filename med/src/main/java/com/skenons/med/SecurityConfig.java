package com.skenons.med;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.skenons.med.data.Profile;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter //PATIENT LOGIN!
{	
	
	@Autowired
	private DataSource ds;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication().dataSource(ds)
		.usersByUsernameQuery("select idnum as principal, password as credentials, true from profile where idnum=? and verified=true and deleted=false")
		.authoritiesByUsernameQuery("select idnum as principal, type as role from profile where idnum=?")
		.passwordEncoder(passEnc())
		;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception //config authentication for every http request
	{
		http.authorizeRequests().antMatchers(
				"/",
				"/register",
				"/login",
				"/verify/**",
				"/debugFill",
				"/about",
				"/css/**",
				"/webjars/**"
				).permitAll()
				//.antMatchers("/SomePageJustForAdmins").hasAnyRole(ProfileType.ADMIN_CLINIC+","+ProfileType.ADMIN_CENTER)
				.anyRequest().authenticated()			
				.and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/profile")
				.and().logout().logoutSuccessUrl("/");
	}
	
	public static String getVerifyToken(Profile p)
	{
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return DatatypeConverter.printHexBinary(md.digest((p.getIDNum()+"Magic Sauce").getBytes())).toUpperCase();
		
	}
	
	@Bean
	public static PasswordEncoder passEnc()
	{
		return new BCryptPasswordEncoder();
	}
}
