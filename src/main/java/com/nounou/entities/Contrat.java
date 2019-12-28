package com.nounou.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Contient les données des contrats
 * @author jonathan
 *
 */
@Entity
@Table(name = "contrats")
public class Contrat {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("contrat")
    private Set<PlanningContrat> planningContrats = new HashSet<>();
	
	@OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("contrat")
    private Set<Pointage> pointages = new HashSet<>();
	
	@OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("contrat")
    private Set<Facture> factures = new HashSet<>();	
	
	@ManyToMany()
	public Set<Person> persons;	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contrat_remunerations_id")
	private ContratRemuneration contratRemuneration;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contrat_indemnites_id")
	private ContratIndemnite contratIndemnites;

	public ContratRemuneration getContratRemuneration() {
		return contratRemuneration;
	}

	public void setContratRemuneration(ContratRemuneration contratRemuneration) {
		this.contratRemuneration = contratRemuneration;
	}

	public ContratIndemnite getContratIndemnites() {
		return contratIndemnites;
	}

	public void setContratIndemnites(ContratIndemnite contratIndemnites) {
		this.contratIndemnites = contratIndemnites;
	}

	public ContratAccueil getContratAccueil() {
		return contratAccueil;
	}

	public void setContratAccueil(ContratAccueil contratAccueil) {
		this.contratAccueil = contratAccueil;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contrat_Accueils_id")
	private ContratAccueil contratAccueil;

	public Set<PlanningContrat> getPlanningContrats() {
		return planningContrats;
	}

	public void setPlanningContrats(Set<PlanningContrat> planningContrats) {
		this.planningContrats = planningContrats;
	}

	public Set<Pointage> getPointages() {
		return pointages;
	}

	public void setPointages(Set<Pointage> pointages) {
		this.pointages = pointages;
	}

	public Set<Facture> getFactures() {
		return factures;
	}

	public void setFactures(Set<Facture> factures) {
		this.factures = factures;
	}

	public Set<Person> getPersons() {
		return persons;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}

	public int getId() {
		return id;
	}
}
