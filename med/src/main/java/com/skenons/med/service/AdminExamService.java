package com.skenons.med.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.Exam;
import com.skenons.med.repo.IExamRepo;
import com.skenons.med.repo.ILeaveRequestRepo;
import com.skenons.med.repo.IProfileRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class AdminExamService extends ISSService<IExamRepo, Exam, Long> {
	@Autowired
	IProfileRepo pRepo;
	@Autowired
	ILeaveRequestRepo lrRepo;
	public List<Exam> findByClinicId(Long clinicId){
		
		return repo.findAll().stream().filter(x -> x.getDoctor().getClinic().getId() == clinicId).collect(Collectors.toList());
	};
	public List<Exam> findByClinicIdD(Long clinicId){
		return repo.findAll().stream().filter(x -> x.getDoctor().getClinic().getId() == clinicId).collect(Collectors.toList());
	};
	
	public List<Exam> getRequests(Long clinicId){
		return repo.findAll().stream().filter(x -> x.getPatient() != null && (x.getApproved() == null ? true : x.getApproved() == false) && x.getDoctor().getClinic().getId() == clinicId).collect(Collectors.toList());
	}
	
	public boolean seeIfAvailable(Date start, Date finish, String id) {
		if (!repo.findAll().stream().filter(x -> x.getDoctor().getIDNum().equals(id) &&  ((start.equals(x.getStart())) || finish.equals(x.getFinish()) || (start.before(x.getStart()) && finish.after(x.getStart())) || (start.before(x.getFinish()) && finish.after(x.getFinish())) || (start.after(x.getStart()) && finish.before(x.getFinish())) || (start.before(x.getStart()) && finish.after(x.getFinish())))).collect(Collectors.toList()).isEmpty()) {
			return false;
		}
		if (!lrRepo.findAll().stream().filter(x -> x.getApproved() != false && x.getEmployee().getIDNum().equals(id) && ((start.equals(x.getStart())) || finish.equals(x.getEnd()) || (start.before(x.getStart()) && finish.after(x.getStart())) || (start.before(x.getEnd()) && finish.after(x.getEnd())) || (start.after(x.getStart()) && finish.before(x.getEnd())) || (start.before(x.getStart()) && finish.after(x.getEnd())))).collect(Collectors.toList()).isEmpty()) {
			return false;
			
		}
		return true;
	}
}
