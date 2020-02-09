package com.skenons.med.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Exam
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Profile patient;
	
	@NotNull @ManyToOne
	private Profile doctor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date start;

	@DateTimeFormat(pattern = "HH:mm")
	private Date finish;
	
	@ManyToOne
	private Room room;
	
	@NotNull @ManyToOne
	private ExamType type;
	
	@OneToOne
	private ExamReport report;
	
	private Double discount;
	
	private Double price;
	
	private Boolean approved;

	public Exam()
	{
		
	}
	
	
	
	public Exam(Profile doctor, Date start, Date finish, Room room, ExamType type, Double discount, //PREDEFINED!
			Double price) {
		super();
		this.doctor = doctor;
		this.start = start;
		this.finish = finish;
		this.room = room;
		this.type = type;
		this.discount = discount;
		this.price = price;
		this.approved = true;
	}

	

	public Exam(Profile patient, Profile doctor, ExamType type, Room room, Date start) { //CUSTOM
		super();
		this.patient = patient;
		this.doctor = doctor;
		this.type = type;
		this.room = room;
		this.start = start;
		approved = false;
	}



	public Profile getPatient() {
		return patient;
	}

	public void setPatient(Profile patient) {
		this.patient = patient;
	}

	public Profile getDoctor() {
		return doctor;
	}

	public void setDoctor(Profile doctor) {
		this.doctor = doctor;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getDuration() {
		return finish;
	}

	public void setDuration(Date finish) {
		this.finish = finish;
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

	public Date getFinish() {
		return finish;
	}



	public void setFinish(Date finish) {
		this.finish = finish;
	}



	public void setReport(ExamReport report) {
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	
	
}
