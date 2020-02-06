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
	
	private Integer basePrice;

	public ExamPrice()
	{
		
	}
	
	
	
	public ExamPrice(ExamType examType, Clinic clinic, Integer basePrice)
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

	public Integer getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Integer basePrice) {
		this.basePrice = basePrice;
	}

	public Long getId() {
		return id;
	}
	
	
}
