package com.nounou.interfacesRepositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nounou.entities.Logs;

/**
 * Interface for Logger repository
 * @author jonathan
 *
 */
public interface IRepoLoggers extends JpaRepository<Logs, Integer> {

	@Override
	ArrayList<Logs> findAll();
}
