package com.skenons.med.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Clinic
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String address;
	private String description;

	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Room> rooms;

	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	List<ClinicRating> ratings;
	
	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	List<ExamPrice> prices;
	
	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Employee> employees;
	
	protected Clinic()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public List<Room> getRooms() {
		return rooms;
	}

	public List<ClinicRating> getRatings() {
		return ratings;
	}

	public List<ExamPrice> getPrices() {
		return prices;
	}

	public List<Employee> getEmployees() {
		return employees;
	}
	
	public void addRoom(Room r)
	{
		if(rooms == null)
		{
			rooms = new ArrayList<Room>();
		}
		r.setClinic(this);
		rooms.add(r);
	}
	
	public void addRating(ClinicRating cr)
	{
		if(ratings == null)
		{
			ratings = new ArrayList<ClinicRating>();
		}
		cr.setClinic(this);
		ratings.add(cr);
	}
	
	public void addPrice(ExamPrice ep)
	{
		if(prices == null)
		{
			prices = new ArrayList<ExamPrice>();
		}
		ep.setClinic(this);
		prices.add(ep);
	}
	
	public void addEmployee(Employee e)
	{
		if(employees == null)
		{
			employees = new ArrayList<Employee>();
		}
		e.setClinic(this);
		employees.add(e);
	}
}
