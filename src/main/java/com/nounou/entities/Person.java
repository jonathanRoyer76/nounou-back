package com.nounou.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Informations sur les personnes enregistrées
 * @author jonathan
 *
 */
@Entity
@Table(name="persons")
public class Person {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String adress;
	private String mobileNumber;
	private String email;
	private boolean isActive; // NOPMD by jonathan on 21/07/2019 14:58
	private String avatarFilePath;
	
	@ManyToOne
	@JoinColumn(name = "typesPerson_id")
	@JsonIgnoreProperties("persons")
	private TypePerson typesPerson;
		
	@ManyToMany(mappedBy = "persons")
	private Set<Contrat> contrats;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(final String adress) {
		this.adress = adress;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(final String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(final boolean isActive) {
		this.isActive = isActive;
	}

	public String getAvatarFilePath() {
		return avatarFilePath;
	}

	public void setAvatarFilePath(final String avatarFilePath) {
		this.avatarFilePath = avatarFilePath;
	}

	public TypePerson getTypePerson() {
		return typesPerson;
	}

	public void setTypePerson(final TypePerson typePerson) {
		this.typesPerson = typePerson;
	}
}
