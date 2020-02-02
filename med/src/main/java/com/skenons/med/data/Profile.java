package com.skenons.med.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class Profile
{
	@Id @NotEmpty @Column(unique = true)
	private String IDNum;
	
	@NotEmpty @Email
	private String email;
	
	@NotEmpty
	private String username;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String lastName;
	
	@NotEmpty
	private String cellNumber;
	
	@NotEmpty
	private String address;
	
	@OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
	private Employee employeeProfile;
	
	@OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
	private Employee patientProfile;

	public Profile()
	{
		
	}
	
	
	
	public Profile(@NotEmpty String iDNum, @NotEmpty @Email String email, @NotEmpty String username,
			@NotEmpty String password, @NotEmpty String name, @NotEmpty String lastName, @NotEmpty String cellNumber,
			@NotEmpty String address) {
		super();
		IDNum = iDNum;
		this.email = email;
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.cellNumber = cellNumber;
		this.address = address;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Employee getEmployeeProfile() {
		return employeeProfile;
	}

	public void setEmployeeProfile(Employee employeeProfile) {
		employeeProfile.setProfile(this);
		this.employeeProfile = employeeProfile;
	}

	public Employee getPatientProfile() {
		return patientProfile;
	}

	public void setPatientProfile(Employee patientProfile) {
		patientProfile.setProfile(this);
		this.patientProfile = patientProfile;
	}

	public void setIDNum(String id)
	{
		this.IDNum = id;
	}
	
	public String getIDNum() {
		return IDNum;
	}
	
	
	
	
}
