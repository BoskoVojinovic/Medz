package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.LeaveRequest;

public interface ILeaveRequestRepo extends JpaRepository<LeaveRequest, String> {

}
