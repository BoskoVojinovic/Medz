package com.skenons.med.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.ExamType;
import com.skenons.med.repo.IExamPriceRepo;
import com.skenons.med.repo.IExamTypeRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class AdminExamTypeService  extends ISSService<IExamTypeRepo, ExamType, Long> {
	@Autowired
	IExamPriceRepo repoP;
	public boolean checkIfExists(ExamType examType, Long id) {
		return !repoP.findAll().stream().filter(x -> examType.getName().equals(x.getExamType().getName()) && id == x.getClinic().getId()).collect(Collectors.toList()).isEmpty();
		
	}
}
