package com.nounou.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DefaultContractValues")
public class DefaultContractValues {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	/*
	 * Date du changement
	 */
	private LocalDateTime dateChange;
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
	/*
	 * taux horaire net
	 */
	private double netHourly;
	/*
	 * Taux horaire brut
	 */
	private double grossHourly;
	public LocalDateTime getDateChange() {
		return dateChange;
	}
	public void setDateChange(LocalDateTime dateChange) {
		this.dateChange = dateChange;
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
	public double getNetHourly() {
		return netHourly;
	}
	public void setNetHourly(double netHourly) {
		this.netHourly = netHourly;
	}
	public double getGrossHourly() {
		return grossHourly;
	}
	public void setGrossHourly(double grossHourly) {
		this.grossHourly = grossHourly;
	}
	public int getId() {
		return id;
	}
}
