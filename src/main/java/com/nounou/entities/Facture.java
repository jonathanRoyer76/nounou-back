package com.nounou.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Contient les données d'une facture liée à un contrat
 * @author jonathan
 *
 */
@Entity
@Table(name = "factures")
public class Facture {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@ManyToOne
	@JoinColumn(name = "contrat_id")
	@JsonIgnoreProperties("factures")
	private Contrat contrat;
}
