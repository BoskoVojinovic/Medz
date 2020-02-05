package com.skenons.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Exam;
import com.skenons.med.data.Profile;

public interface IExamRepo extends JpaRepository<Exam, Long>
{
	List<Exam> findByPatient(Profile patient);
	List<Exam> findByDoctor(Profile doctor);
}
