package com.skenons.med.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import com.skenons.med.data.enums.EmployeeType;

@Entity
public class Employee
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@OneToOne @NotEmpty @MapsId
	private Profile profile;
	
	@ManyToOne
	private Clinic clinic;
	
	@Enumerated
	private EmployeeType type;
	
	private String workingHours;//HH:MM-HH:MM format!
	
	@ManyToOne()
	private ExamType specialty;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EmployeeRating> ratings;
	
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Exam> exams;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LeaveRequest> leaves;
	
	protected Employee()
	{
		
	}
	
	public String getId() {
		return id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public EmployeeType getType() {
		return type;
	}

	public void setType(EmployeeType type) {
		this.type = type;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public ExamType getSpecialty() {
		return specialty;
	}

	public void setSpecialty(ExamType specialty) {
		this.specialty = specialty;
	}

	public List<EmployeeRating> getRatings() {
		return ratings;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public List<LeaveRequest> getLeaves() {
		return leaves;
	}
	
	public void addRating(EmployeeRating er)
	{
		if(ratings==null)
		{
			ratings = new ArrayList<EmployeeRating>();
		}
		er.setEmployee(this);
		ratings.add(er);
	}
	
	public void addExam(Exam e)
	{
		if(exams==null)
		{
			exams = new ArrayList<Exam>();
		}
		e.setDoctor(this);
		exams.add(e);
	}
	
	public void addLeave(LeaveRequest lr)
	{
		if(leaves==null)
		{
			leaves = new ArrayList<LeaveRequest>();
		}
		lr.setEmployee(this);
		leaves.add(lr);
	}
}
