package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Clinic;

public interface IClinicRepo  extends JpaRepository<Clinic, String>{

}
