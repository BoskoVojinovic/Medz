package com.skenons.med.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.LeaveRequest;

public interface ILeaveRequestRepo extends JpaRepository<LeaveRequest, String> {

	Optional<LeaveRequest> findById(Long id);

	boolean existsById(Long id);

	void deleteById(Long id);

}
