package com.nounou.interfacesRepositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nounou.entities.TypePerson;

public interface IRepoTypePersons extends JpaRepository<TypePerson, Integer>{

	@Override
	ArrayList<TypePerson> findAll();
	
	Optional<TypePerson> findById(final int p_id);
	Optional<TypePerson> findByName(final String p_name);
}
