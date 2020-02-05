package com.skenons.med.api;

import java.security.Principal;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.skenons.med.data.enums.ProfileType;
import com.skenons.med.service.ProfileService;

@Controller
public class IndexController
{
	private String title[] = {
			"Did you know?",
			"What's new?",
			"News!",
			"Breaking news!",
			"New research!",
			"Clinic digest",
			"Doctors notes",
			"Stay informed",
			"Your health news:",
			"Message of the day"
	};
	
	private String msg[]= {
			"Health is like a box of chocolates, you never know what you gonna get.",
			"Research shows that every reaserch is fully dedicated to researching at least one another research, that someone has already researched. Science!",
			"Trust us, we're professionals. Most of us at least!",
			"Dogs cause cancer! New studies have found that walking your dog causes severe pulmonary fibrosis and even cancer. Nearby asbestos factory disagrees.",
			"Programming found to be the best method against muscle soreness",
			"You're not sick, you're just not healty enough!",
			"Life is a bitch, and then you die.",
			"Formal education is the most important stage in a persons life. Trust me, I would lie to you.",
			"Proving your worth to worthless people increases helf humility, right?",
			"Hello? Is anyone reading this. I'm being HELD AGAINST MY WILL! They put me here to ... oh no, someone's comsaef!#TGH",
			"I may have put something dirty into one of these, and you may never even know it's there. Just a bad roll of a dice and boom, scarred for life!",
			"Spending your time on comepletely useless activities increases you alpha brain waves by 3.14%.",
			"Advanced medicine is believed to have originated in ancient Greece. Something hurts? Drink wine till' the sun don't shine! They have looong summer days in Greece...",
			"If it's worth doing, it's worth overdoing!",
			"Medicaly speaking, sleep is for loosers!",
			"Good thing we don't sell health insurance, we'd be broke. ",
			"The average is not actually that high, that is just a feel-good statistic ;)",
			"Sign up for a revolutionary medical research, we take you apart, put some science in you and stitch you back together. Excess parts in a to-go bag!"
	};
	
	@Autowired
	private ProfileService ps;
	
	private Random rand = new Random();
	
	@GetMapping("/")
	public String showIndexPage(HttpServletRequest request, Model model)
	{
		Principal p = request.getUserPrincipal();
		model.addAttribute("title", title[rand.nextInt(title.length)]);
		model.addAttribute("msg", msg[rand.nextInt(msg.length)]);
		if(p==null)
		{
			return "views/index/index";//just in case :P
		}
		
		ProfileType pt = ps.getType(p.getName());
		
		
		switch(pt)
		{
		case PATIENT: return "views/index/patient";
		case NURSE: return "views/index/nurse";
		case DOCTOR: return "views/index/doctor";
		case ADMIN_CLINIC: return "views/index/adminClinic";
		case ADMIN_CENTER: return "views/index/adminCenter";
		}
		
		return "index";
	}
}
