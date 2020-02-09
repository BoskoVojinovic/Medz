package com.skenons.med.api;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.servlet.view.RedirectView;

import com.skenons.med.DateConfig;
import com.skenons.med.data.Clinic;
import com.skenons.med.data.Exam;
import com.skenons.med.data.ExamPrice;
import com.skenons.med.data.ExamType;
import com.skenons.med.data.Profile;
import com.skenons.med.data.Records;
import com.skenons.med.data.Room;
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
	
	
	@GetMapping("/clinics")
	public String getClinicsPage(HttpServletRequest request, Model model)
	{
		model.addAttribute("clinics",cs.getAll());
		
		List<ExamType> let = ets.getAll();
		
		model.addAttribute("types",let);
		
		return "views/patientPages/clinicList";
	}
	//=================================================================================================================================================
	@GetMapping("/pexam/{clinicID}")
	public String predefExamStage1(
								@PathVariable(value = "clinicID") Long clinicID, 
								Model model)
	{
		Optional<Clinic> oc = cs.getOne(clinicID);
		if(!oc.isPresent())
		{
			model.addAttribute("message","That clinic does not exist. How did you get here?");
			return "error";
		}
		List<Exam> exs = new ArrayList<Exam>();
		for(Exam e : es.getAll())
		{
			if(e.getRoom()!=null && e.getRoom().getClinic().equals(oc.get()) && e.getPatient()==null)//predefined!
			{
				exs.add(e);
			}
		}
		model.addAttribute("clinicName",oc.get().getName());
		model.addAttribute("exams",exs);
		return "views/patientPages/examOffers";
	}
	
	@GetMapping("/pexam/{clinicID}/{examID}")
	public RedirectView predefExamStage2(
								@PathVariable(value = "clinicID") Long clinicID,
								@PathVariable(value = "examID") Long examID,
								HttpServletRequest request,
								Model model)
	{
		Principal pr = request.getUserPrincipal();
		Optional<Profile> op = ps.getOne(pr.getName());
		Optional<Clinic> oc = cs.getOne(clinicID);
		Optional<Exam> oe = es.getOne(examID);
		if(!oc.isPresent() || !oe.isPresent() || oe.get().getPatient()!=null)
		{
			model.addAttribute("message","Invalid url.");
			new RedirectView("/error");
		}
		oe.get().setPatient(op.get());
		es.saveOne(oe.get());
		return new RedirectView("/examHistory");
	}
	
	//===============================================================================================================================================
	
	@GetMapping("/cexam")
	public RedirectView customExamStage1(
									@RequestParam(name = "examtype") Long typeID,
									@RequestParam(name="date") String date,
									Model m)
	{	
		return new RedirectView("/cexam/"+typeID+"/"+date);
	}
	
	@GetMapping("/cexam/{type}/{date}")
	public String customExamStage2(	@PathVariable("type") Long typeID,
									@PathVariable("date") String date,
									Model m)
	{
		Optional<ExamType> oet = ets.getOne(typeID);
		Date d = null;
		try
		{
			d = DateConfig.parseFromURL(date);
		}
		catch (ParseException e)
		{
			m.addAttribute("message","Invalid url.");
			return "/error";
		}
		
		m.addAttribute("type", oet.get());
		m.addAttribute("date", date);
		
		
		List<ExamPrice> lep = eps.getAll().stream().filter((e)->
		{
			if(!e.getExamType().equals(oet.get()))
			{
				return false;
			}
			Clinic c = e.getClinic();
			List<Profile> doctors = c.getEmployees().stream().filter((doc)->
			{
				//da li je doca doc slobodan?
				return true;
			}).collect(Collectors.toList());
			return !doctors.isEmpty();
		
		}).collect(Collectors.toList());
		m.addAttribute("prices", lep);
		return "views/patientPages/clinicSearch";
	}
	
	@GetMapping("/cexam/{type}/{date}/{clinic}")
	public String customExamStage3(	@PathVariable("type") Long typeID,
									@PathVariable("date") String date,
									@PathVariable("clinic") Long clinicID,
									Model m)
	{
		
		
		
		m.addAttribute("type", ets.getOne(typeID).get());//TODO: check if exist, throw error page!
		m.addAttribute("date", date);
		m.addAttribute("clinic", cs.getOne(clinicID).get());
		m.addAttribute("doctors", ps.getAllForSpecialty(ets.getOne(typeID).get()));//TODO: filter doctors
		return "views/patientPages/doctorSearch";
	}
	
	@GetMapping("/cexam/{type}/{date}/{clinic}/{doctor}")
	public String customExamStage4(	@PathVariable("type") Long typeID,
									@PathVariable("date") String date,
									@PathVariable("clinic") Long clinicID,
									@PathVariable("doctor") String doctorID,
									Model m)
	{
		m.addAttribute("type", ets.getOne(typeID).get());//TODO: check if exist, throw error page!
		m.addAttribute("date", date);
		m.addAttribute("clinic", cs.getOne(clinicID).get());
		m.addAttribute("doctor", ps.getOne(doctorID).get());
		return "views/patientPages/cexamBooking";
	}
	
	
	@GetMapping("/cexam/{type}/{date}/{clinic}/{doctor}/confirm") //TODO: change to post mapping, remove confirmation?
	public RedirectView customExamStage5(
									HttpServletRequest request,
									@PathVariable("type") Long typeID,
									@PathVariable("date") String date,
									@PathVariable("clinic") Long clinicID,
									@PathVariable("doctor") String doctorID,
									Model m)
	{
		Principal pr = request.getUserPrincipal();
		Optional<Profile> op = ps.getOne(pr.getName());
		Optional<Profile> od = ps.getOne(doctorID);
		Optional<ExamType> oet = ets.getOne(typeID);
		Optional<Clinic> oc = cs.getOne(clinicID);
		if(!(op.isPresent() && od.isPresent() && oet.isPresent() && oc.isPresent()))
		{
			m.addAttribute("message","Invalid url.");
			new RedirectView("/error");
		}
		Room r = null;
		for(Room rr : oc.get().getRooms())
		{
			if(rr.getFloor()==0 && rr.getNumber()==0)
			{
				r = rr;
			}
		}
		if(r==null)
		{
			m.addAttribute("message","Internal error.");
			new RedirectView("/error");
		}
		Exam e;
		try
		{
			e = new Exam(op.get(),od.get(),oet.get(),r,DateConfig.parseFromURL(date));
			es.saveOne(e);
		}
		catch (ParseException e1)
		{
			m.addAttribute("message","Internal date error.");
			new RedirectView("/error");
			e1.printStackTrace();
		}
		
		return new RedirectView("/examHistory");
	}
	
	//=========================================================================================================================================================
	
	
	
	
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
