package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Employee;

public interface IEmployeeRepo extends JpaRepository<Employee, String>
{
  	
}
