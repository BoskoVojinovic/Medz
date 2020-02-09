package com.skenons.med.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.Clinic;
import com.skenons.med.data.ClinicRating;
import com.skenons.med.repo.IClinicRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class ClinicService extends ISSService<IClinicRepo, Clinic, Long>
{
	@Autowired
	ClinicRatingService crs;
	
	public List<Clinic> getSearchByNameAndAddress(String name, String address)
	{
		return repo.findByNameLikeAndAddressLike("%"+name+"%", "%"+address+"%");
	}
	
	public void calculateReviewOne(Clinic c)
	{
		double sum = 0;
		double count = 0;
		for(ClinicRating cr : crs.getAll())
		{
			if(cr.getClinic().equals(c))
			{
				sum += cr.getRating();
				count++;
			}
		}
		if(count > 0)
		{
			c.setAvgReview(sum/count);
			saveOne(c);
		}
	}
	
	public void calculateReviewMulti(List<Clinic> clinics)
	{
		for(Clinic c : clinics)
		{
			calculateReviewOne(c);
		}
	}
	
	public void calculateReviewAll()
	{
		for(Clinic c : repo.findAll())
		{
			calculateReviewOne(c);
		}
	}
}
