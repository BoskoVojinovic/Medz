package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Patient;

public interface IPatientRepo extends JpaRepository<Patient, String> {

}
