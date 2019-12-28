package com.nounou.interfacesRepositories;

import java.util.ArrayList;
import java.util.Optional;

import com.nounou.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * IRepoRoles
 */
public interface IRepoRoles extends JpaRepository<Role, Integer>{
	
    @Override
	ArrayList<Role> findAll();

    Optional<Role> findById(final int p_id);
    Optional<Role> findByName(final String p_name);    
}