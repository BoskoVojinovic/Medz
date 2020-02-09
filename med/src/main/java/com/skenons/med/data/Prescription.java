package com.skenons.med.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Prescription
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull @OneToOne
	private Medicine medicine;
	
	@NotNull
	private String description;
	
	//@ManyToOne
	//private Profile authorizer;//automatic nurses :D

	protected Prescription()
	{
		
	}
	
	public Prescription(@NotNull Medicine medicine, @NotNull String description) {
		super();
		this.medicine = medicine;
		this.description = description;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
