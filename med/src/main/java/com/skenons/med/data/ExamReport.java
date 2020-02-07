package com.skenons.med.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ExamReport
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(mappedBy = "report", cascade = CascadeType.ALL)
	private Exam exam;
	
	@OneToOne
	private Diagnosis diagnosis;
	
	@OneToOne
	private Prescription prescription;
	
	@ManyToOne
	private Records records;

	protected ExamReport()
	{
		
	}
	
	public ExamReport(Diagnosis diagnosis, Prescription prescription, Records records) {
		super();
		this.diagnosis = diagnosis;
		this.prescription = prescription;
		this.records = records;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Diagnosis getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(Diagnosis diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Records getRecords() {
		return records;
	}

	public void setRecords(Records records) {
		this.records = records;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public Long getId() {
		return id;
	}
	
	
}
