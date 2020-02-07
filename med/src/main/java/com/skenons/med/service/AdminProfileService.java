package com.skenons.med.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.skenons.med.SecurityConfig;
import com.skenons.med.data.PasswordChange;
import com.skenons.med.data.Profile;
import com.skenons.med.data.enums.ProfileType;
import com.skenons.med.repo.IProfileRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class AdminProfileService extends ISSService<IProfileRepo, Profile, String> {
	public List<Profile> getDoctorsByClinic(Long id){
		return repo.findByType(ProfileType.DOCTOR).stream().filter(x -> id == x.getClinic().getId()).collect(Collectors.toList());
	}
	public void changePassword(PasswordChange pr) {
		Profile p = repo.getOne(pr.getIDNum());
		p.setPassword(SecurityConfig.passEnc().encode(pr.getNewPassword()));
		p.setRePassword(SecurityConfig.passEnc().encode(pr.getRepeatedNewPassword()));
		p.setVerified(true);
		repo.save(p);
	}
}
