package com.nounou.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * config
 */
@Configuration
@EnableWebSecurity
public class config extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable();
        // http.authorizeRequests()
        //     .antMatchers("**/users/**").authenticated()
        //     .anyRequest().permitAll()
        //     .and()
        //     .formLogin()
        //     // .loginPage("/login")
        //     .permitAll();
        
    }

}