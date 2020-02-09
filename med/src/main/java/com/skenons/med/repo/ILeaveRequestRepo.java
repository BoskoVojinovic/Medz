package com.skenons.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Clinic;
import com.skenons.med.data.LeaveRequest;
import com.skenons.med.data.Profile;

public interface ILeaveRequestRepo extends JpaRepository<LeaveRequest, Long>
{
	List<LeaveRequest> findByEmployee(Profile p);
	
}
