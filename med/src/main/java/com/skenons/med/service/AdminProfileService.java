package com.skenons.med.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.SecurityConfig;
import com.skenons.med.data.Exam;
import com.skenons.med.data.PasswordChange;
import com.skenons.med.data.Profile;
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
	public List<Profile> findAvailable(Date start, Date finish, Long typeId, Long clinicId){
		List<Exam> exams = examRepo.findAll().stream().filter(x -> x.getDoctor().getClinic().getId() == clinicId && (start.before(x.getStart()) && finish.after(x.getStart())) || (start.before(x.getFinish()) && finish.after(x.getFinish())) || (start.after(x.getStart()) && finish.before(x.getFinish())) || (start.before(x.getStart()) && finish.after(x.getFinish()))).collect(Collectors.toList());
		List<Profile> ids = new ArrayList<Profile>();
		for (Exam long1 : exams) {
			ids.add(long1.getDoctor());
		}
		
		List<Profile> r = profileRepo.findByType(ProfileType.DOCTOR).stream().filter(x -> x.getSpecialty().getId() == typeId && x.getClinic().getId() == clinicId && x.getWorkingHoursStart().before(start) && x.getWorkingHoursEnd().after(finish)).collect(Collectors.toList());
	System.out.println(r.size() + "    " + ids.size());
		r.removeAll(ids);
		return r;
	}
	
	public List<Profile> getPatients(){
		return repo.findByType(ProfileType.PATIENT);
	}
}
