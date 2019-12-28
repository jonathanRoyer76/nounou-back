package com.nounou.entities;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Contient les jours et heures prévues au contrat
 * @author jonathan
 *
 */
@Entity
@Table(name = "planningContrats")
public class PlanningContrat {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	private int dayOfWeekNumber;
	private String dayOfWeek;
	private LocalTime arrivalTime;
	public int getDayOfWeekNumber() {
		return dayOfWeekNumber;
	}

	public void setDayOfWeekNumber(int dayOfWeekNumber) {
		this.dayOfWeekNumber = dayOfWeekNumber;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public int getId() {
		return id;
	}

	private LocalTime departureTime;
	
	@ManyToOne
	@JoinColumn(name = "contrat_id")
	@JsonIgnoreProperties("planningContrats")
	private Contrat contrat;

}
