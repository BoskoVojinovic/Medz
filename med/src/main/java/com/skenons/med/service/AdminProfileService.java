package com.skenons.med.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.SecurityConfig;
import com.skenons.med.data.Exam;
import com.skenons.med.data.PasswordChange;
import com.skenons.med.data.Profile;
import com.skenons.med.data.Room;
import com.skenons.med.data.enums.ProfileType;
import com.skenons.med.repo.IClinicRepo;
import com.skenons.med.repo.IExamRepo;
import com.skenons.med.repo.IProfileRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class AdminProfileService extends ISSService<IProfileRepo, Profile, String> {
	
	@Autowired
	IExamRepo examRepo;

	@Autowired
	IClinicRepo clinicRepo;
	@Autowired
	IProfileRepo profileRepo;
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
	List<Exam> findByClinic(Long one){
		return examRepo.findAll().stream().filter(x -> x.getDoctor().getClinic().getId() == one).collect(Collectors.toList());
	};
	public List<Profile> findAvailable(Date start, Date finish, Long clinicId){
		List<Exam> exams = examRepo.findAll().stream().filter(x -> x.getDoctor().getClinic().getId() == clinicId && (start.before(x.getStart()) && finish.after(x.getStart())) || (start.before(x.getDuration()) && finish.after(x.getDuration())) || (start.after(x.getStart()) && finish.before(x.getDuration())) || (start.before(x.getStart()) && finish.after(x.getDuration()))).collect(Collectors.toList());
		List<Profile> ids = new ArrayList<Profile>();
		for (Exam long1 : exams) {
			ids.add(long1.getDoctor());
		}
		
		List<Profile> r = profileRepo.findByType(ProfileType.DOCTOR).stream().filter(x -> x.getClinic().getId() == clinicId && x.getWorkingHoursStart().before(start) && x.getWorkingHoursEnd().after(finish)).collect(Collectors.toList());
		r.removeAll(ids);
		return r;
	}
}
