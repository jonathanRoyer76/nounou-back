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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.nounou.entities.User;
import com.nounou.interfacesRepositories.IRepoUsers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWTAuthenticationFilter
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private final AuthenticationManagerImpl _authenticationManager;

    private final IRepoUsers _repoUser;

    public JWTAuthenticationFilter(final AuthenticationManagerImpl manager
    , final IRepoUsers p_repoUsers
    ){
    	super();
        this._authenticationManager = manager;
        this._repoUser = p_repoUsers;
       }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest req,
                                                final HttpServletResponse res) {

        final String username = req.getParameter("userName");
        
        if (StringUtils.isEmpty(username) || username == null) {
        	logger.warn("UserName absent dans la requète");
        }
            
        final String password = req.getParameter("password");
        if (StringUtils.isEmpty(password) || password == null) {
            logger.warn("Password absent dans la requète");
        }


        Collection<? extends GrantedAuthority> authoritiesList = null;

        // To determine a list of authorities for a user
        final Optional<User> optionUser = this._repoUser.findByUserName(username);
        if (optionUser.isPresent()){
            final User user = optionUser.get();
            final UserPrincipal userPrincipal = new UserPrincipal(user);
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
    public Authentication attemptAuthentication(final String p_userName,
                                                final String p_password) {

        if (StringUtils.isEmpty(p_userName) || p_userName == null) {
        	logger.warn("UserName absent dans la requète");
        }
        if (StringUtils.isEmpty(p_password) || p_password == null) {
        	logger.warn("Password absent dans la requète");
        }
            

        Collection<? extends GrantedAuthority> authoritiesList = null;

        // To determine a list of authorities for a user
        final Optional<User> optionUser = this._repoUser.findByUserName(p_userName);
        if (optionUser.isPresent()){
        	final User user = optionUser.get();
            final UserPrincipal userPrincipal = new UserPrincipal(user);
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
    protected void successfulAuthentication(final HttpServletRequest req,
                                            final HttpServletResponse res,
                                            final FilterChain chain,
                                            final Authentication auth) throws IOException, ServletException {

        final String token = generateToken(auth);        

        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }
    
    public String generateToken(final Authentication p_auth) {
    	
    	String returnValue = "";
    	try{

            final List<String> roles = p_auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

            logger.info("Construction du token");
            returnValue = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SIGNING_KEY)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(p_auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .claim("rol", roles)
                .compact();
        }catch(Exception e){
            logger.error(e.toString());
        }
    	return returnValue;
    	
    }
}