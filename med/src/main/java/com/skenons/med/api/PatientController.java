package com.skenons.med.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.skenons.med.service.ProfileService;

@Controller
public class PatientController
{
	@Autowired
	private ProfileService ps;
	
	@GetMapping("/profiles")
	public String openProfile(Model model, @RequestParam(defaultValue = "") String name)
	{
		model.addAttribute("profiles", ps.findByName(name));
		return "views/list";
	}
}
