package com.nounou.restControllers;

import java.util.List;

import com.nounou.entities.User;
import com.nounou.interfacesRepositories.IRepoUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UsersRepository
 */
@RestController
@RequestMapping("users")
public class UsersRepository {

    @Autowired
    private IRepoUsers _repoUsers;

    @Autowired
    private BCryptPasswordEncoder _encoder;

    @PostMapping(value = "add")
    @CrossOrigin(origins = "*")
    public User add(@RequestBody User p_user) {

        if (p_user != null) {
            
            p_user.setPassword(_encoder.encode(p_user.getPassword()));
            this._repoUsers.save(p_user);
        }

        return p_user;
    }

    @GetMapping("getAll")
    @CrossOrigin(origins = "*")
    public List<User> getAll() {

        return this._repoUsers.findAll();

    }

    @PostMapping(value = "update")
    @CrossOrigin(origins = "*")
    public User update(@RequestBody User p_user) {

        return this.add(p_user);
        
    }

}