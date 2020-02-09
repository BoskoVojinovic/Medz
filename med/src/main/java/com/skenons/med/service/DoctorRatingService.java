package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.DoctorRating;
import com.skenons.med.repo.IDoctorRatingRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class DoctorRatingService extends ISSService<IDoctorRatingRepo, DoctorRating, Long>
{
	
}
