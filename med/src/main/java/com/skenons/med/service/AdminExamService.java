package com.skenons.med.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.skenons.med.data.Exam;
import com.skenons.med.repo.IExamRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class AdminExamService extends ISSService<IExamRepo, Exam, Long> {
	public List<Exam> findByClinicId(Long clinicId){
		return repo.findAll().stream().filter(x -> x.getDoctor().getClinic().getId() == clinicId).collect(Collectors.toList());
	};
	public List<Exam> findByClinicIdD(Long clinicId){
		return repo.findAll().stream().filter(x -> x.getDoctor().getClinic().getId() == clinicId).collect(Collectors.toList());
	};
	
	public List<Exam> getRequests(Long clinicId){
		return repo.findAll().stream().filter(x -> x.getRoom() == null && x.getDoctor().getClinic().getId() == clinicId).collect(Collectors.toList());
	}
}
