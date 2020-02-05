package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.Medicine;
import com.skenons.med.repo.IMedicineRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class MedicineService extends ISSService<IMedicineRepo, Medicine, Long>
{
	
}
