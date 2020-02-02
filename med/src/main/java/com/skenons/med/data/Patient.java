package com.skenons.med.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Patient
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@OneToOne @NotEmpty @MapsId
	private Profile profile;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Exam> exams;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)//Potentially false (remember rating?)
	private List<ClinicRating> clinicRatings;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)//Potentially false (remember rating?)
	private List<EmployeeRating> doctorRatings;
	
	@OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true) //Potentially false (remember records?)
	private Records record;

	protected Patient()
	{
		clinicRatings = new ArrayList<ClinicRating>();
		doctorRatings = new ArrayList<EmployeeRating>();
		exams = new ArrayList<Exam>();
	}
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Records getRecord() {
		return record;
	}

	public void setRecord(Records record) {
		record.setPatient(this);
		this.record = record;
	}

	public String getId() {
		return id;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public List<ClinicRating> getClinicRatings() {
		return clinicRatings;
	}

	public List<EmployeeRating> getDoctorRatings() {
		return doctorRatings;
	}
	
	public void addExam(Exam e)
	{
		if(exams==null)
		{
			exams = new ArrayList<Exam>();
		}
		e.setPatient(this);
		exams.add(e);
	}
	
	public void addClinicRating(ClinicRating cr)
	{
		if(clinicRatings==null)
		{
			clinicRatings = new ArrayList<ClinicRating>();
		}
		cr.setPatient(this);
		clinicRatings.add(cr);
	}
	
	public void addDoctorRating(EmployeeRating er)
	{
		if(doctorRatings==null)
		{
			doctorRatings = new ArrayList<EmployeeRating>();
		}
		er.setPatient(this);
		doctorRatings.add(er);
	}
}
