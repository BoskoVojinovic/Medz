package com.skenons.med.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.skenons.med.data.Clinic;
import com.skenons.med.data.ExamPrice;
import com.skenons.med.data.ExamType;
import com.skenons.med.data.Profile;
import com.skenons.med.data.enums.ProfileType;
import com.skenons.med.service.ClinicService;
import com.skenons.med.service.ExamTypeService;
import com.skenons.med.service.ProfileService;

@Controller
public class PageController
{
	
	@Autowired ProfileService ps;
	@Autowired ExamTypeService es;
	@Autowired ClinicService cs;

	@GetMapping("/login")
	public String showLoginForm()
	{
		return "views/login";
	}
	
	
	
	@GetMapping("/debug")
	public String debug(Model m)
	{
		if(!ps.getOne("1111111111111").isPresent())
		{
			ps.createProfileAs(new Profile("1111111111111", "mail1@mail.com", "password1", "Name1", "LastName1", "060-cell-01", "Address 1"), ProfileType.PATIENT);
			ps.createProfileAs(new Profile("1111111111112", "mail2@mail.com", "password2", "Name2", "LastName2", "060-cell-02", "Address 2"), ProfileType.PATIENT);
			ps.createProfileAs(new Profile("1111111111113", "mail3@mail.com", "password3", "Name3", "LastName3", "060-cell-03", "Address 3"), ProfileType.PATIENT);
			ps.createProfileAs(new Profile("1111111111114", "mail4@mail.com", "password4", "Name4", "LastName4", "060-cell-04", "Address 4"), ProfileType.NURSE);
			ps.createProfileAs(new Profile("1111111111115", "mail5@mail.com", "password5", "Name5", "LastName5", "060-cell-05", "Address 5"), ProfileType.DOCTOR);
			ps.createProfileAs(new Profile("1111111111116", "mail6@mail.com", "password6", "Name6", "LastName6", "060-cell-06", "Address 6"), ProfileType.DOCTOR);
			ps.createProfileAs(new Profile("1111111111117", "mail7@mail.com", "password7", "Name7", "LastName7", "060-cell-07", "Address 7"), ProfileType.ADMIN_CLINIC);
			
			Clinic c;
			c = new Clinic("Clinic1", "Adress1", "The best clinic in the world 1"); cs.saveOne(c);
			c = new Clinic("Clinic2", "Adress2", "The best clinic in the world 2"); cs.saveOne(c);
			c = new Clinic("Clinic3", "Adress3", "The best clinic in the world 3"); cs.saveOne(c);
			c = new Clinic("Clinic4", "Adress4", "The best clinic in the world 4"); cs.saveOne(c);
			
			ExamType e;
			e = new ExamType("Head exam", "idk"); e.addPrice(new ExamPrice(null, cs.getOne(1L).get(), 100)); 	es.saveOne(e);
			e = new ExamType("Arm exam", "idk"); e.addPrice(new ExamPrice(null, cs.getOne(2L).get(), 150)); 	es.saveOne(e);
			e = new ExamType("Hand exam", "idk"); e.addPrice(new ExamPrice(null, cs.getOne(2L).get(), 200)); 	es.saveOne(e);
			e = new ExamType("Foot exam", "idk"); e.addPrice(new ExamPrice(null, cs.getOne(3L).get(), 250)); 	es.saveOne(e);

			Profile p;
			p = ps.getOne("1111111111115").get(); p.setClinic(cs.getOne(1L).get()); p.setSpecialty(e); ps.saveOne(p);
			p = ps.getOne("1111111111116").get(); p.setClinic(cs.getOne(3L).get()); p.setSpecialty(e); ps.saveOne(p);
			p = ps.getOne("1111111111117").get(); p.setClinic(cs.getOne(3L).get()); p.setSpecialty(e); ps.saveOne(p);
			
			
			m.addAttribute("title","DEBUG MSG");
			m.addAttribute("msg","Database loaded!");
			return "views/index/index";
		}
		m.addAttribute("title","DEBUG MSG");
		m.addAttribute("msg","Database already full!");
		return "views/index/index";
	}
}
