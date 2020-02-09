package com.skenons.med.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DoctorRating
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Profile patient;
	
	@ManyToOne
	private Profile doctor;
	
	private Integer rating;

	protected DoctorRating()
	{
		
	}
	
	
	
	public DoctorRating(Profile patient, Profile doctor, Integer rating) {
		super();
		this.patient = patient;
		this.doctor = doctor;
		this.rating = rating;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Profile getPatient() {
		return patient;
	}

	public void setPatient(Profile patient) {
		this.patient = patient;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public void setDoctor(Profile doctor) {
		this.doctor = doctor;
	}
	
	public Profile getDoctor() {
		return doctor;
	}
	
	
}
