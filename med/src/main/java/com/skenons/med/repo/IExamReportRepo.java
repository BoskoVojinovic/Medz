package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.ExamReport;

public interface IExamReportRepo extends JpaRepository<ExamReport, Long>
{
	
}
