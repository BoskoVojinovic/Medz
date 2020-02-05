package com.skenons.med.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
		return "views/register";
	}
	
	@PostMapping("/register")
	public String registerProfile(@Valid Profile p, BindingResult br, Model m)
	{
		
		if(!p.getPassword().equals(p.getRepassword()))
		{
			//br.addError(new ObjectError("repassword", "Please"));
		}
		if(br.hasErrors())
		{
			return "views/register";
		}
		if(ps.exists(p.getIDNum()))
		{
			m.addAttribute("exist", true);
			return "views/register";
		}
		ps.createPatient(p);
		return "views/registerSuccess";
	}
	
}
