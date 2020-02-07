package com.skenons.med.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skenons.med.data.Exam;
import com.skenons.med.data.Profile;
import com.skenons.med.repo.IExamRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class ExamService extends ISSService<IExamRepo, Exam, Long>
{
	public List<Exam> getForPatient(Profile patient)
	{
		return repo.findByPatient(patient);
	}
}
