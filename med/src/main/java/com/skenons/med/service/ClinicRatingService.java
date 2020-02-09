package com.skenons.med.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skenons.med.data.Clinic;
import com.skenons.med.data.ClinicRating;
import com.skenons.med.data.Profile;
import com.skenons.med.repo.IClinicRatingRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class ClinicRatingService extends ISSService<IClinicRatingRepo, ClinicRating, Long>
{
	public List<ClinicRating> findByClinic(Clinic id){
		return repo.findByClinic(id);
	}
	public List<ClinicRating> getForClinicAndPatient(Clinic c, Profile p)
	{
		return repo.findByClinicAndPatient(c, p);
	}
}
