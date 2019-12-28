package com.nounou.restcontrollers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * restControllerAdmin
 */
@RestController
@RequestMapping("admin")
public class RestControllerAdmin {

    @GetMapping(value = "home")
    @CrossOrigin(origins = "*")
    public String test(){
        return "Dans la méthode de test ADMIN";
    }
}