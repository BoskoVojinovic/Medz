package com.skenons.med.api;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.skenons.med.EmailConfig;
import com.skenons.med.data.Clinic;
import com.skenons.med.data.ClinicRating;
import com.skenons.med.data.DoctorRating;
import com.skenons.med.data.Exam;
import com.skenons.med.data.ExamPrice;
import com.skenons.med.data.ExamType;
import com.skenons.med.data.Profile;
import com.skenons.med.data.Records;
import com.skenons.med.data.Room;
import com.skenons.med.service.ClinicRatingService;
import com.skenons.med.service.ClinicService;
import com.skenons.med.service.DoctorRatingService;
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
	@Autowired ClinicRatingService crs;
	@Autowired DoctorRatingService drs;
	
	@Autowired DateConfig dc;
	
	
	@GetMapping("/clinics")
	public String getClinicsPage(
								@RequestParam(name = "name", defaultValue = "") String name,
								@RequestParam(name = "address", defaultValue = "") String address,
								HttpServletRequest request,
								Model model)
	{
		System.out.println(name + "   " + address);
		model.addAttribute("clinics",cs.getSearchByNameAndAddress(name,address));
		model.addAttribute("types",ets.getAll());
		model.addAttribute("searchName",name);
		model.addAttribute("searchAddress",address);
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
		EmailConfig.sendPExamConfirmation(oe.get());
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
			d = dc.parseFromURL(date);
		}
		catch (ParseException e)
		{
			m.addAttribute("message","Invalid url.");
			return "/error";
		}
		
		List<ExamPrice> lep = new ArrayList<ExamPrice>();
		for(ExamPrice ep : eps.getAll())
		{
			if(!ep.getExamType().equals(oet.get()))
			{
				continue;
			}
			Clinic c = ep.getClinic();
			for(Profile doc : c.getEmployees())
			{
				if(dc.getFreeIntervals(doc, d).size() > 0)
				{
					lep.add(ep);
					break;
				}
			}
		}
		
		
		m.addAttribute("type", oet.get());
		m.addAttribute("date", date);
		m.addAttribute("prices", lep);
		return "views/patientPages/clinicSearch";
	}
	
	@GetMapping("/cexam/{type}/{date}/{clinic}")//DOC SELECTION
	public String customExamStage3(	
									@RequestParam(name = "name", defaultValue = "")String name,
									@RequestParam(name = "lastName", defaultValue = "")String lastName,
									@RequestParam(name = "rating", defaultValue = "")String rating,
									@PathVariable("type") Long typeID,
									@PathVariable("date") String date,
									@PathVariable("clinic") Long clinicID,
									Model m)
	{
		Optional<ExamType> oet = ets.getOne(typeID);
		Optional<Clinic> oc = cs.getOne(clinicID);
		Date d = null;
		Double minRating = 0D;
		try
		{
			d = dc.parseFromURL(date);
			minRating = Double.parseDouble(rating);
		}
		catch (ParseException e)
		{
			m.addAttribute("message","Invalid URL!");
			return "error";
		}
		catch(NumberFormatException e) {minRating = 0D;}
		
		if(!(oet.isPresent() && oc.isPresent() && d != null))
		{
			m.addAttribute("message","Invalid URL!");
			return "error";
		}

		List<Profile> docs = ps.getAllForNameLastNameAndSpecialty(name, lastName, oet.get());
		List<Profile> docs2 = new ArrayList<Profile>();
		for(Profile p : docs)
		{
			Map<Date, Date> times = dc.getFreeIntervals(p, d);
			if(times.size() > 0 && !p.getDeleted() && p.getClinic().equals(oc.get()) && (p.getAvgReview()==null || p.getAvgReview()>=minRating))
			{
				p.setTimes(times);
				docs2.add(p);
			}
		}
		
		
		
		m.addAttribute("type", oet.get());
		m.addAttribute("date", date);
		m.addAttribute("clinic", oc.get());
		m.addAttribute("doctors", docs2);
		m.addAttribute("searchName",name);
		m.addAttribute("searchLastName",lastName);
		m.addAttribute("searchRating",minRating);
		return "views/patientPages/doctorSearch";
	}
	
	@GetMapping("/cexam/{type}/{date}/{clinic}/{doctor}")
	public String customExamStage4(	@PathVariable("type") Long typeID,
									@PathVariable("date") String date,
									@PathVariable("clinic") Long clinicID,
									@PathVariable("doctor") String doctorID,
									Model m)
	{
		m.addAttribute("type", ets.getOne(typeID).get());
		m.addAttribute("date", date);
		m.addAttribute("clinic", cs.getOne(clinicID).get());
		m.addAttribute("doctor", ps.getOne(doctorID).get());
		return "views/patientPages/cexamBooking";
	}
	
	
	@GetMapping("/cexam/{type}/{date}/{clinic}/{doctor}/confirm")
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
			e = new Exam(op.get(),od.get(),oet.get(),r,dc.parseFromURL(date));
			e.setPrice(eps.getForTypeAndClinic(oet.get(), oc.get()).get(0).getBasePrice());
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
		List<ClinicRating> lcr = crs.getForClinicAndPatient(oe.get().getRoom().getClinic(), op.get());
		if(!lcr.isEmpty())
		{
			model.addAttribute("review",lcr.get(0));
		}
		
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

		List<DoctorRating> ldr = drs.getByDoctorAndPatient(oe.get().getDoctor(), op.get());
		if(!ldr.isEmpty())
		{
			model.addAttribute("review",ldr.get(0));
		}
		
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
