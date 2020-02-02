package com.skenons.med.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ExamType
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	
	@OneToMany(mappedBy = "examType", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ExamPrice> prices;
	
	@OneToMany(mappedBy = "specialty", cascade = CascadeType.ALL)
	private List<Employee> specialists;

	protected ExamType()
	{
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public List<ExamPrice> getPrices() {
		return prices;
	}

	public List<Employee> getSpecialists() {
		return specialists;
	}
	
	public void addPrice(ExamPrice ep)
	{
		if(prices==null)
		{
			prices = new ArrayList<ExamPrice>();
		}
		ep.setExamType(this);
		prices.add(ep);
	}
	
	public void addSpecialist(Employee e)
	{
		if(specialists==null)
		{
			specialists = new ArrayList<Employee>();
		}
		e.setSpecialty(this);
		specialists.add(e);
	}
	
}
