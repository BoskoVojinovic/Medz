package com.skenons.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Clinic;
import com.skenons.med.data.DoctorRating;
import com.skenons.med.data.Profile;

public interface IDoctorRatingRepo extends JpaRepository<DoctorRating, Long> {
  	List<DoctorRating> findByDoctor(Clinic id);

	List<DoctorRating> findByDoctorAndPatient(Profile d, Profile p);
}
