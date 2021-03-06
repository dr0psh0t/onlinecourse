package com.onlinecourse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.onlinecourse.entity.Roles;

@Configuration
@EnableWebSecurity
public class CourseSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CourseSecurityConfig(
            UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//	always use hasAuthority
    	
        http.authorizeRequests()
            .antMatchers("/Home").hasAnyAuthority(Roles.INSTRUCTOR.name(), Roles.STUDENT.name(), Roles.ADMIN.name(), Roles.MODERATOR.name())
            .antMatchers("/Update/UpdateUserForm").hasAnyAuthority(Roles.INSTRUCTOR.name(), Roles.STUDENT.name(), Roles.ADMIN.name(), Roles.MODERATOR.name())
            .antMatchers("/Admin/**").hasAnyAuthority(Roles.ADMIN.name(), Roles.MODERATOR.name())
            .antMatchers("/Instructor/**").hasAuthority(Roles.INSTRUCTOR.name())
            .antMatchers("/Student/**").hasAuthority(Roles.STUDENT.name())
            .and()
                .formLogin()
                .loginPage("/Login")
                .loginProcessingUrl("/authenticateTheUser")
                .defaultSuccessUrl("/Home")
                .permitAll()
            .and()
            	.logout()
	            	.logoutUrl("/Logout")
	            	.invalidateHttpSession(true)
	            	.permitAll()	//	adds logout support
            .and()
            	.exceptionHandling().accessDeniedPage("/AccessDenied");
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
