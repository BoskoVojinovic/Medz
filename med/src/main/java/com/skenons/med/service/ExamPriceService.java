package com.skenons.med.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skenons.med.data.Clinic;
import com.skenons.med.data.ExamPrice;
import com.skenons.med.data.ExamType;
import com.skenons.med.repo.IExamPriceRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class ExamPriceService extends ISSService<IExamPriceRepo, ExamPrice, Long>
{
	public List<ExamPrice> getForType(ExamType et)
	{
		return repo.findByExamType(et);
	}
	public List<ExamPrice> getForTypeAndClinic(ExamType et, Clinic c)
	{
		return repo.findByExamTypeAndClinic(et,c);
	}
}
