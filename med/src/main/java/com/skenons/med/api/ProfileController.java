package com.skenons.med.api;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.skenons.med.data.enums.ProfileType;
import com.skenons.med.service.ProfileService;

@Controller
public class ProfileController
{
	@Autowired
	private ProfileService ps;
	
	@GetMapping("/profiles")
	public String openProfile(Model model, @RequestParam(defaultValue = "") String name)
	{
		//model.addAttribute("profiles", ps.findByName(name));
		return "views/list";
	}
	
	@GetMapping("/profile")
	public String openProfile(HttpServletRequest request, Model model)
	{
		Principal p = request.getUserPrincipal();
		if(p==null)
		{
			return "index";//just in case :P
		}
		
		ProfileType pt = ps.getType(p.getName());
		model.addAttribute("profile", ps.getOne(p.getName()).get());
		
		switch(pt)
		{
		case PATIENT: return "views/profile/patient";
		case NURSE: return "views/profile/nurse";
		case DOCTOR: return "views/profile/doctor";
		case ADMIN_CLINIC: return "views/profile/adminClinic";
		case ADMIN_CENTER: return "views/profile/adminCenter";
		}
		
		return "index";
	}
}
