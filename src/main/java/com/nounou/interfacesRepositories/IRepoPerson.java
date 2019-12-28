package com.nounou.interfacesRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nounou.entities.Person;

public interface IRepoPerson extends JpaRepository<Person, Integer>{

	Optional<Person> findByLastName(final String p_lastName);
	Optional<Person> findByFirstName(final String p_firstName);
	Optional<Person> findByEmail(final String p_email);
	Optional<Person> findByFirstNameAndLastName(final String firstName, final String lastName);
	@Override
	List<Person> findAll();
}
