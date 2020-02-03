package com.skenons.med.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Clinic;

public interface IClinicRepo  extends JpaRepository<Clinic, String>{

	boolean existsById(Long id);

	Optional<Clinic> findById(Long id);

	boolean existsByName(String name);

	void deleteById(Long id);

}
