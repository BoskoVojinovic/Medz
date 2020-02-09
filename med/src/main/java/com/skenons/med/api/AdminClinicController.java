package com.skenons.med.api;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skenons.med.EmailConfig;
import com.skenons.med.data.Clinic;
import com.skenons.med.data.ClinicRating;
import com.skenons.med.data.DoctorRating;
import com.skenons.med.data.Exam;
import com.skenons.med.data.ExamPrice;
import com.skenons.med.data.ExamType;
import com.skenons.med.data.LeaveRequest;
import com.skenons.med.data.Profile;
import com.skenons.med.data.Reason;
import com.skenons.med.data.Room;
import com.skenons.med.data.enums.ProfileType;
import com.skenons.med.service.AdminExamPricesService;
import com.skenons.med.service.AdminExamService;
import com.skenons.med.service.AdminExamTypeService;
import com.skenons.med.service.AdminProfileService;
import com.skenons.med.service.AdminRoomService;
import com.skenons.med.service.ClinicRatingService;
import com.skenons.med.service.ClinicService;
import com.skenons.med.service.DoctorRatingService;
import com.skenons.med.service.LeaveRequestService;
import com.skenons.med.service.ProfileService;

@Controller
@RequestMapping("/clinic")
public class AdminClinicController {
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private ClinicRatingService clinicRatingService;
	@Autowired
	private DoctorRatingService doctorRatingService;
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
	
	
	@GetMapping("/doctors")
	public String showDoctorsPage(@PathVariable(value = "id") Long id) {
		return "views/adminPages/doctors";
	}
	
	@GetMapping("/{id}/doctors")
	public String showDoctors(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASD");

		model.addAttribute("clinicId", id);
		model.addAttribute("doctors", profileService.getDoctorsByClinic(id));
		return "views/adminPages/doctors";
	}
	
	
	@GetMapping("/{id}/reports")
	public String showReports(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASD");

		model.addAttribute("clinicId", id);
		HashMap<Profile, Double> doctors = new HashMap<Profile, Double>();
		
		List<DoctorRating> dr = doctorRatingService.findByClinic(id);
		
		for (DoctorRating doctorRating : dr) {
			if (doctors.containsKey(doctorRating.getDoctor())){
				doctors.put(doctorRating.getDoctor(), doctorRating.getRating() + doctors.get(doctorRating.getDoctor()));
			} else {
				doctors.put(doctorRating.getDoctor(), doctorRating.getRating().doubleValue());
			}
		}
		for (Profile p : doctors.keySet()) {
			int i = 0;
			for (DoctorRating doctorRating : dr) {
				if (doctorRating.getDoctor() == p) {
					i++;
				}
			}
			doctors.put(p, doctors.get(p)/i);
		}
		model.addAttribute("doctorStar", doctors);
		List<ClinicRating> cr = clinicRatingService.findByClinic(clinicService.getOne(id).get());
		model.addAttribute("clinic", clinicService.getOne(id).get());
		Double ratingC = 0.00;
		for (ClinicRating clinicRating : cr) {
			ratingC +=clinicRating.getRating();
		}
		ratingC = ratingC/cr.size();
		model.addAttribute("clinicStar", ratingC);
		return "views/adminPages/ratings";
	}
	
	@GetMapping("/{id}/examSlots")
	public String showExamSlots(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASwwwwwwwwwD");

		model.addAttribute("clinicId", id);
		model.addAttribute("exams", examService.findByClinicId(id));
		return "views/adminPages/examSlots";
	}
	
	@GetMapping("/{id}/leaveRequests")
	public String showLeaveRequests(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASwwwwwwwwwD");

		model.addAttribute("clinicId", id);
		model.addAttribute("leaves", leaveRequestService.findByClinic(clinicService.getOne(id).get()));
		return "views/adminPages/leaveRequests";
	}
	
	@GetMapping("/{id}/leaveRequests/{requestId}/approve")
	public String approveLeaveRequests(@PathVariable(value = "requestId") Long requestId,@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASwwwwwwwwwD");
		LeaveRequest lr = leaveRequestService.getOne(requestId).get();
		lr.setApproved(true);
		leaveRequestService.saveOne(lr);
		EmailConfig.sendLeaveApprovalMail(leaveRequestService.getOne(requestId).get().getEmployee());
		model.addAttribute("clinicId", id);
		model.addAttribute("leaves", leaveRequestService.findByClinic(clinicService.getOne(id).get()));
		return "views/adminPages/leaveRequests";
	}
	
