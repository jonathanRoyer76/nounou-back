package com.nounou.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWTAuthenticationFilter
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private AuthenticationManager _authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager manager){
        this._authenticationManager = manager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        // try {
        //     User creds = new ObjectMapper()
        //             .readValue(req.getInputStream(), User.class);
        //     return _authenticationManager.authenticate(
        //             new UsernamePasswordAuthenticationToken(
        //                     creds.getUserName(),
        //                     creds.getPassword(),
        //                     new ArrayList<>())
        //     );
        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }

        String username = req.getParameter("userName");
        if (username == "")
            log.warn("UserName absent dans le header de la requète");
        String password = req.getParameter("password");
        if (password == "")
            log.warn("Password absent dans le header de la requète");
        
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication temp = null;
        try{
            temp = _authenticationManager.authenticate(authenticationToken);
        }catch (Exception e){
            log.error(e.toString());
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
            log.info("Construction du user");
            UserDetails user = (UserDetails)auth.getPrincipal();

            log.info("Construction des rôles");
            List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

            log.info("Construction du token");
            token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SIGNING_KEY)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                // .setIssuer(SecurityConstants.TOKEN_ISSUER)
                // .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("rol", roles)
                .compact();
        }catch(Exception e){
            log.error(e.toString());
        }

        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }
}