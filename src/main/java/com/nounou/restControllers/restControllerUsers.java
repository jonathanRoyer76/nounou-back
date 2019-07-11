package com.nounou.restControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nounou.constants.EnumExceptions;
import com.nounou.constants.EnumLogType;
import com.nounou.constants.EnumUserRoles;
import com.nounou.entities.Role;
import com.nounou.entities.User;
import com.nounou.interfacesRepositories.IRepoLoggers;
import com.nounou.interfacesRepositories.IRepoRoles;
import com.nounou.interfacesRepositories.IRepoUsers;
import com.nounou.security.AuthenticationManagerImpl;
import com.nounou.security.JWTAuthenticationFilter;
import com.nounou.security.SecurityConstants;
import com.nounou.services.LoggerService;

import utils.JWTToken;

/**
 * restControllerUsers
 */
@RestController
@CrossOrigin(origins = "*")
public class restControllerUsers {
	
	@Autowired
    private AuthenticationManagerImpl _authImpl;
    @Autowired
    private IRepoUsers _repoUsers;   
    @Autowired
    private IRepoRoles _repoRoles; 
    @Autowired
    private BCryptPasswordEncoder _passwordEncoder;
    @Autowired 
    private LoggerService _loggerService;

    @PostMapping(value = "users/add")
    public User add(final User p_user) {

        if (p_user != null) {
            p_user.setPassword(_passwordEncoder.encode(p_user.getPassword()));
            return this._repoUsers.save(p_user);
        }else {
        	this._loggerService.error("restControllerUser", EnumExceptions.NULLPOINTER, "Objet p_user null", "add");
        }

        return p_user;
    }
    
    /**
     * To login a user and retrieve a JWT token
     * @param p_user contains log in infos
     */
    @PostMapping(value = "sign-in")
    public ResponseEntity<JWTToken> signIn(@RequestBody User p_user) {
    	
    	if (p_user != null) {
    		JWTAuthenticationFilter filter = new JWTAuthenticationFilter(_authImpl, _repoUsers);
        	Authentication authentication = this.getAuthentication(p_user, filter);    	
        	if (authentication != null) {
        		SecurityContextHolder.getContext().setAuthentication(authentication);
        		String token = filter.generateToken(authentication);
        		HttpHeaders headers = new HttpHeaders();
        		headers.add(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        		return ResponseEntity.ok()
        				.headers(headers)
        				.body(new JWTToken(token));    		
        	}else {
        		this._loggerService.error("restControllerUser", EnumExceptions.NULLPOINTER, "Objet authentification null", "signIn");    		
        	}
    	}else {
    		this._loggerService.error("restControllerUser", EnumExceptions.NULLPOINTER, "Objet p_user null", "signIn");
    	}
    	return null;
    }
    
    /**
     * 
     * @param p_user
     * @return authenticated object
     */
    private Authentication getAuthentication(User p_user, final JWTAuthenticationFilter p_filter) {
    	if (!StringUtils.isEmpty(p_user.getUserName()) && !StringUtils.isEmpty(p_user.getUserName())) {
    		Authentication authentication = p_filter.attemptAuthentication(p_user.getUserName(), p_user.getPassword());
    		return authentication;
    	}else {
    		this._loggerService.error("restControllerUser", EnumExceptions.EMPTYSTRING, "userName et/ou password vide", "getAuthentication");
    	}
    	return null;
    }

    /**
     * Endpoint for registering a new user
     * @param p_user 
     */
    @PostMapping(value = "sign-up")    
    public void signUp(User p_user, final int p_roleId){
        
    	if (p_user != null) {
    		if(p_roleId != 0) {
    			p_user.setRole(this.getRole(p_roleId));
    	        this.add(p_user);
    		}else {
    			this._loggerService.error("restControllerUser", EnumExceptions.EMPTYSTRING, "Id role absent", "signUp");
    		}
    	}else {
    		this._loggerService.error("restControllerUser", EnumExceptions.NULLPOINTER, "p_user vide", "signUp");
    	}
    }
    
    /**
     * @param p_roleId the id od role to find
     * @return roles for a user or default
     */
    private Role getRole(final int p_roleId) {
    	
    	Optional<Role> optionRole = this._repoRoles.findById(p_roleId);
    	if (optionRole.isPresent()) {
    		// Wanted role founded
    		return optionRole.get();
    	}else {
    		this._loggerService.error("restControllerUser", EnumExceptions.NULLPOINTER, "optionRole vide", "getRole");
    	}
    	optionRole = this._repoRoles.findByName(EnumUserRoles.USER.toString());
    	if (optionRole.isPresent()) {
    		// Default role returned
    		return optionRole.get();
    	}else {
    		this._loggerService.error("restControllerUser", EnumExceptions.NULLPOINTER, "optionRole vide", "getRole");
    	}
    	
    	return new Role();
    }

    /**
     * Find and return every User in DB
     * @return List<User>
     */
    @GetMapping("users/getAll")
    public List<User> getAll() {

        ArrayList<User> usersList = this._repoUsers.findAll();
        return usersList;

    }

    /**
     * Find and return a User by his id
     * @return List<User>
     */
    @GetMapping("users/get/{id}")
    public User getById(@PathVariable("id") int p_id) {

        if (p_id != 0){
           Optional<User> optionUser = this._repoUsers.findById(p_id);
           if (optionUser.isPresent()){
               User user = optionUser.get();
               return user;
           }else {
        	   this._loggerService.error("restControllerUser", EnumExceptions.NULLPOINTER, "optionUser", "getById");
           }
        }else {
        	this._loggerService.error("restControllerUser", EnumExceptions.NULLPOINTER, "p_id est vide", "getById");
        }

        return null;

    }

    @PostMapping(value = "users/update")
    public User update(@RequestBody User p_user) {

    	if(p_user != null) {
    		p_user = this.add(p_user);
    	}else {
    		this._loggerService.error("restControllerUser", EnumExceptions.NULLPOINTER, "p_user est vide", "update");
    	}
    	
        return p_user;
        
    }
}