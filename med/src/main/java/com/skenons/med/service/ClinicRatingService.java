package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.ClinicRating;
import com.skenons.med.repo.IClinicRatingRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class ClinicRatingService extends ISSService<IClinicRatingRepo, ClinicRating, Long>
{
	
}