package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Medicine;

public interface IMedicineRepo extends JpaRepository<Medicine, String> {

}
