package com.skenons.med.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.SecurityConfig;
import com.skenons.med.data.Clinic;
import com.skenons.med.data.Exam;
import com.skenons.med.data.LeaveRequest;
import com.skenons.med.data.PasswordChange;
import com.skenons.med.data.Profile;
import com.skenons.med.data.enums.ProfileType;
import com.skenons.med.repo.IClinicRepo;
import com.skenons.med.repo.IExamRepo;
import com.skenons.med.repo.ILeaveRequestRepo;
import com.skenons.med.repo.IProfileRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class AdminProfileService extends ISSService<IProfileRepo, Profile, String> {
	
	@Autowired
	IExamRepo examRepo;

	@Autowired
	ILeaveRequestRepo leaveRepo;

	@Autowired
	IClinicRepo clinicRepo;
	@Autowired
	IProfileRepo profileRepo;
	public List<Profile> getDoctorsByClinic(Long id){
		return repo.findByType(ProfileType.DOCTOR).stream().filter(x -> id == x.getClinic().getId() && x.getDeleted() == false).collect(Collectors.toList());
	}
	
	public List<Profile> getSearchByNameAndLastName(String name, String lastName, Long id)
	{
		System.out.println(name + "   " + lastName + "   "+ id);
		if (name == null) {
			name = "";
		}
		if (lastName == null) {
			lastName = "";
		}
		return repo.findByNameLikeAndLastNameLike("%"+name+"%", "%"+lastName+"%").stream().filter(x -> (x.getDeleted() == false) && (x.getClinic() != null ? x.getClinic().getId() == id : false) &&  x.getType() == ProfileType.DOCTOR).collect(Collectors.toList());
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
		List<Exam> exams = examRepo.findAll().stream().filter(x -> x.getDoctor().getClinic().getId() == clinicId && ((start.equals(x.getStart())) || finish.equals(x.getFinish()) || (start.before(x.getStart()) && finish.after(x.getStart())) || (start.before(x.getFinish()) && finish.after(x.getFinish())) || (start.after(x.getStart()) && finish.before(x.getFinish())) || (start.before(x.getStart()) && finish.after(x.getFinish())))).collect(Collectors.toList());
		List<Profile> ids = new ArrayList<Profile>();
		for (Exam long1 : exams) {
			if (long1.getDoctor().getDeleted() == false)
				ids.add(long1.getDoctor());
		}
		List<LeaveRequest> requests = leaveRepo.findAll().stream().filter(x -> x.getEmployee().getClinic().getId() == clinicId && ((start.equals(x.getStart())) || finish.equals(x.getEnd()) || (start.before(x.getStart()) && finish.after(x.getStart())) || (start.before(x.getEnd()) && finish.after(x.getEnd())) || (start.after(x.getStart()) && finish.before(x.getEnd())) || (start.before(x.getStart()) && finish.after(x.getEnd())))).collect(Collectors.toList());
		
		List<Profile> r = profileRepo.findByType(ProfileType.DOCTOR).stream().filter(x -> x.getSpecialty().getId() == typeId && x.getDeleted() == false && x.getClinic().getId() == clinicId && (x.getWorkingHoursStart().before(start) || x.getWorkingHoursStart().equals(start)) &&( x.getWorkingHoursEnd().after(finish) || x.getWorkingHoursEnd().after(finish))).collect(Collectors.toList());
		for (LeaveRequest long1 : requests) {
			if (long1.getEmployee().getDeleted() == false)
				ids.add(long1.getEmployee());
		}
		
		System.out.println(r.size() + "    " + ids.size() +  "    " + requests.size());
		r.removeAll(ids);
		return r;
	}
	
	public List<Profile> getPatients(){
		return repo.findByType(ProfileType.PATIENT);
	}
	
	public List<Profile> findAllAdmins(Long id){
		return repo.findByType(ProfileType.ADMIN_CLINIC).stream().filter(x -> x.getClinic().getId() == id).collect(Collectors.toList());
	}
	
	public boolean seeIfAvailable(String id) {
		Date date = Calendar.getInstance().getTime();
		if(!examRepo.findAll().stream().filter(x -> (x.getDoctor() != null ? x.getDoctor().getIDNum().equals(id) : false) && x.getStart().after(date)).collect(Collectors.toList()).isEmpty()){
			return false;
		} else {
			return true;
		}
	}
}
