package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.Prescription;
import com.skenons.med.repo.IPrescriptionRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class PrescriptionService extends ISSService<IPrescriptionRepo, Prescription, Long>
{
	
}
