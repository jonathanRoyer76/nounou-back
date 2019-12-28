package com.nounou.interfacesRepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nounou.entities.Contrat;

public interface IRepoContrat extends JpaRepository<Contrat, Integer> {

}
