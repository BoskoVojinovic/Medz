package com.skenons.med.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.skenons.med.data.enums.ProfileType;

@Entity
public class Profile
{
	@Id @NotEmpty @Column(unique = true) @Size(min = 13, max = 13)
	private String IDNum;
	
	@NotEmpty @Email
	private String email;
	
	@NotEmpty @Size(min = 8)
	private String password="";
	
	@Transient
	private String rePassword="";
	
	@NotEmpty @Size(max = 50)
	private String name;
	
	@NotEmpty @Size(max = 50)
	private String lastName;
	
	@NotEmpty @Size(min = 9, max = 11)
	private String cellNumber;
	
	@NotEmpty @Size(max = 100)
	private String address;
	
	@ManyToOne
	private Clinic clinic;
	
	@ManyToOne()
	private ExamType specialty;
	
	@Enumerated(EnumType.STRING)
	private ProfileType type;
	

	@DateTimeFormat(pattern = "HH:mm")
	private Date workingHoursStart;
	

	@DateTimeFormat(pattern = "HH:mm")
	private Date workingHoursEnd;

	@Transient
	private Double avgReview;
	
	private Boolean approved = true; //automatic admins :D
	
	private Boolean verified = false;
	
	private Boolean deleted = false;
	
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
	public Profile(@NotEmpty String iDNum, @NotEmpty @Email String email,
			@NotEmpty String password, @NotEmpty String name, @NotEmpty String lastName, @NotEmpty String cellNumber,
			@NotEmpty String address, Date whs, Date whe) {
		super();
		IDNum = iDNum;
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.cellNumber = cellNumber;
		this.address = address;
		this.workingHoursStart = whs;
		this.workingHoursEnd = whe;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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

	public void setIDNum(String id)
	{
		this.IDNum = id;
	}
	
	public String getIDNum() {
		return IDNum;
	}
	
	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public ProfileType getType() {
		return type;
	}

	public void setType(ProfileType type) {
		this.type = type;
	}

	public Date getWorkingHoursStart() {
		return workingHoursStart;
	}

	public void setWorkingHoursStart(Date workingHoursStart) {
		this.workingHoursStart = workingHoursStart;
	}

	public Date getWorkingHoursEnd() {
		return workingHoursEnd;
	}

	public void setWorkingHoursEnd(Date workingHoursEnd) {
		this.workingHoursEnd = workingHoursEnd;
	}

	public ExamType getSpecialty() {
		return specialty;
	}

	public void setSpecialty(ExamType specialty) {
		this.specialty = specialty;
	}
	
	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	
	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
	
	

	public Double getAvgReview() {
		return avgReview;
	}

	public void setAvgReview(Double avgReview) {
		this.avgReview = avgReview;
	}

	@Override
	public String toString() {
		return "Profile [IDNum=" + IDNum + ", email=" + email + ", password=" + password + ", name=" + name
				+ ", lastName=" + lastName + ", cellNumber=" + cellNumber + ", address=" + address + "]";
	}
}
