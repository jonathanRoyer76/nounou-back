package com.nounou.security;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private BCryptPasswordEncoder _passwordEncoder;

    private String passwordToEncrypt = "";

    /**
     * @param passwordToEncrypt the passwordToEncrypt to set
     */
    public void setPasswordToEncrypt(final String p_passwordToEncrypt) {
        this.passwordToEncrypt = p_passwordToEncrypt;
    }

    @Override
    public Authentication authenticate(final Authentication p_auth){
        
    	UsernamePasswordAuthenticationToken returnObject = null;
    	
        if (p_auth != null){
            final String userName = p_auth.getName();            
            final Collection<? extends GrantedAuthority> authoritiesList = p_auth.getAuthorities();

            final Optional<User> optionUser = this._repoUser.findByUserName(userName);
            if (optionUser.isPresent()){
            	final User user = optionUser.get();
                if (this._passwordEncoder.matches(passwordToEncrypt, user.getPassword())){
                	returnObject = new UsernamePasswordAuthenticationToken(userName, user.getPassword(), authoritiesList);
                }
            }
        }

        return returnObject;
    }
}