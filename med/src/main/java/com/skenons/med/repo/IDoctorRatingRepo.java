package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.DoctorRating;

public interface IDoctorRatingRepo extends JpaRepository<DoctorRating, Long> {

}
