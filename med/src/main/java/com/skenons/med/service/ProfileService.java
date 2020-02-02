package com.skenons.med.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.repo.IProfileRepo;

@Service
public class ProfileService
{
	@Autowired
	IProfileRepo clinics;
	

	
}
