package com.nounou.interfacesRepositories;

import java.util.ArrayList;

import com.nounou.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * IRepoUsers
 */
public interface IRepoUsers extends JpaRepository<User, Integer> {
    ArrayList<User> findAll();
}