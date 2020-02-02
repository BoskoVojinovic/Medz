package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.ExamPrice;

public interface IExamPriceRepo extends JpaRepository<ExamPrice, String> {

}
