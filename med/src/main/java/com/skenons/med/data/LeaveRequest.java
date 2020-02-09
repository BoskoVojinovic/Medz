package com.skenons.med.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.skenons.med.data.enums.LeaveType;

@Entity
public class LeaveRequest
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private LeaveType type;
	
	@ManyToOne
	private Profile employee;
	
	private Boolean approved;
	
	private String reason;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date start;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date end;

	protected LeaveRequest()
	{
		
	}
	
	public LeaveType getType() {
		return type;
	}

	public void setType(LeaveType type) {
		this.type = type;
	}

	public Profile getEmployee() {
		return employee;
	}

	public void setEmployee(Profile employee) {
		this.employee = employee;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Long getId() {
		return id;
	}
	
	
}
