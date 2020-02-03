package com.skenons.med.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.LeaveRequest;
import com.skenons.med.repo.ILeaveRequestRepo;

@Service
public class LeaveRequestService {
	@Autowired
	private ILeaveRequestRepo leaveRequestRepo;
	
	public List<LeaveRequest> getAll() {
		return leaveRequestRepo.findAll();
	}

	public LeaveRequest getById(Long id) {
		if(!leaveRequestRepo.existsById(id)) {
			return null;
		}
		LeaveRequest leaveRequest = leaveRequestRepo.findById(id).orElse(null);
		return leaveRequest;
	}
	
	public Boolean save(LeaveRequest leaveRequest) {
		leaveRequestRepo.save(leaveRequest);
		return true;
	}
	
	public Boolean delete(Long id) {
		if(!leaveRequestRepo.existsById(id)) {
			return false;
		}
		leaveRequestRepo.deleteById(id);
		return true;
	}
	
	public Boolean update(Long id, LeaveRequest leaveRequest) {
		if(!leaveRequestRepo.existsById(id)) {
			return false;
		}
		LeaveRequest oldLeaveRequest = leaveRequestRepo.findById(id).orElse(null);
		if(oldLeaveRequest != null) {
			oldLeaveRequest.setApproved(leaveRequest.getApproved());
			oldLeaveRequest.setType(leaveRequest.getType());
			oldLeaveRequest.setEnd(leaveRequest.getEnd());
			oldLeaveRequest.setStart(leaveRequest.getStart());
			leaveRequestRepo.save(oldLeaveRequest);
			return true;
		}else {
			return false;
		}
	}
}
