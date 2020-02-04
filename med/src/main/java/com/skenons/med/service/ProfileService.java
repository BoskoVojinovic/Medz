package com.skenons.med.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.skenons.med.data.Patient;
import com.skenons.med.data.Profile;
import com.skenons.med.repo.IProfileRepo;

@Service
public class ProfileService
{
	@Autowired
	private IProfileRepo profileRepo;

	

	public List<Profile> getAll()
	{ 
		return profileRepo.findAll();
	}

	public Optional<Profile> getOne(String id)
	{
		return profileRepo.findById(id);
	}

	public boolean exists(String id)
	{
		return getOne(id).isPresent();
	}

	public void createPatient(@Valid Profile p)
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		p.setPassword(encoder.encode(p.getPassword()));
		Patient pa = new Patient(p);
		p.setPatientProfile(pa);
		profileRepo.save(p);
	}

	public List<Profile> findByName(String name)
	{
		return profileRepo.findByNameLike("%"+name+"%");
	};
}
