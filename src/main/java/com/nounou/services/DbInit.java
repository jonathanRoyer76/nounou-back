package com.nounou.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nounou.entities.Role;
import com.nounou.entities.TypePerson;
import com.nounou.interfacesRepositories.IRepoRoles;
import com.nounou.interfacesRepositories.IRepoTypePersons;

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
    @Autowired
    private IRepoTypePersons repoTypePersons;

    @Override
    public void run(final String... args) throws Exception {

    	// Roles initialization
        final ArrayList<Role> roles = this.repoRoles.findAll();

        if (roles.isEmpty()){
            this.repoRoles.deleteAll();

            final Role roleAdmin = new Role("Admin");
            final Role roleNounou = new Role("Nounou");
            final Role roleParent = new Role("User");
            final Role roleGuest = new Role("Guest");

            final List<Role> rolesList = Arrays.asList(roleAdmin, roleGuest, roleNounou, roleParent);       
            
            this.repoRoles.saveAll(rolesList);
        }
        
        // Types persons initialization
        final ArrayList<TypePerson> typePersons = this.repoTypePersons.findAll();

        if (typePersons.isEmpty()){
            this.repoTypePersons.deleteAll();

            final TypePerson typeParent = new TypePerson("Parent");
            final TypePerson typeEnfant = new TypePerson("Enfant");
            final TypePerson typeTuteur = new TypePerson("Tuteur");
            final TypePerson typeMedecin = new TypePerson("Médecin");

            final List<TypePerson> typePersonList = Arrays.asList(typeParent, typeEnfant, typeTuteur, typeMedecin);       
            
            this.repoTypePersons.saveAll(typePersonList);
        }
    }
}