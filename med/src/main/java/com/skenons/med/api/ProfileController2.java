package com.skenons.med.api;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.skenons.med.service.ProfileService;


@Controller
public class ProfileController2
{
	
	ProfileService profileService;

    @GetMapping("/")
	public String showIndexPage()
    {
		return "index";
	}
}
