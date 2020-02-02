package com.skenons.med.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.Profile;
import com.skenons.med.repo.IProfileRepo;

@Service
public class ProfileService
{
	@Autowired
	IProfileRepo clinics;
	@Autowired
	private IProfileRepo profileRepo;

	

	public List<Profile> getAll(){ 
		return profileRepo.findAll().stream().collect(Collectors.toList());
	};
}
