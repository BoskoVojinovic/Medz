package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Diagnosis;

public interface IDiagnosisRepo extends JpaRepository<Diagnosis, Long> {

}
