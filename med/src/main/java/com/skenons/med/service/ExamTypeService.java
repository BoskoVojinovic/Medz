package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.ExamType;
import com.skenons.med.repo.IExamTypeRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class ExamTypeService extends ISSService<IExamTypeRepo, ExamType, Long>
{
	
}
