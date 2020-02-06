package com.skenons.med.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.ExamPrice;
import com.skenons.med.data.ExamType;
import com.skenons.med.repo.IExamPriceRepo;
import com.skenons.med.repo.IExamTypeRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class AdminExamPricesService  extends ISSService<IExamPriceRepo, ExamPrice , Long> {
	@Autowired
	IExamTypeRepo repoP;
	public List<ExamType> getTypesByClinic(Long id){
		List<ExamType> et = new ArrayList<ExamType>();
		List<ExamPrice> r = repo.findAll().stream().filter(x -> x.getClinic().getId() == id).collect(Collectors.toList());
		for (ExamPrice examPrice : r) {
			et.add(repoP.findById(examPrice.getExamType().getId()).get());
		}
		return et;
	}
	public void deleteByExamTypeId(Long examTypeId) {
		// TODO Auto-generated method stub
		
	}
}
