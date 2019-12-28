package com.nounou.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Type de personne enregistrée (parent, enfant)
 * @author jonathan
 *
 */
@Entity
@Table(name = "typesPerson")
public class TypePerson {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    
    @OneToMany(mappedBy = "typesPerson", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("typesPerson")
    private Set<Person> persons = new HashSet<>();
    
    /**
     * Default constructor
     */
    public TypePerson() {
    	
    }
    
    public TypePerson(final String p_name) {
    	this.name = p_name;
    }

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Set<Person> getPersons() {
		return persons;
	}

	public void setPersons(final Set<Person> persons) {
		this.persons = persons;
	}
}
