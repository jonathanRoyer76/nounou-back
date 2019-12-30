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
 * Contient la rémunération d'un contrat
 * @author jonathan
 *
 */
@Entity
@Table(name = "ContratRemunerations")
public class ContratRemuneration {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contrat_id")
	private Contrat contrat;
	
	/*
	 * taux horaire net
	 */
	private double netHourly;
	/*
	 * Taux horaire brut
	 */
	private double grossHourly;
	/*
	 * Salaire brut mensuel
	 */
	private double grossMonthlySalary;
	/*
	 * Salaire net mensuel
	 */
	private double netMonthlySalary;
	/*
	 * Montant de congés payés brut
	 */
	private double grossPaidvacation;
	/*
	 * Montant des congés payés net
	 */
	private double netPaidVacation;
	/*
	 * Montant total du salaire mensuel en brut
	 */
	private double grossTotalSalary;
	/*
	 * Montant total du salaire mensuel en net
	 */
	private double netTotalSalary;
	public Contrat getContrat() {
		return contrat;
	}
	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
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
	public double getGrossMonthlySalary() {
		return grossMonthlySalary;
	}
	public void setGrossMonthlySalary(double grossMonthlySalary) {
		this.grossMonthlySalary = grossMonthlySalary;
	}
	public double getNetMonthlySalary() {
		return netMonthlySalary;
	}
	public void setNetMonthlySalary(double netMonthlySalary) {
		this.netMonthlySalary = netMonthlySalary;
	}
	public double getGrossPaidvacation() {
		return grossPaidvacation;
	}
	public void setGrossPaidvacation(double grossPaidvacation) {
		this.grossPaidvacation = grossPaidvacation;
	}
	public double getNetPaidVacation() {
		return netPaidVacation;
	}
	public void setNetPaidVacation(double netPaidVacation) {
		this.netPaidVacation = netPaidVacation;
	}
	public double getGrossTotalSalary() {
		return grossTotalSalary;
	}
	public void setGrossTotalSalary(double grossTotalSalary) {
		this.grossTotalSalary = grossTotalSalary;
	}
	public double getNetTotalSalary() {
		return netTotalSalary;
	}
	public void setNetTotalSalary(double netTotalSalary) {
		this.netTotalSalary = netTotalSalary;
	}
	public int getId() {
		return id;
	}
	
}
