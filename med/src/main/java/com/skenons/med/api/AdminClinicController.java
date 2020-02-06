package com.skenons.med.api;

import java.util.List;
import java.util.function.Consumer;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skenons.med.data.Clinic;
import com.skenons.med.data.Profile;
import com.skenons.med.data.Room;
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
	
	@GetMapping("/{id}/rooms/{roomId}")
	public String addRoomsForm(@PathVariable(value = "id") Long id, @PathVariable(value = "roomId") Long roomId, Model model) {
		System.out.println(id +"ASadD");
		model.addAttribute("clinicId", id);
		model.addAttribute("active", false);
		model.addAttribute("roomId", roomId);
		model.addAttribute("room", roomService.getOne(roomId));
		return "views/adminPages/roomForm";
	}
	
	@PostMapping("/{id}/rooms")
	public String updateRoomsForm(@PathVariable(value = "id") Long id, @Valid Room room, BindingResult br, Model model) {
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
	
	@PostMapping("/{id}/rooms/{roomId}")
	public String updateRooms(@PathVariable(value = "id") Long id, @PathVariable(value = "roomId") Long roomId, @Valid Room room, BindingResult br, Model model) {
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
	
	@PutMapping("/{id}")
	public void update(@PathVariable Long id, @RequestBody Clinic Clinic){
		clinicService.consumeAndSave(id,(Consumer<com.skenons.med.data.Clinic>) Clinic);
	}
}
