package com.nounou.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nounou.entities.TypePerson;
import com.nounou.interfacesRepositories.IRepoTypePersons;

@RestController
@RequestMapping(value="typesPerson")
@CrossOrigin(origins="*")
public class RestControllerTypePerson {

	@Autowired
	private IRepoTypePersons _repotypePerson;
	
	@GetMapping(value="/getAll")
	public List<TypePerson> getAll(){
		
		final List<TypePerson> retour = this._repotypePerson.findAll();
		for (final TypePerson type : retour) {
			type.setPersons(null);
		}
		return retour;
	}
	
}
