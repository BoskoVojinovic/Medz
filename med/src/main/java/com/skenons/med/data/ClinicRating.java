package com.skenons.med.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ClinicRating
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Profile patient;
	
	@ManyToOne
	private Clinic clinic;
	
	private Integer rating;

	protected ClinicRating()
	{
		
	}
	
	
	
	public ClinicRating(Profile patient, Clinic clinic, Integer rating) {
		super();
		this.patient = patient;
		this.clinic = clinic;
		this.rating = rating;
	}



	public Profile getPatient() {
		return patient;
	}

	public void setPatient(Profile patient) {
		this.patient = patient;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	
}
