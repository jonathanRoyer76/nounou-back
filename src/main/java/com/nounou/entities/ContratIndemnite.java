package com.nounou.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Contient les indemnités d'un contrat
 * @author jonathan
 *
 */
@Entity
@Table(name = "ContratIndemnites")
public class ContratIndemnite {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contrat_id")
	private Contrat contrat;
	
	/*
	 * Montant facturé pour un repas
	 */
	private double mealAmount;
	/*
	 * Montant facturé pour une indemnité journalière
	 */
	private double dailyAmount;
	/*
	 * Montant facturé pour un goûter
	 */
	private double gouterAmount;
	/*
	 * Montant facturé pour le barême kilométrique
	 */
	private double vehicleAmount;
	public Contrat getContrat() {
		return contrat;
	}
	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}
	public double getMealAmount() {
		return mealAmount;
	}
	public void setMealAmount(double mealAmount) {
		this.mealAmount = mealAmount;
	}
	public double getDailyAmount() {
		return dailyAmount;
	}
	public void setDailyAmount(double dailyAmount) {
		this.dailyAmount = dailyAmount;
	}
	public double getGouterAmount() {
		return gouterAmount;
	}
	public void setGouterAmount(double gouterAmount) {
		this.gouterAmount = gouterAmount;
	}
	public double getVehicleAmount() {
		return vehicleAmount;
	}
	public void setVehicleAmount(double vehicleAmount) {
		this.vehicleAmount = vehicleAmount;
	}
	public int getId() {
		return id;
	}
}
