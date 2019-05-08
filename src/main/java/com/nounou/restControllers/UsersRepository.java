package com.nounou.restControllers;

import java.util.List;

import com.nounou.entities.User;
import com.nounou.interfacesRepositories.IRepoUsers;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value = "add")
    @CrossOrigin(origins = "*")
    public User add(@RequestBody User p_user) {
        User returnValue = null;
        System.out.println(p_user.isAdmin());

        if (p_user != null) {
            returnValue = this._repoUsers.save(p_user);
        }

        return returnValue;
    }

    @GetMapping("getAll")
    @CrossOrigin(origins = "*")
    public List<User> getAll() {

        return this._repoUsers.findAll();

    }
}