package com.skenons.med.api;

import java.util.List;
import java.util.function.Consumer;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skenons.med.data.Clinic;
import com.skenons.med.service.AdminExamPricesService;
import com.skenons.med.service.AdminProfileService;
import com.skenons.med.service.AdminRoomService;
import com.skenons.med.service.ClinicService;

@Controller
@RequestMapping("/clinic")
public class AdminClinicController {
	@Autowired
	private ClinicService clinicService;
	@Autowired
	private AdminProfileService profileService;
	@Autowired
	private AdminRoomService roomService;
	
	@Autowired
	private AdminExamPricesService examPriceService;
	
	@GetMapping("/doctors")
	public String showDoctorsPage(@PathVariable(value = "id") Long id) {
		return "views/adminPages/doctors";
	}
	
	@GetMapping("/{id}/doctors")
	public String showDoctors(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASD");
		model.addAttribute("doctors", profileService.getDoctorsByClinic(id));
		return "views/adminPages/doctors";
	}
	
	@GetMapping("/{id}/rooms")
	public String showRooms(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");

		model.addAttribute("rooms", roomService.getRoomsByClinic(id));
		return "views/adminPages/rooms";
	}
	
	@GetMapping("/{id}/examTypes")
	public String showExamTypes(@PathVariable(value = "id") Long id, Model model) {
		System.out.println(id +"ASadD");

		model.addAttribute("types", examPriceService.getTypesByClinic(id));
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
	
	@PutMapping("/{id}")
	public void update(@PathVariable Long id, @RequestBody Clinic Clinic){
		clinicService.consumeAndSave(id,(Consumer<com.skenons.med.data.Clinic>) Clinic);
	}
}