	@GetMapping("/{id}/leaveRequests/{requestId}/reject")
	public String rejectLeaveRequests(@PathVariable(value = "requestId") Long requestId,@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASwwwwwwwwwD");
		model.addAttribute("clinicId", id);
		model.addAttribute("reqestId", requestId);
		model.addAttribute("object", new Reason());
		model.addAttribute("leaves", leaveRequestService.findByClinic(clinicService.getOne(id).get()));
		return "views/adminPages/leaveRequestRejected";
	}

	@PostMapping("/{id}/leaveRequests/{requestId}/reject")
	public String rejectRequests(@PathVariable(value = "requestId") Long requestId,@PathVariable(value = "id") Long id, Reason reason, Model model) {
		System.out.println(id +"ASwwwwwwwwwD");
		LeaveRequest lr = leaveRequestService.getOne(requestId).get();
		lr.setApproved(false);
		leaveRequestService.saveOne(lr);
		EmailConfig.sendLeaveRejectionMail(leaveRequestService.getOne(requestId).get().getEmployee(), reason.getReason());
		model.addAttribute("clinicId", id);
		model.addAttribute("reqestId", requestId);
		model.addAttribute("leaves", leaveRequestService.findByClinic(clinicService.getOne(id).get()));
		return "views/adminPages/leaveRequests";
	}
	
	@GetMapping("/{id}/rooms")
	public String showRooms(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);

