package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Exam;

public interface IExamRepo extends JpaRepository<Exam, String> {

}
