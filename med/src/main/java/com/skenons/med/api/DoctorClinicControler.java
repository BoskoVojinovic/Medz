package com.skenons.med.api;

import java.util.Calendar;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skenons.med.EmailConfig;
import com.skenons.med.data.Exam;
import com.skenons.med.data.LeaveRequest;
import com.skenons.med.data.PasswordChange;
import com.skenons.med.service.AdminExamPricesService;
import com.skenons.med.service.AdminExamService;
import com.skenons.med.service.AdminExamTypeService;
import com.skenons.med.service.AdminProfileService;
import com.skenons.med.service.AdminRoomService;
import com.skenons.med.service.ClinicService;
import com.skenons.med.service.LeaveRequestService;
import com.skenons.med.service.ProfileService;

@Controller
@RequestMapping("/clinic")
public class DoctorClinicControler {
	@Autowired
	private ClinicService clinicService;
	@Autowired
	private LeaveRequestService leaveRequestService;

	@Autowired
	private AdminProfileService profileService;

	@Autowired
	private AdminExamService examService;

	@Autowired
	private ProfileService profileServices;

	@Autowired
	private AdminRoomService roomService;

	@Autowired
	private AdminExamPricesService examPriceService;

	@Autowired
	private AdminExamTypeService examTypeService;

	@GetMapping("/{id}/doctors/{doctorId}/patients")
	public String showDoctors(@PathVariable(value = "id") Long id, @PathVariable(value = "doctorId") String doctorId,
			Model model) {
		System.out.println(id + "ASD");
		model.addAttribute("doctorId", doctorId);
		model.addAttribute("clinicId", id);
		model.addAttribute("patients", profileService.getPatients());
		return "views/doctorPages/patients";
	}
	

	@GetMapping("/{id}/doctors/{doctorId}/profile")
	public String showDoctorsProfile(@PathVariable(value = "doctorId") String doctorId,@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASD");

		model.addAttribute("clinicId", id);
		model.addAttribute("profile", profileService.getOne(doctorId).get());
		return "views/doctorPages/profile";
	}
	
	@GetMapping("/{id}/doctors/{doctorId}/changePassword")
	public String changePassword(@PathVariable(value = "doctorId") String doctorId,@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASD");
		PasswordChange pc = new PasswordChange();
		pc.setIDNum(doctorId);
		model.addAttribute("passwordChange", pc);
		model.addAttribute("clinicId", id);
		model.addAttribute("profile", profileService.getOne(doctorId).get());
		return "views/doctorPages/changePassword";
	}
	@GetMapping("/{id}/doctors/{doctorId}/leaveRequest")
	public String leaveRequest(@PathVariable(value = "doctorId") String doctorId,@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASD");
		PasswordChange pc = new PasswordChange();
		pc.setIDNum(doctorId);
		model.addAttribute("passwordChange", pc);
		model.addAttribute("clinicId", id);
		model.addAttribute("leaveRequest", new LeaveRequest());
		return "views/doctorPages/leaveRequest";
	}
	
	@PostMapping("/{id}/doctors/{doctorId}/leaveRequest")
	public String saveLeaveRequest(@PathVariable(value = "doctorId") String doctorId,@PathVariable(value = "id") Long id,@Valid LeaveRequest leaveRequest, BindingResult br, Model model) {
		System.out.println(id +"ASD");
		PasswordChange pc = new PasswordChange();
		pc.setIDNum(doctorId);
		leaveRequest.setApproved(null);
		leaveRequest.setEmployee(profileService.getOne(doctorId).get());
		leaveRequestService.saveOne(leaveRequest);
		
		EmailConfig.sendNewLeaveRequestMail(profileService.findAllAdmins(id), leaveRequest);
		model.addAttribute("passwordChange", pc);
		model.addAttribute("clinicId", id);
		model.addAttribute("profile", profileService.getOne(doctorId).get());
		return "views/profile/doctor";
	}



	@GetMapping("/{id}/doctors/{doctorId}/patients/{patientId}")
	public String showPatient(@PathVariable(value = "patientId") String patientId, @PathVariable(value = "id") Long id,
			@PathVariable(value = "doctorId") String doctorId, Model model) {
		System.out.println(id + "ASD");
		model.addAttribute("patientId", patientId);
		model.addAttribute("doctorId", doctorId);
		model.addAttribute("clinicId", id);
		model.addAttribute("patients", profileService.getPatients());
		return "views/doctorPages/patient";
	}

	@GetMapping("/{id}/doctors/{doctorId}/patients/{patientId}/newExam")
	public String newExam(@PathVariable(value = "patientId") String patientId, @PathVariable(value = "id") Long id,
			@PathVariable(value = "doctorId") String doctorId, Model model) {
		System.out.println(id + "ASD");
		model.addAttribute("patientId", patientId);
		model.addAttribute("doctorId", doctorId);
		model.addAttribute("clinicId", id);
		model.addAttribute("examSlot", new Exam());
		return "views/doctorPages/newExam";
	}

	@PostMapping("/{id}/doctors/{doctorId}/patients/{patientId}/newExam")
	public String addExamSlot(@PathVariable(value = "patientId") String patientId, @PathVariable(value = "id") Long id,
			@PathVariable(value = "doctorId") String doctorId, @Valid Exam exam, BindingResult br, Model model) {
		System.out.println(exam.getDuration() + "" + exam.getStart() + "ASadD" + exam.getType() + "ASD");
		model.addAttribute("clinicId", id);
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(exam.getStart());
		cal.setTime(exam.getFinish());
		int year = cal1.get(Calendar.YEAR);
		int month = cal1.get(Calendar.MONTH);
		int day = cal1.get(Calendar.DAY_OF_MONTH);
		cal.set(year, month, day);
		exam.setFinish(cal.getTime());
		exam.setType(examTypeService.getOne(profileService.getOne(doctorId).get().getSpecialty().getId()).get());
		exam.setDoctor(profileService.getOne(doctorId).get());
		exam.setPatient(profileService.getOne(patientId).get());
		examService.saveOne(exam);
		return "views/doctorPages/patient";
	}

}
