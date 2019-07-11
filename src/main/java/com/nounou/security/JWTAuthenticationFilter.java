package com.nounou.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nounou.entities.User;
import com.nounou.interfacesRepositories.IRepoUsers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWTAuthenticationFilter
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private AuthenticationManagerImpl _authenticationManager;

    private IRepoUsers _repoUser;

    public JWTAuthenticationFilter(AuthenticationManagerImpl manager
    , IRepoUsers p_repoUsers
    ){
        this._authenticationManager = manager;
        this._repoUser = p_repoUsers;
       }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {

        String username = req.getParameter("userName");
        
        if (username == "" || username == null)
            logger.warn("UserName absent dans la requète");
        String password = req.getParameter("password");
        if (password == "" || password == null)
            logger.warn("Password absent dans la requète");

        Collection<? extends GrantedAuthority> authoritiesList = null;

        // To determine a list of authorities for a user
        Optional<User> optionUser = this._repoUser.findByUserName(username);
        if (optionUser.isPresent()){
            User user = optionUser.get();
            UserPrincipal userPrincipal = new UserPrincipal(user);
            authoritiesList = userPrincipal.getAuthorities();
        }

        Authentication temp = null;
        try{
            _authenticationManager.setPasswordToEncrypt(password);
            temp = _authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, authoritiesList));
        }catch (Exception e){
            logger.error(e.toString());
        }
        return temp;
    }
    
    // Used for logging
    public Authentication attemptAuthentication(String p_userName,
                                                String p_password) throws AuthenticationException {

        if (p_userName == "" || p_userName == null)
            logger.warn("UserName absent dans la requète");
        if (p_password == "" || p_password == null)
            logger.warn("Password absent dans la requète");

        Collection<? extends GrantedAuthority> authoritiesList = null;

        // To determine a list of authorities for a user
        Optional<User> optionUser = this._repoUser.findByUserName(p_userName);
        if (optionUser.isPresent()){
            User user = optionUser.get();
            UserPrincipal userPrincipal = new UserPrincipal(user);
            authoritiesList = userPrincipal.getAuthorities();
        }

        Authentication temp = null;
        try{
            _authenticationManager.setPasswordToEncrypt(p_password);
            temp = _authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(p_userName, p_password, authoritiesList));
        }catch (Exception e){
            logger.error(e.toString());
        }
        return temp;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = generateToken(auth);        

        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }
    
    public String generateToken(Authentication p_auth) {
    	
    	try{

            List<String> roles = p_auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

            logger.info("Construction du token");
            return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SIGNING_KEY)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(p_auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("rol", roles)
                .compact();
        }catch(Exception e){
            logger.error(e.toString());
        }
    	return "";
    	
    }
}