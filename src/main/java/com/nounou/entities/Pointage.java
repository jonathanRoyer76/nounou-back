package com.nounou.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Contient les données de pointages liées à une contrat
 * @author jonathan
 *
 */
@Entity
@Table(name = "pointages")
public class Pointage {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	private LocalDateTime arrivalTime;
	private LocalDateTime departureTime;
	private LocalDateTime totalDayTime;
	
	public LocalDateTime getTotalDayTime() {
		return totalDayTime;
	}

	public void setTotalDayTime(LocalDateTime totalDayTime) {
		this.totalDayTime = totalDayTime;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
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

	@ManyToOne
	@JoinColumn(name = "contrat_id")
	@JsonIgnoreProperties("pointages")
	private Contrat contrat;
	
}
