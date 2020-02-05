package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.ExamReport;
import com.skenons.med.repo.IExamReportRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class ExamReportService extends ISSService<IExamReportRepo, ExamReport, Long>
{
	
}
