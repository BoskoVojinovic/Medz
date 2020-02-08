package com.skenons.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.ExamPrice;
import com.skenons.med.data.ExamType;

public interface IExamPriceRepo extends JpaRepository<ExamPrice, Long>
{
	public List<ExamPrice> findByExamType(ExamType et);
}
