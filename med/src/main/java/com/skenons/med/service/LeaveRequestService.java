package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.LeaveRequest;
import com.skenons.med.repo.ILeaveRequestRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class LeaveRequestService extends ISSService<ILeaveRequestRepo, LeaveRequest, Long>
{
	
}
