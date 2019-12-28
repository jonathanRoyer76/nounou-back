package com.nounou.security;

import java.util.Optional;
import com.nounou.entities.User;
import com.nounou.interfacesRepositories.IRepoUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

/**
 * UserDetailsServiceImpl
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IRepoUsers _userRepository;

    @Override
    public UserDetails loadUserByUsername(final String p_userName) throws UsernameNotFoundException { // NOPMD by jonathan on 21/07/2019 12:50
        final Optional<User> user = _userRepository.findByUserName(p_userName);
        if (!user.isPresent()) {
        	throw new UsernameNotFoundException(p_userName);
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUserName(), user.get().getPassword(), emptyList());
    }

    
}