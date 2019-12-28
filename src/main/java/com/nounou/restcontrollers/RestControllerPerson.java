package com.nounou.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nounou.constants.EnumExceptions;
import com.nounou.entities.Person;
import com.nounou.entities.TypePerson;
import com.nounou.interfacesRepositories.IRepoPerson;
import com.nounou.interfacesRepositories.IRepoTypePersons;
import com.nounou.services.LoggerService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "persons")
public class RestControllerPerson {
	
	@Autowired
	private IRepoPerson _repoPerson;
	@Autowired
	private IRepoTypePersons _repoTypesPerson;
	@Autowired
	private LoggerService _loggerService;
	private static String _className = "restControllerPerson";
	
	@GetMapping(value = "/getAll")
	public List<Person> getAll(){
		
		return this._repoPerson.findAll();
		
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@RequestBody final Person p_person) {
		
		ResponseEntity<?> returnObject = null;
		
		if (p_person.getTypePerson() == null) {
			final Optional<TypePerson> optionType = this._repoTypesPerson.findByName("Parent");
			if (optionType.isPresent()) {
				p_person.setTypePerson(optionType.get());
			}else {
				this._loggerService.error(_className, EnumExceptions.NULLPOINTER, "Pas de typePersonne dans l'objet p_person", "add");
			}
		}
		
		if (!StringUtils.isEmpty(p_person.getEmail())) {
			// check by email
			final Optional<Person> optionPerson = this._repoPerson.findByEmail(p_person.getEmail());
			if (optionPerson.isPresent()) {
				returnObject =  new ResponseEntity<String>("Cet email est déjà présent dans la base de données.", HttpStatus.METHOD_NOT_ALLOWED);
			}else {
				// check by firstname & lastname
				if (!StringUtils.isEmpty(p_person.getFirstName()) && !StringUtils.isEmpty(p_person.getLastName())) {
					final Optional<Person> optionFirstLastName = this._repoPerson.findByFirstNameAndLastName(p_person.getFirstName(), p_person.getLastName());
					if (optionFirstLastName.isPresent()) {
						returnObject =  new ResponseEntity<String>("Cette personne existe déjà dans la base de données.", HttpStatus.METHOD_NOT_ALLOWED);
					}else {
						final Person returnDb = this._repoPerson.save(p_person);
						returnObject = new ResponseEntity<Person>(returnDb, HttpStatus.ACCEPTED);
					}
				}else {
					this._loggerService.error(_className, EnumExceptions.EMPTYSTRING, "FirstName et/ou lastName vide dans l'objet p_person", "add");
				}
			}
		}else {
			this._loggerService.error(_className, EnumExceptions.EMPTYSTRING, "Email vide dans l'objet p_person", "add");
		}
		
		return returnObject;
	}
}
