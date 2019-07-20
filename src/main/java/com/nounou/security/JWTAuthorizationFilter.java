package com.nounou.security;

import java.io.IOException;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * JWTAuthorizationFilter
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(final AuthenticationManager authManager){
        super(authManager);
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest req,
                                    final HttpServletResponse res,
                                    final FilterChain chain) throws IOException, ServletException {
        final String header = req.getHeader(SecurityConstants.HEADER_STRING);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        final UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
    
    public UsernamePasswordAuthenticationToken getAuthenticationFromToken(final String p_token) {
    	return this.getAuthentication(p_token);
    }
    
    private UsernamePasswordAuthenticationToken getAuthentication(final String p_token) {
    	
    	UsernamePasswordAuthenticationToken returnObject = null;
    	try {
            final Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(SecurityConstants.SIGNING_KEY)
                .parseClaimsJws(p_token.replace(SecurityConstants.TOKEN_PREFIX, ""));

            final String username = parsedToken
                .getBody()
                .getSubject();

            final List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
                .get("rol")).stream()
                .map(authority -> new SimpleGrantedAuthority((String) authority))
                .collect(Collectors.toList());

            if (!StringUtils.isEmpty(username)) {
                returnObject = new UsernamePasswordAuthenticationToken(username, null, authorities);
            }
        } catch (ExpiredJwtException exception) {
        	LOGGER.warn("Request to parse expired JWT : {} failed : {}", p_token, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
        	LOGGER.warn("Request to parse unsupported JWT : {} failed : {}", p_token, exception.getMessage());
        } catch (MalformedJwtException exception) {
        	LOGGER.warn("Request to parse invalid JWT : {} failed : {}", p_token, exception.getMessage());
        } catch (SignatureException exception) {
        	LOGGER.warn("Request to parse JWT with invalid signature : {} failed : {}", p_token, exception.getMessage());
        } catch (IllegalArgumentException exception) {
        	LOGGER.warn("Request to parse empty or null JWT : {} failed : {}", p_token, exception.getMessage());
        }
    	
    	return returnObject;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest request) {

    	UsernamePasswordAuthenticationToken returnObject = null;
        final String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (!StringUtils.isEmpty(token)) {
        	returnObject = this.getAuthentication(token);
        }

        return returnObject;
    }

}