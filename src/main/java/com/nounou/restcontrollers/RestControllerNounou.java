package com.nounou.restcontrollers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nounou.constants.EnumExceptions;
import com.nounou.entities.DefaultContractValues;
import com.nounou.interfacesRepositories.IRepoDefaultContractValues;
import com.nounou.services.LoggerService;

/**
 * restControllerNounou
 */
@RestController
@RequestMapping("nounou")
public class RestControllerNounou {

	@Autowired 
    private LoggerService _loggerService;
    private static String _className = "restControllerUser"; 
    @Autowired
    private IRepoDefaultContractValues _repoContractValues;
    
    @CrossOrigin(origins = "*")
    @GetMapping(value = "home")    
    public String home(){
        return "Dans la méthode de nounou";
    }
    
    /**
     * Add a new object in DB with date of present day
     * @param p_contractValues Object to add in DB
     * @return The inserted object with its ID
     */
    @PostMapping(value = "addDefaultIndemnites")
    public DefaultContractValues updateDefaultIndemnites(@RequestBody final DefaultContractValues p_contractValues) {
    	DefaultContractValues returnObject = p_contractValues;
    	
    	if (p_contractValues == null) {
    		_loggerService.error(_className, EnumExceptions.NULLPOINTER, "Object p_indemnite null", "updateDefaultIndemnites");
    	}else {
    		if (p_contractValues.getDateChange() == null)
    			p_contractValues.setDateChange(LocalDateTime.now());
    		returnObject = _repoContractValues.save(p_contractValues);    		
    	}
    	
    	return returnObject;
    }
    
    /**
     * 
     * @return the last objet inserted in DB
     */
    @GetMapping(value = "getDefaultIndemnites")
    public DefaultContractValues getDefaultIndemnites() {
    	
    	return _repoContractValues.getLastContractValuesByDate();
    }
}