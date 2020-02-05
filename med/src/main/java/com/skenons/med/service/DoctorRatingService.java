package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.ExamPrice;
import com.skenons.med.repo.IExamPriceRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class DoctorRatingService extends ISSService<IExamPriceRepo, ExamPrice, Long>
{
	
}
