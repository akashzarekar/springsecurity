package com.apisecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.apisecurity.roles.RolesAndAuthorities;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.inMemoryAuthentication()
		.passwordEncoder(new BCryptPasswordEncoder())
		.withUser("akash")
		.password("$2a$10$.61yxOm3ZOnoPUtYvilzLOpErIeXCTj9EZ9mGUJW9kW9yV4Zt8br2")
		.roles(RolesAndAuthorities.USER.name())
		.authorities(RolesAndAuthorities.USER.name())
		.and()
		.withUser("priya")
		.password("$2a$10$LbiRmMrDWmpNxy6NTiaqfOD9BUAKVUPoTA15ffbKAvThBvcWxhaeu")
		.roles(RolesAndAuthorities.ADMIN.name())
		.authorities(RolesAndAuthorities.ADMIN.name(),RolesAndAuthorities.USER.name(),RolesAndAuthorities.DBA.name())
		.and()
		.withUser("mahesh")
		.password("$2a$10$Fl0akvtv.zT1z/3oey4ufeHfHvPdR4lToCdIbMgm2aHr3chJN43cu")
		.roles(RolesAndAuthorities.DBA.name())
		.authorities(RolesAndAuthorities.DBA.name());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/","/home")
		.permitAll()
		.antMatchers("/admin/**")
		.hasAuthority(RolesAndAuthorities.ADMIN.name())
		.antMatchers("/dba/**")
		.hasAuthority(RolesAndAuthorities.DBA.name())
		.antMatchers("/user/**")
		.hasAuthority(RolesAndAuthorities.USER.name())
		.anyRequest()
		.fullyAuthenticated()
		.and()
		.formLogin()
		.and()
		.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/home")
		.and()
		.exceptionHandling()
		.accessDeniedPage("/accessDenied");
		
	}

}
