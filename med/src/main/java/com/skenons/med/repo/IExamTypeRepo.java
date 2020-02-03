package com.skenons.med.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.ExamType;

public interface IExamTypeRepo extends JpaRepository<ExamType, String> {

	boolean existsById(Long id);

	Optional<ExamType> findById(Long id);

	boolean existsByName(String name);

	void deleteById(Long id);

}
