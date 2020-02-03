package com.skenons.med.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Exam;

public interface IExamRepo extends JpaRepository<Exam, String> {

	boolean existsById(Long id);

	Optional<Exam> findById(Long id);

	void deleteById(Long id);

}
