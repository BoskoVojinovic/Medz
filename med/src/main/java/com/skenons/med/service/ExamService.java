package com.skenons.med.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.Exam;
import com.skenons.med.repo.IExamRepo;

@Service
public class ExamService {
	@Autowired
	private IExamRepo examRepo;
	
	public List<Exam> getAll() {
		return examRepo.findAll();
	}

	public Exam getById(Long id) {
		if(!examRepo.existsById(id)) {
			return null;
		}
		Exam exam = examRepo.findById(id).orElse(null);
		return exam;
	}
	
	public Boolean save(Exam exam) {
		examRepo.save(exam);
		return true;
	}
	
	public Boolean delete(Long id) {
		if(!examRepo.existsById(id)) {
			return false;
		}
		examRepo.deleteById(id);
		return true;
	}
	
	public Boolean update(Long id, Exam exam) {
		if(!examRepo.existsById(id)) {
			return false;
		}
		Exam oldExam = examRepo.findById(id).orElse(null);
		if(oldExam != null) {
			oldExam.setDiscount(exam.getDiscount());
			oldExam.setStart(exam.getStart());
			oldExam.setDuration(exam.getDuration());
			examRepo.save(oldExam);
			return true;
		}else {
			return false;
		}
	}

}
