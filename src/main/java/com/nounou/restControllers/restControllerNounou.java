package com.nounou.restControllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * restControllerNounou
 */
@RestController
@RequestMapping("nounou")
public class restControllerNounou {

    @GetMapping(value = "home")
    @CrossOrigin(origins = "*")
    public String home(){
        return "Dans la méthode de nounou";
    }
}