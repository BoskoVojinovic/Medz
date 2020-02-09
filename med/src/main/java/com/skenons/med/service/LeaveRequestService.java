package com.skenons.med.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.skenons.med.data.Clinic;
import com.skenons.med.data.LeaveRequest;
import com.skenons.med.data.Profile;
import com.skenons.med.repo.ILeaveRequestRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class LeaveRequestService extends ISSService<ILeaveRequestRepo, LeaveRequest, Long>
{
	public List<LeaveRequest> getAllForEmployee(Profile p)
	{
		return repo.findByEmployee(p);
	}
	
	public List<LeaveRequest> findByClinic(Clinic c){
		return repo.findAll().stream().filter(x -> x.getEmployee().getClinic() == c).collect(Collectors.toList());
	}
}
