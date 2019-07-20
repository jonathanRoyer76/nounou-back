package com.nounou.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.nounou.constants.EnumExceptions;
import com.nounou.constants.EnumUserRoles;
import com.nounou.entities.Role;
import com.nounou.entities.User;
import com.nounou.interfacesRepositories.IRepoRoles;
import com.nounou.interfacesRepositories.IRepoUsers;
import com.nounou.security.AuthenticationManagerImpl;
import com.nounou.security.JWTAuthenticationFilter;
import com.nounou.security.JWTAuthorizationFilter;
import com.nounou.security.SecurityConstants;
import com.nounou.services.LoggerService;

import utils.JWTToken;

/**
 * restControllerUsers
 */
@RestController
@CrossOrigin(origins = "*")
public class RestControllerUsers {
	
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
    private static String _className = "restControllerUser";

    @PostMapping(value = "users/add")
    public User add(final User p_user) {

    	User returnObject = p_user;
        if (p_user == null) {
        	this._loggerService.error(_className, EnumExceptions.NULLPOINTER, "Objet p_user null", "add");            
        }else {
        	returnObject.setPassword(_passwordEncoder.encode(p_user.getPassword()));
        	returnObject = this._repoUsers.save(p_user);
        }

        return returnObject;
    }
    
    /**
     * To login a user and retrieve a JWT token
     * @param p_user contains log in infos
     */
    @PostMapping(value = "sign-in")
    public ResponseEntity<JWTToken> signIn(@RequestBody final User p_user) {
    	
    	ResponseEntity<JWTToken> returnObject = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	if (p_user != null) {
    		final JWTAuthenticationFilter filter = new JWTAuthenticationFilter(_authImpl, _repoUsers);
        	final Authentication authentication = this.getAuthentication(p_user, filter);    	
        	if (authentication != null) {
        		SecurityContextHolder.getContext().setAuthentication(authentication);
        		final String token = filter.generateToken(authentication);       
        		final HttpHeaders headers = new HttpHeaders();
        		headers.add(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        		returnObject = ResponseEntity.ok()
        				.headers(headers)
        				.body(new JWTToken(token));
        	}else {
        		this._loggerService.error(_className, EnumExceptions.NULLPOINTER, "Objet authentification null", "signIn");    		
        	}
    	}else {
    		this._loggerService.error(_className, EnumExceptions.NULLPOINTER, "Objet p_user null", "signIn");
    	} 
    		return returnObject;
    }
    
    /**
     * 
     * @param p_user
     * @return authenticated object
     */
    private Authentication getAuthentication(final User p_user, final JWTAuthenticationFilter p_filter) {
    	
    	Authentication returnObject = null;
    	
    	if (StringUtils.isEmpty(p_user.getUserName()) && StringUtils.isEmpty(p_user.getUserName())) {
    		this._loggerService.error(_className, EnumExceptions.EMPTYSTRING, "userName et/ou password vide", "getAuthentication");
    	}else {
    		final Authentication authentication = p_filter.attemptAuthentication(p_user.getUserName(), p_user.getPassword());
    		if (authentication != null) {
    			returnObject = authentication;
    		}
    	}
    	return returnObject;
    }
    
    @GetMapping(value = "/users/getByToken")
    public User getUserFromToken(@RequestHeader(value="Authorization") final String p_tokenHeader) {
    	
    	User returnObject = null;
    	if (p_tokenHeader != "") {
    		final JWTAuthorizationFilter filter = new JWTAuthorizationFilter(_authImpl);
    		UsernamePasswordAuthenticationToken object = null;
    		try {
    			object = filter.getAuthenticationFromToken(p_tokenHeader);
    		}catch(Exception e) {
    			_loggerService.error(_className, EnumExceptions.NULLPOINTER, "Erreur en récupérant le userName du token", "getUserFromToken");
    		}
    		if (object != null) {
    			final String userName = object.getName();
    			if (!StringUtils.isEmpty(userName)) {
    				Optional<User> optionUser = this._repoUsers.findByUserName(userName);
    				if (optionUser.isPresent()) {
    					returnObject = optionUser.get();
    				}else {
    					_loggerService.warn(_className, EnumExceptions.NULLPOINTER, "Utilisateur introuvable", "getUserFromToken");
    				}
    			}else {
    				_loggerService.error(_className, EnumExceptions.EMPTYSTRING, "Nom d'utilisateur vide", "getUserFromToken");
    			}
    		}
    	}else {
    		this._loggerService.error(_className, EnumExceptions.EMPTYSTRING, "Token vide dans le header", "getUserFromToken");
    	}
    	
    	return returnObject;
    }

    /**
     * Endpoint for registering a new user
     * @param p_user 
     */
    @PostMapping(value = "sign-up")    
    public void signUp(@RequestBody User p_user, @RequestBody int p_roleId){
        
    	if (p_user != null) {
    		if(p_roleId != 0) {
    			p_user.setRole(this.getRole(p_roleId));
    	        this.add(p_user);
    		}else {
    			this._loggerService.error(_className, EnumExceptions.EMPTYSTRING, "Id role absent", "signUp");
    		}
    	}else {
    		this._loggerService.error(_className, EnumExceptions.NULLPOINTER, "p_user vide", "signUp");
    	}
    }
    
    /**
     * @param p_roleId the id od role to find
     * @return roles for a user or default
     */
    private Role getRole(final int p_roleId) {
    	
    	Role returnObject = null;
    	Optional<Role> optionRole = this._repoRoles.findById(p_roleId);
    	if (optionRole.isPresent()) {
    		// Wanted role founded
    		returnObject = optionRole.get();
    	}else {
    		this._loggerService.error(_className, EnumExceptions.NULLPOINTER, "optionRole vide", "getRole");
    	}
    	optionRole = this._repoRoles.findByName(EnumUserRoles.USER.toString());
    	if (optionRole.isPresent()) {
    		// Default role returned
    		returnObject = optionRole.get();
    	}else {
    		this._loggerService.error(_className, EnumExceptions.NULLPOINTER, "optionRole vide", "getRole");
    	}
    	
    	return returnObject;
    }

    /**
     * Find and return every User in DB
     * @return List<User>
     */
    @GetMapping("users/getAll")
    public List<User> getAll() {

        return this._repoUsers.findAll();

    }

    /**
     * Find and return a User by his id
     * @return List<User>
     */
    @GetMapping("users/get/{id}")
    public User getById(@PathVariable("id") final int p_id) {

    	User returnObject = null;
        if (p_id > 0){
           final Optional<User> optionUser = this._repoUsers.findById(p_id);
           if (optionUser.isPresent()){
        	   returnObject = optionUser.get();
           }else {
        	   this._loggerService.error(_className, EnumExceptions.NULLPOINTER, "optionUser", "getById");
           }
        }else {
        	this._loggerService.error(_className, EnumExceptions.NULLPOINTER, "p_id est vide", "getById");
        }

        return returnObject;

    }

    @PostMapping(value = "users/update")
    public User update(@RequestBody final User p_user) {

    	User returnObject = p_user;
    	if(p_user != null) {
    		this._loggerService.error(_className, EnumExceptions.NULLPOINTER, "p_user est vide", "update");
    	}else {
    		returnObject = this.add(p_user);
    	}
    	
        return returnObject;
        
    }
}