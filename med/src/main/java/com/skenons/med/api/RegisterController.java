package com.skenons.med.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.skenons.med.data.Profile;
import com.skenons.med.service.ProfileService;

@Controller
public class RegisterController
{
	@Autowired
	ProfileService ps;
	
	@GetMapping("/register")
	public String registerForm(Model model)
	{
		model.addAttribute("profile", new Profile());
		return "views/registerForm";
	}
	
	@PostMapping("/register")
	public String registerProfile(@Valid Profile p, BindingResult br, Model m)
	{
		if(br.hasErrors())
		{
			return "views/registerForm";
		}
		if(ps.exists(p.getIDNum()))
		{
			m.addAttribute("exist", true);
			return "views/registerForm";
		}
		ps.createPatient(p);
		return "views/registerSuccess";
	}
	
}
