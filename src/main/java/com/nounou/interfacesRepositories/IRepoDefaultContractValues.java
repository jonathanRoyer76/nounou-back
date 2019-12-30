package com.nounou.interfacesRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nounou.entities.DefaultContractValues;

public interface IRepoDefaultContractValues extends JpaRepository<DefaultContractValues, Integer>{
	
	/**
	 * @return The last DefaultContractValues inserted in DB by date
	 */
	@Query(value = "SELECT * FROM nounou.default_contract_values order by date_change desc limit 1", nativeQuery = true)
	DefaultContractValues getLastContractValuesByDate();

}
