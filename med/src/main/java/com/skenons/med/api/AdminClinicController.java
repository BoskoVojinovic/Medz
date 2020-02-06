package com.skenons.med.api;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import com.skenons.med.data.Clinic;
import com.skenons.med.data.ExamPrice;
import com.skenons.med.data.ExamType;
import com.skenons.med.data.Profile;
import com.skenons.med.data.Room;
import com.skenons.med.data.enums.ProfileType;
import com.skenons.med.service.AdminExamPricesService;
import com.skenons.med.service.AdminExamTypeService;
import com.skenons.med.service.AdminProfileService;
import com.skenons.med.service.AdminRoomService;
import com.skenons.med.service.ClinicService;
import com.skenons.med.service.ProfileService;

@Controller
@RequestMapping("/clinic")
public class AdminClinicController {
	@Autowired
	private ClinicService clinicService;
	@Autowired
	private AdminProfileService profileService;
	

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
	
	@GetMapping("/{id}/rooms")
	public String showRooms(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);

		model.addAttribute("rooms", roomService.getRoomsByClinic(id));
		return "views/adminPages/rooms";
	}
	
	@GetMapping("/{id}/rooms/form")
	public String addRoomsForm(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		model.addAttribute("active", true);
		model.addAttribute("room", new Room());
		return "views/adminPages/roomForm";
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
			return "views/adminPages/examTypeForm";
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

		profile.setClinic(clinicService.getOne(id).get());
		profile.setType(ProfileType.DOCTOR);
		if(br.hasErrors())
		{

			return "views/adminPages/doctorForm";
		}

		profile.setClinic(clinicService.getOne(id).get());
		if (profileService.exists(profile.getIDNum())) {
			model.addAttribute("exist", true);
			return "views/adminPages/doctorForm";
		}
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