		model.addAttribute("rooms", roomService.getRoomsByClinic(id));
		return "views/adminPages/rooms";
	}
	
	@GetMapping("/{id}/examRequests")
	public String showExamRequests(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);

		model.addAttribute("exams", examService.getRequests(id));
		return "views/adminPages/examRequests";
	}
	
	@GetMapping("/{id}/examRequests/{requestId}/approve")
	public String approveRequest(@PathVariable(value = "requestId") Long requestId, @PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);

		model.addAttribute("requestId", requestId);
		Exam e = examService.getOne(requestId).get();
		model.addAttribute("rooms", roomService.findAvailable(e.getStart(), e.getFinish(), id));
		model.addAttribute("calendar", examService.findByClinicIdD(id));
		model.addAttribute("request", examService.getOne(requestId).get());
		return "views/adminPages/examRequestApproval";
	}
	@GetMapping("/{id}/examRequests/{requestId}/approved/{roomId}")
	public String approvedRequest(@PathVariable(value = "roomId") Long roomId, @PathVariable(value = "requestId") Long requestId, @PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("requestId", requestId);

		model.addAttribute("clinicId", id);
		Exam e = examService.getOne(requestId).get();
		e.setRoom(roomService.getOne(roomId).get());
		e.setPrice(examPriceService.findByExamTypeId(e.getType().getId()).getBasePrice());
		model.addAttribute("exams", examService.getRequests(id));
		examService.saveOne(e);
		return "views/adminPages/examRequests";
	}
	
	@GetMapping("/{id}/examRequests/{requestId}/findAvailable")
	public String findAvailable(@PathVariable(value = "roomId") Long roomId, @PathVariable(value = "requestId") Long requestId, @PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");

		Exam e = examService.getOne(requestId).get();
		model.addAttribute("requestId", requestId);
		model.addAttribute("rooms", roomService.getAll());
		model.addAttribute("calendar", examService.findByClinicIdD(id));
		
		model.addAttribute("clinicId", id);
		examService.saveOne(e);
		return "views/adminPages/findAvailableExtra";
	}
	
	@GetMapping("/{id}/rooms/form")
	public String addRoomsForm(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		model.addAttribute("active", true);
		model.addAttribute("room", new Room());
		return "views/adminPages/roomForm";
	}
	
	
	@GetMapping("/getRoomsByDate")
	public String getRoomsByDate(@PathParam(value = "start") String start, @PathParam(value = "duration") String duration, Exam exam, HttpServletRequest http) {
		System.out.println(exam.getStart() +"ASadD");
		http.getSession().setAttribute("exam", exam);
		
		return "views/adminPages/examSlotForm";
	}
	
	@GetMapping("/{id}/examSlots/form")
	public String addExamSlotForm(@PathVariable(value = "id") Long id, Model model) {

		model.addAttribute("rooms", roomService.getRoomsByClinic(id));
		model.addAttribute("clinicId", id);
		model.addAttribute("active", true);
		model.addAttribute("types", examPriceService.getTypesByClinic(id));

			model.addAttribute("rooms", null);
			model.addAttribute("doctors", null);
		
		model.addAttribute("examSlot", new Exam());
		return "views/adminPages/examSlotForm";
	}
	@PostMapping("/{id}/examSlots")
	public String addExamSlot(@PathVariable(value = "id") Long id, @Valid Exam exam, BindingResult br, Model model) {
		System.out.println(exam.getDuration() + ""+ exam.getStart() +"ASadD"  +exam.getType() + "ASD");
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
		System.out.println(exam.getStart() + "ASDSADSA" + exam.getFinish());
		if (exam.getDuration() != null && exam.getStart() != null && exam.getType() != null) {
			model.addAttribute("rooms", roomService.findAvailable(exam.getStart(), exam.getFinish(), id));
			System.out.println("AS");
			model.addAttribute("doctors", profileService.findAvailable(exam.getStart(),exam.getFinish(), exam.getType().getId(), id));
		} else {
			model.addAttribute("rooms", null);
			model.addAttribute("doctors", null);
		
		}
		model.addAttribute("types", examPriceService.getTypesByClinic(id));
		model.addAttribute("examSlot", exam);
		if (exam.getDoctor() != null && exam.getDuration() != null && exam.getStart() != null && exam.getRoom() != null && exam.getDiscount() != null) {
			exam.setPrice(examPriceService.findByExamTypeId(exam.getType().getId()).getBasePrice());
			
			examService.saveOne(exam);
			model.addAttribute("exams", examService.findByClinicId(id));

			return "views/adminPages/examSlots";
		}
		return "views/adminPages/examSlotForm";
	}
	
	
	@GetMapping("/{id}/examTypes/form")
	public String addExamTypesForm(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		model.addAttribute("active", true);
		model.addAttribute("examPrice", new ExamPrice());
		return "views/adminPages/examTypesForm";
	}
	@GetMapping("/{id}/doctors/form")
	public String addDoctorsForm(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		model.addAttribute("profile", new Profile());
		model.addAttribute("specialities", examPriceService.getTypesByClinic(id));
		return "views/adminPages/doctorForm";
	}
	
	@GetMapping("/{id}/rooms/{roomId}")
	public String updateRoomsForm(@PathVariable(value = "id") Long id, @PathVariable(value = "roomId") Long roomId, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		model.addAttribute("active", false);
		model.addAttribute("roomId", roomId);
		model.addAttribute("room", roomService.getOne(roomId));
		return "views/adminPages/roomForm";
	}
	@GetMapping("/{id}/examTypes/{examTypeId}")
	public String updateExamTypeRoomsForm(@PathVariable(value = "id") Long id, @PathVariable(value = "examTypeId") Long examTypeId, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		model.addAttribute("active", false);
		model.addAttribute("examTypeId", examTypeId);
		model.addAttribute("examPrice", examPriceService.findByExamTypeId(examTypeId));
		return "views/adminPages/examTypesForm";
	}
	
	@GetMapping("/{id}/rooms/remove/{roomId}")
	public String removeRoom(@PathVariable(value = "id") Long id, @PathVariable(value = "roomId") Long roomId, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		model.addAttribute("roomId", roomId);
		roomService.deleteOne(roomId);
		model.addAttribute("rooms", roomService.getRoomsByClinic(id));
		return "views/adminPages/rooms";
	}
	
	@GetMapping("/{id}/examTypes/remove/{examTypeId}")
	public String removeExamType(@PathVariable(value = "id") Long id, @PathVariable(value = "examTypeId") Long examTypeId, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		model.addAttribute("examTypeId", examTypeId);
		examTypeService.deleteOne(examTypeId);
		examPriceService.deleteByExamTypeId(examTypeId);
		model.addAttribute("types", examPriceService.getTypesByClinic(id));
		return "views/adminPages/examTypes";
	}
	
	@GetMapping("/{id}/doctors/remove/{doctorId}")
	public String removeDoctor(@PathVariable(value = "id") Long id, @PathVariable(value = "doctorId") String doctorId, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		model.addAttribute("roomId", doctorId);
		profileService.deleteOne(doctorId);
		model.addAttribute("doctors", profileService.getDoctorsByClinic(id));
		return "views/adminPages/doctors";
	}
	
	@PostMapping("/{id}/rooms")
	public String addRoom(@PathVariable(value = "id") Long id, @Valid Room room, BindingResult br, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		if(br.hasErrors())
		{
			return "views/adminPages/roomForm";
		}

		room.setClinic(clinicService.getOne(id).get());
		if (roomService.checkIfExists(room)) {
			model.addAttribute("exist", true);
			return "views/adminPages/roomForm";
		}
		roomService.saveOne(room);

		model.addAttribute("rooms", roomService.getRoomsByClinic(id));
		return "views/adminPages/rooms";
	}
	@PostMapping("/{id}/examTypes")
	public String addExamType(@PathVariable(value = "id") Long id, @Valid ExamPrice examPrice, BindingResult br, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		if(br.hasErrors())
		{
			return "views/adminPages/examTypesForm";
		}
		ExamType et = new ExamType(examPrice.getExamType().getName(), examPrice.getExamType().getDescription());
		if (examTypeService.checkIfExists(et, id)) {
			System.out.println("AS");
			model.addAttribute("exist", true);
			return "views/adminPages/examTypesForm";
		}
		ExamType e = examTypeService.saveOne(et);

		examPrice.setClinic(clinicService.getOne(id).get());
		examPrice.setExamType(examTypeService.getOne(e.getId()).get());
		examPriceService.saveOne(examPrice);

		model.addAttribute("types", examPriceService.getTypesByClinic(id));
		return "views/adminPages/examTypes";
	}
	@PostMapping("/{id}/doctors")
	public String addDoctor(@PathVariable(value = "id") Long id, @Valid Profile profile, BindingResult br, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		model.addAttribute("specialities", examPriceService.getTypesByClinic(id));
		System.out.println(profile.getWorkingHoursStart() + "AFFAF");
		profile.setClinic(clinicService.getOne(id).get());
		profile.setType(ProfileType.DOCTOR);
		if(br.hasErrors())
		{
			System.out.println(profile.getWorkingHoursStart() + "AFFAF");

			return "views/adminPages/doctorForm";
		}

		profile.setClinic(clinicService.getOne(id).get());
		if (profileService.exists(profile.getIDNum())) {
			model.addAttribute("exist", true);
			return "views/adminPages/doctorForm";
		}
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(profile.getWorkingHoursEnd());
		cal1.set(Calendar.YEAR, 2100);
		profile.setWorkingHoursEnd(cal1.getTime());
		profileServices.createProfileAs(profile, profile.getType());

		model.addAttribute("doctors", profileService.getDoctorsByClinic(id));
		return "views/adminPages/doctors";
	}
	
	@PostMapping("/{id}/rooms/{roomId}")
	public String updateRoom(@PathVariable(value = "id") Long id, @PathVariable(value = "roomId") Long roomId, @Valid Room room, BindingResult br, Model model) {
		System.out.println(id + roomId +"ASadD" + room.getFloor());
		model.addAttribute("clinicId", id);

		room.setClinic(clinicService.getOne(id).get());
		if(br.hasErrors())
		{
			model.addAttribute("room", roomService.getOne(roomId));
			return "views/adminPages/roomForm";
		}
		if (roomService.checkIfExists(room)) {
			model.addAttribute("exist", true);
			return "views/adminPages/roomForm";
		}
		
		System.out.println(roomId + "ASD");
		roomService.updateOne(roomId, room);

		model.addAttribute("rooms", roomService.getRoomsByClinic(id));
		return "views/adminPages/rooms";
	}
	
	@PostMapping("/{id}/examTypes/{examTypeId}")
	public String updateExamType(@PathVariable(value = "id") Long id, @PathVariable(value = "examTypeId") Long examTypeId, @Valid ExamPrice examPrice, BindingResult br, Model model) {
		model.addAttribute("clinicId", id);

		if(br.hasErrors())
		{
			System.out.println("HASE");
			model.addAttribute("types", examPriceService.getTypesByClinic(id));
			return "views/adminPages/examTypesForm";
		}
		ExamType o = new ExamType(examPrice.getExamType().getName(),examPrice.getExamType().getDescription());
		System.out.println(examPrice.getExamType().getName() + "AAA");

		if (examTypeService.checkIfExists(o, id)) {
			System.out.println(examPrice.getExamType().getName() + "AAAAAAA");

			model.addAttribute("exist", true);
			return "views/adminPages/examTypesForm";
		}
		ExamType et = examTypeService.getOne(examTypeId).get();
		et.setDescription(examPrice.getExamType().getDescription());
		et.setName(examPrice.getExamType().getName());
		
		examTypeService.updateOne(examTypeId, et);

		ExamPrice ep = examPriceService.findByExamTypeId(examTypeId);
		ep.setBasePrice(examPrice.getBasePrice());
		examPriceService.updateOne(ep.getId(), ep);
		model.addAttribute("types", examPriceService.getTypesByClinic(id));
		return "views/adminPages/examTypes";
	}
	
	@GetMapping("/{id}/examTypes")
	public String showExamTypes(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");

		model.addAttribute("types", examPriceService.getTypesByClinic(id));
		model.addAttribute("clinicId", id);
		return "views/adminPages/examTypes";
	}
	
	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping()
	public List<Clinic> getAll(){
		return clinicService.getAll();
	}
	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/{id}")
	public void getById(@PathVariable Long id, HttpSession session){
		clinicService.getOne(id);
	}
	
	@PostMapping()
	public void create(@RequestBody Clinic clinic){
		clinicService.saveOne(clinic);
			
	}
	
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id){
		clinicService.deleteOne(id);
	}
	
}
