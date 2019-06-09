package com.nounou.security;

import java.util.Collection;
import java.util.Optional;

import com.nounou.entities.User;
import com.nounou.interfacesRepositories.IRepoUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthenticationManagerImpl
 */
@Service
public class AuthenticationManagerImpl implements AuthenticationManager {

    @Autowired
    private IRepoUsers _repoUser;

    @Autowired
    BCryptPasswordEncoder _passwordEncoder;

    private String passwordToEncrypt = "";

    /**
     * @param passwordToEncrypt the passwordToEncrypt to set
     */
    public void setPasswordToEncrypt(String p_passwordToEncrypt) {
        this.passwordToEncrypt = p_passwordToEncrypt;
    }

    @Override
    public Authentication authenticate(Authentication p_auth) throws AuthenticationException {
        
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