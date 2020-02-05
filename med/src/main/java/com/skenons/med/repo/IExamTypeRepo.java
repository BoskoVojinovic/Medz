package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.ExamType;

public interface IExamTypeRepo extends JpaRepository<ExamType, Long>
{
	
}
