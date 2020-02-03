package com.skenons.med.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.ExamType;
import com.skenons.med.repo.IExamTypeRepo;

@Service
public class ExamTypeService {

	@Autowired
	private IExamTypeRepo examTypeRepo;
	
	public List<ExamType> getAll() {
		return examTypeRepo.findAll();
	}

	public ExamType getById(Long id) {
		if(!examTypeRepo.existsById(id)) {
			return null;
		}
		ExamType examType = examTypeRepo.findById(id).orElse(null);
		return examType;
	}
	
	public Boolean save(ExamType examType) {
		if(examTypeRepo.existsByName(examType.getName())) {
			return false;
		}
		examTypeRepo.save(examType);
		return true;
	}
	
	public Boolean delete(Long id) {
		if(!examTypeRepo.existsById(id)) {
			return false;
		}
		examTypeRepo.deleteById(id);
		return true;
	}
	
	public Boolean update(Long id, ExamType examType) {
		if(!examTypeRepo.existsById(id)) {
			return false;
		}
		ExamType oldExamType = examTypeRepo.findById(id).orElse(null);
		if(oldExamType != null) {
			oldExamType.setName(examType.getName());
			oldExamType.setDescription(examType.getDescription());
			examTypeRepo.save(oldExamType);
			return true;
		}else {
			return false;
		}
	}


}
