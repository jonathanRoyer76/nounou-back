package com.nounou.restControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nounou.entities.User;
import com.nounou.interfacesRepositories.IRepoUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * restControllerUsers
 */
@RestController
@RequestMapping("users")
public class restControllerUsers {

    @Autowired
    private IRepoUsers _repoUsers;    
    @Autowired
    private BCryptPasswordEncoder _passwordEncoder;

    @PostMapping(value = "add")
    @CrossOrigin(origins = "*")
    public User add(final User p_user) {

        if (p_user != null) {
            p_user.setPassword(_passwordEncoder.encode(p_user.getPassword()));
            return this._repoUsers.save(p_user);
        }

        return p_user;
    }

    /**
     * Endpoint for registering a new user
     * @param p_user 
     */
    @PostMapping(value = "sign-up")
    @CrossOrigin(origins = "*")
    public void signUp(final User p_user){
        
        this.add(p_user);
    }

    /**
     * Find and return every User in DB
     * @return List<User>
     */
    @GetMapping("getAll")
    @CrossOrigin(origins = "*")
    public List<User> getAll() {

        ArrayList<User> usersList = this._repoUsers.findAll();
        return usersList;

    }

    /**
     * Find and return a User by his id
     * @return List<User>
     */
    @GetMapping("get/{id}")
    @CrossOrigin(origins = "*")
    public User getById(@PathVariable("id") int p_id) {

        if (p_id != 0){
           Optional<User> optionUser = this._repoUsers.findById(p_id);
           if (optionUser.isPresent()){
               User user = optionUser.get();
               return user;
           }
        }

        return null;

    }

    @GetMapping("public")
    @CrossOrigin(value = "*")
    public String test(){

        return "Dans la méthode de test PUBLIC";
    }

    @GetMapping("private")
    @CrossOrigin(value = "*")
    public String testPrive(){

        return "Dans la méthode de test PRIVEE";
    }

    @PostMapping(value = "update")
    @CrossOrigin(origins = "*")
    public User update(final @RequestBody User p_user) {

        return this.add(p_user);
        
    }
}