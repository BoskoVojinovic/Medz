package com.skenons.med.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ExamPrice
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private ExamType examType;
	
	@ManyToOne
	private Clinic clinic;
	
	private Double basePrice;

	public ExamPrice()
	{
		
	}
	
	
	
	public ExamPrice(ExamType examType, Clinic clinic, Double basePrice)
	{
		this.examType = examType;
		this.clinic = clinic;
		this.basePrice = basePrice;
	}



	public ExamType getExamType() {
		return examType;
	}

	public void setExamType(ExamType examType) {
		this.examType = examType;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Long getId() {
		return id;
	}
	
	
}
