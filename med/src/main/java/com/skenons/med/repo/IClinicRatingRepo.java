package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.ClinicRating;

public interface IClinicRatingRepo extends JpaRepository<ClinicRating, Long>
{
  	
}