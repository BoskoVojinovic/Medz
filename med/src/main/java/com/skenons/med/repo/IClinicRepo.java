package com.skenons.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Clinic;

public interface IClinicRepo  extends JpaRepository<Clinic, Long>
{
	public List<Clinic> findByNameLikeAndAddressLike(String name, String address);
}
