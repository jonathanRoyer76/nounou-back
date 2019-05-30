package com.nounou.services;

import java.util.Optional;

import com.nounou.entities.CustomUserDetails;
import com.nounou.entities.User;
import com.nounou.interfacesRepositories.IRepoUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IRepoUsers repoUsers;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = this.repoUsers.findByUserName(username);    
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Nom d'utilisateur non trouvé"));

        return optionalUser.map(CustomUserDetails::new).get();
    }

}