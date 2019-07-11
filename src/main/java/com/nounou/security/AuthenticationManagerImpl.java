package com.nounou.security;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nounou.entities.User;
import com.nounou.interfacesRepositories.IRepoUsers;

/**
 * AuthenticationManagerImpl
 */
@Service
public class AuthenticationManagerImpl implements AuthenticationManager {

    @Autowired
    private IRepoUsers _repoUser;

    @Autowired
    BCryptPasswordEncoder _passwordEncoder;
    
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private String passwordToEncrypt = "";

    /**
     * @param passwordToEncrypt the passwordToEncrypt to set
     */
    public void setPasswordToEncrypt(String p_passwordToEncrypt) {
        this.passwordToEncrypt = p_passwordToEncrypt;
    }

    @Override
    public Authentication authenticate(Authentication p_auth) throws AuthenticationException {
        logger.debug("requete de connexion recue");
        if (p_auth != null){
            String userName = p_auth.getName();            
            Collection<? extends GrantedAuthority> authoritiesList = p_auth.getAuthorities();

            Optional<User> optionUser = this._repoUser.findByUserName(userName);
            if (optionUser.isPresent()){
                User user = optionUser.get();
                if (this._passwordEncoder.matches(passwordToEncrypt, user.getPassword())){
                    return new UsernamePasswordAuthenticationToken(userName, user.getPassword(), authoritiesList);
                }
            }
        }

        return null;
    }
}