package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.Diagnosis;
import com.skenons.med.repo.IDiagnosisRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class DiagnosisService extends ISSService<IDiagnosisRepo, Diagnosis, Long>
{
	
}
