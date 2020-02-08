package com.skenons.med.api;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.skenons.med.data.Clinic;
import com.skenons.med.data.Exam;
import com.skenons.med.data.ExamType;
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
	
	
	
	@GetMapping("clinicSearch")
	public String clinicSearchResultPage(@RequestParam(name = "examtype") Long typeID, @RequestParam(name="date") String date, Model m)
	{
		
		
		m.addAttribute("type",ets.getOne(typeID).get());//TODO: check if exist, throw error page!
		m.addAttribute("date", date);
		
		
		//TODO: filter clinics for time availability!
		m.addAttribute("prices",eps.getForType(ets.getOne(typeID).get()));
		
		return "views/patientPages/clinicSearch";
	}
	
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
		
		List<ExamType> let = ets.getAll();
		
		model.addAttribute("types",let);
		
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
	
	@GetMapping("/review/clinic/{basedOnExam}")
	public String getClinicReviewPage(@PathVariable(value = "basedOnExam") Long examID, HttpServletRequest request, Model model)
	{
		Principal pr = request.getUserPrincipal();
		Optional<Profile> op = ps.getOne(pr.getName());
		Optional<Exam> oe = es.getOne(examID);
		if(!oe.isPresent())
		{
			model.addAttribute("message","The exam based on which you're reviewing does not exist. How did you get here?");
			return "error";
		}
		if(!oe.get().getPatient().equals(op.get()))
		{
			model.addAttribute("message","You can only review based on exams you attended! How did you get here?");
			return "error";
		}
		if(oe.get().getReport()==null)
		{
			model.addAttribute("message","You can only review based on exams in the past!");
			return "error";
		}
		//TODO: check existing review?
		
		return "views/patientPages/clinicReview";
	}
	
	@GetMapping("/review/doctor/{basedOnExam}")
	public String getDoctorReviewPage(@PathVariable(value = "basedOnExam") Long examID, HttpServletRequest request, Model model)
	{
		Principal pr = request.getUserPrincipal();
		Optional<Profile> op = ps.getOne(pr.getName());
		Optional<Exam> oe = es.getOne(examID);
		if(!oe.isPresent())
		{
			model.addAttribute("message","The exam based on which you're reviewing does not exist. How did you get here?");
			return "error";
		}
		if(!oe.get().getPatient().equals(op.get()))
		{
			model.addAttribute("message","You can only review based on exams you attended! How did you get here?");
			return "error";
		}
		if(oe.get().getReport()==null)
		{
			model.addAttribute("message","You can only review based on exams in the past!");
			return "error";
		}

		//TODO: check existing review?
		
		return "views/patientPages/doctorReview";
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
