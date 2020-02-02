package com.skenons.med.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.skenons.med.data.enums.BloodType;

@Entity
public class Records
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Patient patient;
	private Integer height;
	private Integer weight;
	private String alergies;
	
	@Enumerated
	private BloodType bloodType;
	
	@OneToMany(mappedBy = "records", cascade = CascadeType.ALL, orphanRemoval = true) //potentially false(remember report?)
	private List<ExamReport> history;

	protected Records()
	{
		
	}
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getAlergies() {
		return alergies;
	}

	public void setAlergies(String alergies) {
		this.alergies = alergies;
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public Long getId() {
		return id;
	}

	public List<ExamReport> getHistory() {
		return history;
	}
	
	public void addHistory(ExamReport er)
	{
		if(history == null)
		{
			history = new ArrayList<ExamReport>();
		}
		er.setRecords(this);
		history.add(er);
	}
}
