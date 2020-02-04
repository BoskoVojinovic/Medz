package com.skenons.med.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController
{

	@GetMapping("/")
	public String showIndexPage()
	{
		return "index";
	}

	@RequestMapping(value = "/plogin", method = {RequestMethod.POST,RequestMethod.GET})
	public String showPLoginForm()
	{
		return "views/plogin";
	}

	@RequestMapping(value = "/slogin", method = {RequestMethod.POST,RequestMethod.GET})
	public String showSLoginForm()
	{
		return "views/slogin";
	}

	@GetMapping("/profile")
	public String openProfile()
	{
		return "views/profile";
	}
}
