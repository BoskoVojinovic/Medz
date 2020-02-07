package com.skenons.med.api;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	
	@GetMapping("/clinics")
	public String getClinicsPage()
	{
		return "views/patientPages/clinicList";
	}
	
	@GetMapping("/examHistory")
	public String getExamsPage()
	{
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
