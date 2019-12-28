package com.nounou.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Contient les données concernant l'accueil d'un contrat
 * @author jonathan
 *
 */
@Entity
@Table(name = "ContratAccueils")
public class ContratAccueil {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contrat_id")
	private Contrat contrat;
	
	/*
	 * Moyenne des heures de garde par semaine
	 */
	private double weeklyHoursAverage;
	/*
	 * Moyenne des semaines de garde par an
	 */
	private double annualWeeksAverage;
	/*
	 * Moyenne des heures par mois
	 */
	private double monthlyHoursAverage;
	/*
	 * Moyenne des jours par mois
	 */
	private double monthlyDaysAverage;
	/*
	 * Nombre de jours de garde par semaine
	 */
	private double weeklyDaysCount;
	/*
	 * Date de début du contrat
	 */
	private LocalDateTime contractBeginingDate;
	/*
	 * Date de fin du contrat
	 */
	private LocalDateTime contractEndDate;
	public Contrat getContrat() {
		return contrat;
	}
	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}
	public double getWeeklyHoursAverage() {
		return weeklyHoursAverage;
	}
	public void setWeeklyHoursAverage(double weeklyHoursAverage) {
		this.weeklyHoursAverage = weeklyHoursAverage;
	}
	public double getAnnualWeeksAverage() {
		return annualWeeksAverage;
	}
	public void setAnnualWeeksAverage(double annualWeeksAverage) {
		this.annualWeeksAverage = annualWeeksAverage;
	}
	public double getMonthlyHoursAverage() {
		return monthlyHoursAverage;
	}
	public void setMonthlyHoursAverage(double monthlyHoursAverage) {
		this.monthlyHoursAverage = monthlyHoursAverage;
	}
	public double getMonthlyDaysAverage() {
		return monthlyDaysAverage;
	}
	public void setMonthlyDaysAverage(double monthlyDaysAverage) {
		this.monthlyDaysAverage = monthlyDaysAverage;
	}
	public double getWeeklyDaysCount() {
		return weeklyDaysCount;
	}
	public void setWeeklyDaysCount(double weeklyDaysCount) {
		this.weeklyDaysCount = weeklyDaysCount;
	}
	public LocalDateTime getContractBeginingDate() {
		return contractBeginingDate;
	}
	public void setContractBeginingDate(LocalDateTime contractBeginingDate) {
		this.contractBeginingDate = contractBeginingDate;
	}
	public LocalDateTime getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(LocalDateTime contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public int getId() {
		return id;
	}
	
}
