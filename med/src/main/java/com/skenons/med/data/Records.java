package com.skenons.med.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.skenons.med.data.enums.BloodType;

@Entity
public class Records
{
	@Id
	private String id;
	
	@OneToOne @MapsId
	private Profile patient;
	private Integer height;
	private Integer weight;
	private String allergies;
	private Boolean donor;
	
	@Enumerated(EnumType.STRING)
	private BloodType bloodType;
	
	@OneToMany(mappedBy = "records", cascade = CascadeType.ALL, orphanRemoval = true) //potentially false(remember report?)
	private List<ExamReport> history = new ArrayList<ExamReport>();

	protected Records()
	{
		
	}
	
	
	public Records(Profile patient, Integer height, Integer weight, String alergies, BloodType bloodType, Boolean donor)
	{
		super();
		this.patient = patient;
		this.height = height;
		this.weight = weight;
		this.allergies = alergies;
		this.bloodType = bloodType;
		this.donor = donor;
	}


	public Profile getPatient() {
		return patient;
	}

	public void setPatient(Profile patient) {
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

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String alergies) {
		this.allergies = alergies;
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public String getId() {
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


	public Boolean getDonor() {
		return donor;
	}


	public void setDonor(Boolean donor) {
		this.donor = donor;
	}
}
