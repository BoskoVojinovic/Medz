package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.EmployeeRating;

public interface IEmployeeRatingRepo extends JpaRepository<EmployeeRating, String> {

}
