package com.nounou.restcontrollers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * restControllerNounou
 */
@RestController
@RequestMapping("nounou")
public class RestControllerNounou {

    @CrossOrigin(origins = "*")
    @GetMapping(value = "home")    
    public String home(){
        return "Dans la méthode de nounou";
    }
}