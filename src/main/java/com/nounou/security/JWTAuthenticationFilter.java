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

import com.nounou.entities.User;
import com.nounou.interfacesRepositories.IRepoUsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        if (username == "")
            logger.warn("UserName absent dans le header de la requète");
        String password = req.getParameter("password");
        if (password == "")
            logger.warn("Password absent dans le header de la requète");

        Collection<? extends GrantedAuthority> authoritiesList = null;

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

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = "";
        try{

            List<String> roles = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

            logger.info("Construction du token");
            token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SIGNING_KEY)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("rol", roles)
                .compact();
        }catch(Exception e){
            logger.error(e.toString());
        }

        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }
}