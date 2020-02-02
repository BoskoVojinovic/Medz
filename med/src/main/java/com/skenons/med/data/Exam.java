package com.skenons.med.data;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Exam
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Patient patient;
	
	@ManyToOne
	private Employee doctor;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;

	@Temporal(TemporalType.TIME)
	private Date duration;
	
	@ManyToOne
	private Room room;
	
	@ManyToOne
	private ExamType type;
	
	@OneToOne(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true) //potentially false(remember report?)
	private ExamReport report;
	
	private Double discount;
	
	private Boolean approved;

	protected Exam()
	{
		
	}
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Employee getDoctor() {
		return doctor;
	}

	public void setDoctor(Employee doctor) {
		this.doctor = doctor;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getDuration() {
		return duration;
	}

	public void setDuration(Date duration) {
		this.duration = duration;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public ExamType getType() {
		return type;
	}

	public void setType(ExamType type) {
		this.type = type;
	}

	public ExamReport getReport() {
		return report;
	}

	public void setReport(ExamReport report) {
		report.setExam(this);
		this.report = report;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Long getId() {
		return id;
	}
	
	
}
