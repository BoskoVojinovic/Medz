package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Prescription;

public interface IPrescriptionRepo extends JpaRepository<Prescription, String>
{
  	
}