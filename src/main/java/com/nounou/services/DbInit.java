package com.nounou.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nounou.entities.Role;
import com.nounou.interfacesRepositories.IRepoRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * DbInit
 */
@Service
public class DbInit implements CommandLineRunner{

    @Autowired
    private IRepoRoles repoRoles;

    @Override
    public void run(String... args) throws Exception {

        ArrayList<Role> roles = this.repoRoles.findAll();

        if (roles.isEmpty()){
            this.repoRoles.deleteAll();

            Role roleAdmin = new Role("Admin");
            Role roleNounou = new Role("Nounou");
            Role roleParent = new Role("User");
            Role roleGuest = new Role("Guest");

            List<Role> rolesList = Arrays.asList(roleAdmin, roleGuest, roleNounou, roleParent);       
            
            this.repoRoles.saveAll(rolesList);
        }
    }
    
}