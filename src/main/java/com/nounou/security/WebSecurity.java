package com.nounou.security;

import java.util.Arrays;

import com.nounou.interfacesRepositories.IRepoUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
/**
 * WebSecurity
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter{

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_NOUNOU = "ROLE_NOUNOU";
    public static final String ROLE_GUEST = "ROLE_GUEST";
    public static final String ROLE_USER = "ROLE_USER";

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
            .antMatchers("/admin/**").hasAuthority(ROLE_ADMIN)
            .antMatchers("/nounou/**").hasAnyAuthority(ROLE_ADMIN, ROLE_NOUNOU)
            .antMatchers("/users/**").hasAnyAuthority(ROLE_ADMIN, ROLE_NOUNOU)
            .antMatchers("/users/sign-up", "/", "/login").permitAll()  // Chemins accessibles publiquement
            .anyRequest().authenticated()
            .and()
            .addFilter(new JWTAuthenticationFilter(this._authImpl, this._repoUser))
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        p_http.cors();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "UPDATE", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}