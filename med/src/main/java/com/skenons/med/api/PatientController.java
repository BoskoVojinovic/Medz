package com.skenons.med.api;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.skenons.med.EmailConfig;
import com.skenons.med.data.Clinic;
import com.skenons.med.data.Exam;
import com.skenons.med.data.Profile;
import com.skenons.med.data.Records;
import com.skenons.med.service.ClinicService;
import com.skenons.med.service.ExamPriceService;
import com.skenons.med.service.ExamService;
import com.skenons.med.service.ExamTypeService;
import com.skenons.med.service.ProfileService;
import com.skenons.med.service.RecordsService;
import com.skenons.med.service.RoomService;

@Controller
public class PatientController
{
	@Autowired ProfileService ps;
	@Autowired ClinicService cs;
	@Autowired RecordsService rs;
	@Autowired ExamService es;
	@Autowired ExamTypeService ets;
	@Autowired ExamPriceService eps;
	@Autowired RoomService rms;
	
	
	@PostMapping("/profile")
	public String registerProfile(HttpServletRequest request, @Valid Profile p, BindingResult br, Model m)
	{
		Principal pr = request.getUserPrincipal();
		Optional<Profile> op = ps.getOne(pr.getName());
		for(FieldError fe : br.getFieldErrors())
		{
			if(!fe.getField().equalsIgnoreCase("password"))
			{
				return "views/profile/patient";
			}
		}
		m.addAttribute("msg","Profile info updated!");
		ps.updateOne(op.get().getIDNum(), p);
		return "views/profile/patient";
	}
	
	
	@GetMapping("/clinics")
	public String getClinicsPage(HttpServletRequest request, Model model)
	{
		model.addAttribute("clinics",cs.getAll());
		
		
		return "views/patientPages/clinicList";
	}
	
	@GetMapping("/examOffers/{clinicID}")
	public String getClinicsPage(@PathVariable(value = "clinicID") Long clinicID, HttpServletRequest request, Model model)
	{
		Optional<Clinic> oc = cs.getOne(clinicID);
		if(!oc.isPresent())
		{
			model.addAttribute("message","That clinic does not exist. How did you get here?");
			return "error";
		}
		List<Exam> exs = new ArrayList<Exam>();
		
		
		
		model.addAttribute("clinicName",oc.get().getName());
		model.addAttribute("exams",exs);
		return "views/patientPages/examOffers";
	}
	
	@GetMapping("/examHistory")
	public String getExamsPage(HttpServletRequest request, Model model)
	{
		Principal pr = request.getUserPrincipal();
		Optional<Profile> op = ps.getOne(pr.getName());
		
		model.addAttribute("exams",es.getForPatient(op.get()));
		return "views/patientPages/exams";
	}
	
	@GetMapping("/records")
	public String getRecordsPage(HttpServletRequest request, Model model)
	{
		Principal p = request.getUserPrincipal();
		Optional<Records> or = rs.getOne(p.getName());
		if(!or.isPresent())
		{
			model.addAttribute("missing",true);
			return "views/patientPages/records";
		}
		model.addAttribute("records",or.get()); //TODO: FILTER PAST EXAMS! PASS AS TWO SEPARATE ATTRIBUTES!!!
		
		return "views/patientPages/records";
	}
}
