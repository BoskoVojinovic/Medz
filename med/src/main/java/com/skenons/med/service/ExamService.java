package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.Exam;
import com.skenons.med.repo.IExamRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class ExamService extends ISSService<IExamRepo, Exam, Long>
{
	
}
