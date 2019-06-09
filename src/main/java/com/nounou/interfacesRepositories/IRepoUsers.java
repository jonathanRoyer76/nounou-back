package com.nounou.interfacesRepositories;

import java.util.ArrayList;
import java.util.Optional;

import com.nounou.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * IRepoUsers
 */
public interface IRepoUsers extends JpaRepository<User, Integer> {
    ArrayList<User> findAll();

    Optional<User> findById(final int p_id);
    Optional<User> findByUserName(final String p_userName);
}