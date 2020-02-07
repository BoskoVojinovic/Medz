package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Profile;
import com.skenons.med.data.Records;

public interface IRecordsRepo extends JpaRepository<Records, String>
{
  	Records findByPatient(Profile patient);
}