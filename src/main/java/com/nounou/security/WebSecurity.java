package com.nounou.security;

import com.nounou.interfacesRepositories.IRepoUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * WebSecurity
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter{

    @Autowired
    private IRepoUsers _repoUser;
    @Autowired
    private UserDetailsService _userDetailsService;
    @Autowired
    private BCryptPasswordEncoder _bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManagerImpl _authImpl;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(_userDetailsService).passwordEncoder(_bCryptPasswordEncoder);

    }

    @Override
    public void configure(HttpSecurity p_http) throws Exception{        

        p_http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/users/public", "/users/sign-up").permitAll()  // Chemins accessibles publiquement
            .anyRequest().authenticated()
            .and()
            .addFilter(new JWTAuthenticationFilter(this._authImpl, this._repoUser))
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}