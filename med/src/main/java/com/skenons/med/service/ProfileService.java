package com.skenons.med.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.EmailConfig;
import com.skenons.med.SecurityConfig;
import com.skenons.med.data.DoctorRating;
import com.skenons.med.data.ExamType;
import com.skenons.med.data.Profile;
import com.skenons.med.data.enums.ProfileType;
import com.skenons.med.repo.IProfileRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class ProfileService extends ISSService<IProfileRepo, Profile, String>
{
	@Autowired DoctorRatingService drs;
	
	public List<Profile> getAllForType(ProfileType type)
	{
		return repo.findByType(type);
	}
	
	public List<Profile> getAllForSpecialty(ExamType type)
	{
		return repo.findBySpecialty(type);
	}
	
	public List<Profile> getAllForNameLastNameAndSpecialty(String name, String lastName, ExamType type)
	{
		return repo.findByNameLikeAndLastNameLikeAndSpecialty("%"+name+"%", "%"+lastName+"%", type);
	}
	
	public ProfileType getType(String id)
	{
		if(!exists(id))
		{
			return null;
		}
		return getOne(id).get().getType(); 
	}

	public void createPatient(@Valid Profile p)
	{
		createProfileAs(p, ProfileType.PATIENT);
	}
	
	public void createProfileAs(@Valid Profile p, ProfileType type)
	{
		createProfileAs(p, type, false);
	}
	
	public void createProfileAs(@Valid Profile p, ProfileType type, Boolean preVerified)
	{
		p.setVerified(preVerified);
		p.setPassword(SecurityConfig.passEnc().encode(p.getPassword()));
		p.setRePassword(SecurityConfig.passEnc().encode(p.getRePassword()));
		p.setType(type);
		repo.save(p);
		EmailConfig.sendVerificationMail(p);
	}
	
	public Profile getOrCreateMainAdmin()
	{
		Optional<Profile> lp = repo.findById("0000000000000");
		if(lp.isPresent())
		{
			return lp.get();
		}
		Profile p = new Profile("0000000000000", "admin@medz.com", SecurityConfig.passEnc().encode("admin123"), "Dr. Lisa", "Cuddy", "0601234567", "Greg's house ;)");
		p.setVerified(true);
		p.setType(ProfileType.ADMIN_CENTER);
		repo.save(p);
		return p;
	}
	
	public void calculateReviewOne(Profile p)
	{
		double sum = 0;
		double count = 0;
		for(DoctorRating dr : drs.getAll())
		{
			if(dr.getDoctor().equals(p))
			{
				sum += dr.getRating();
				count++;
			}
		}
		if(count > 0)
		{
			p.setAvgReview(sum/count);
			saveOne(p);
		}
	}
	
	public void calculateReviewMulti(List<Profile> docs)
	{
		for(Profile p : docs)
		{
			calculateReviewOne(p);
		}
	}
	
	public void calculateReviewAll()
	{
		for(Profile p : repo.findAll())
		{
			calculateReviewOne(p);
		}
	}
}
