package com.skenons.med.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Profile
{
	@Id @NotEmpty @Column(unique = true) @Size(min = 13, max = 13)
	private String IDNum;
	
	@NotEmpty @Email
	private String email;
	
	@NotEmpty @Size(min = 8)
	private String password;
	
	@NotEmpty @Size(max = 50)
	private String name;
	
	@NotEmpty @Size(max = 50)
	private String lastName;
	
	@NotEmpty @Size(min = 9, max = 11)
	private String cellNumber;
	
	@NotEmpty @Size(max = 100)
	private String address;
	
	@OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
	private Employee employeeProfile;
	
	@OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
	private Patient patientProfile;

	public Profile()
	{
		
	}
	
	public Profile(@NotEmpty String iDNum, @NotEmpty @Email String email,
			@NotEmpty String password, @NotEmpty String name, @NotEmpty String lastName, @NotEmpty String cellNumber,
			@NotEmpty String address) {
		super();
		IDNum = iDNum;
		this.email = email;
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

	public Patient getPatientProfile() {
		return patientProfile;
	}

	public void setPatientProfile(Patient patientProfile) {
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

	@Override
	public String toString() {
		return "Profile [IDNum=" + IDNum + ", email=" + email + ", password=" + password + ", name=" + name
				+ ", lastName=" + lastName + ", cellNumber=" + cellNumber + ", address=" + address + "]";
	}
	
	
}
