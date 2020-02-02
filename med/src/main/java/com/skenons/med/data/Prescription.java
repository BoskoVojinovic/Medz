package com.skenons.med.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Prescription
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private ExamReport report;
	
	@ManyToOne
	private Medicine medicine;
	
	@ManyToOne
	private Employee authorizer;

	protected Prescription()
	{
		
	}
	
	public ExamReport getReport() {
		return report;
	}

	public void setReport(ExamReport report) {
		this.report = report;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public Employee getAuthorizer() {
		return authorizer;
	}

	public void setAuthorizer(Employee authorizer) {
		this.authorizer = authorizer;
	}

	public Long getId() {
		return id;
	}
	
	
}
