package com.skenons.med.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
	@Transient
	private Double avgReview;

	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Room> rooms = new ArrayList<Room>();

	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	List<ClinicRating> ratings = new ArrayList<ClinicRating>();
	
	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	List<ExamPrice> prices = new ArrayList<ExamPrice>();
	
	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Profile> employees = new ArrayList<Profile>();
	
	protected Clinic()
	{
		
	}
	
	public Clinic(String name, String address, String description)
	{
		this.name = name;
		this.address = address;
		this.description = description;
		Room r = new Room(this, 0, 0);
		rooms.add(r);
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

	public List<Profile> getEmployees() {
		return employees;
	}
	
	
	
	public Double getAvgReview() {
		return avgReview;
	}

	public void setAvgReview(Double averageReview) {
		this.avgReview = averageReview;
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
	
	public void addEmployee(Profile e)
	{
		if(employees == null)
		{
			employees = new ArrayList<Profile>();
		}
		e.setClinic(this);
		employees.add(e);
	}
}
