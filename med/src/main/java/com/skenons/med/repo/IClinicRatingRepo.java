package com.skenons.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Clinic;
import com.skenons.med.data.ClinicRating;

public interface IClinicRatingRepo extends JpaRepository<ClinicRating, Long>
{
  	List<ClinicRating> findByClinic(Clinic id);
}