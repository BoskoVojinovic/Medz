package com.skenons.med.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.skenons.med.data.DoctorRating;
import com.skenons.med.repo.IDoctorRatingRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class DoctorRatingService extends ISSService<IDoctorRatingRepo, DoctorRating, Long>
{
	public List<DoctorRating> findByClinic(Long id){
		return repo.findAll().stream().filter(x -> x.getDoctor().getClinic().getId() == id).collect(Collectors.toList());
	}
}
