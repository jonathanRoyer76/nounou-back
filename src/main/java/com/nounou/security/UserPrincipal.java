package com.nounou.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nounou.entities.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserPrincipal
 */
public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 1L;
    /**
     * User to manipulate
     */
    private final User user;
    
    public User getUser() {
		return user;
	}

	/**
	 * Default constructor
	 * @param pUser
	 */
    public UserPrincipal(final User pUser){
        this.user = pUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<>();

        if (this.user.getRole().getName().contains("Admin")) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }            
        if (this.user.getRole().getName().contains("Nounou")) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_NOUNOU"));
        }   
        if (this.user.getRole().getName().contains("Guest")){
        	authorities.add(new SimpleGrantedAuthority("ROLE_GUEST"));
        }
        if (this.user.getRole().getName().contains("User")) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }

    
}