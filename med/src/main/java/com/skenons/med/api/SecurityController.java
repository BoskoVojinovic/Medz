package com.skenons.med.api;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.skenons.med.EmailConfig;
import com.skenons.med.SecurityConfig;
import com.skenons.med.data.PasswordChange;
import com.skenons.med.data.Profile;
import com.skenons.med.data.enums.ProfileType;
import com.skenons.med.service.ProfileService;

@Controller
public class SecurityController//Handling login, register and account verification
{
	@Autowired
	ProfileService ps;
	
	@GetMapping("/register")
	public String registerForm(Model model)
	{
		model.addAttribute("profile", new Profile());
		return "views/security/register";
	}
	
	@GetMapping("/login")
	public String showLoginForm()
	{
		return "views/security/login";
	}
	
	@PostMapping("/register")
	public String registerProfile(@Valid Profile p, BindingResult br, Model m)
	{
		
		if(!p.getPassword().equals(p.getRePassword()))
		{
			System.out.println("asdfasdfasdfas");
			br.addError(new FieldError("profile", "rePassword", "Passwords must match!"));
		}
		if(br.hasErrors())
		{
			return "views/security/register";
		}
		if(ps.exists(p.getIDNum()))
		{
			m.addAttribute("exist", true);
			return "views/security/register";
		}
		ps.createPatient(p);
		EmailConfig.sendVerificationMail(p);
		return "views/security/registerSuccess";
	}
	
	@GetMapping("/verify/{id}/{token}")
	public String registerForm(@PathVariable(value = "id") String id, @PathVariable(value = "token") String token, Model model)
	{
		Optional<Profile> op = ps.getOne(id);
		System.out.println(token);
		System.out.println(SecurityConfig.getVerifyToken(op.get()));
		if(!op.isPresent() || !SecurityConfig.getVerifyToken(op.get()).equals(token))
		{
			model.addAttribute("msg","Invalid verification link!");
			return "views/security/verify";
		}
		if(!op.get().getType().equals(ProfileType.PATIENT))
		{
			PasswordChange pc = new PasswordChange();
			pc.setIDNum(id);
			model.addAttribute("passwordChange", pc);
			return "views/security/verifyStaff";
		}
		if(op.get().getVerified())
		{
			model.addAttribute("msg","Account already verified! But thanks for being thorough!");
		}
		else
		{
			op.get().setVerified(true);
			ps.saveOne(op.get());
			model.addAttribute("msg","Account verified, please proceed to the login page.");
		}
		return "views/security/verify";
	}
	
}
