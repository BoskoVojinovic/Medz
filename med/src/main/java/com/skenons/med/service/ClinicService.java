package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.Clinic;
import com.skenons.med.repo.IClinicRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class ClinicService extends ISSService<IClinicRepo, Clinic, Long>
{
	
}
