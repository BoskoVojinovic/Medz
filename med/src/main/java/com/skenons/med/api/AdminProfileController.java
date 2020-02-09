package com.skenons.med.api;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.skenons.med.SecurityConfig;
import com.skenons.med.data.ChangeProfile;
import com.skenons.med.data.PasswordChange;
import com.skenons.med.data.Profile;
import com.skenons.med.data.enums.ProfileType;
import com.skenons.med.service.AdminProfileService;


@Controller
public class AdminProfileController
{
	@Autowired
	AdminProfileService profileService;

  
    @PostMapping("{id}/changePassword")
    public String changePassword(@PathVariable(value = "id") String id, @Valid PasswordChange p, BindingResult br, Model model) {
    	Profile pr = profileService.getOne(id).get();
    	PasswordChange pc = new PasswordChange();
    	p.setIDNum(id);
		pc.setIDNum(id);
		model.addAttribute("passwordChange", pc);
		if(!p.getNewPassword().equals(p.getRepeatedNewPassword()))
		{
			br.addError(new FieldError("passwordChange", "repeatedNewPassword", "Passwords must match!"));
		}
		if(!SecurityConfig.passEnc().matches(p.getCurrentPassword(), pr.getPassword()))
		{
			br.addError(new FieldError("currentPassword", "currentPassword", "Wrong password!"));
		}
		if(br.hasErrors()) {
			return "views/security/verifyStaff";
		}
		profileService.changePassword(p);
		if (pr.getType().equals(ProfileType.DOCTOR)) {	
			
			return "views/profile/doctor";}
		else if (pr.getType().equals(ProfileType.ADMIN_CLINIC)) {
			return "views/profile/adminClinic";
		} else {
			return "views/profile/nurse";

		}
	}
    
    @PostMapping("{id}/changeProfilePassword")
    public String changeProfilePassword(@PathVariable(value = "id") String id, @Valid PasswordChange p, BindingResult br, Model model) {
    	Profile pr = profileService.getOne(id).get();
    	PasswordChange pc = new PasswordChange();
    	p.setIDNum(id);
		pc.setIDNum(id);
		System.out.println(p.getCurrentPassword() + "" + pr.getPassword() + "" + id);
		model.addAttribute("passwordChange", pc);
		if(!p.getNewPassword().equals(p.getRepeatedNewPassword()))
		{
			br.addError(new FieldError("passwordChange", "repeatedNewPassword", "Passwords must match!"));
		}
		if(!SecurityConfig.passEnc().matches(p.getCurrentPassword(), pr.getPassword()))
		{
			br.addError(new FieldError("currentPassword", "currentPassword", "Wrong password!"));
		}
		if(br.hasErrors()) {
			System.out.println(br.getAllErrors());

			return "views/doctorPages/changePassword";
		}
		model.addAttribute("profile", profileService.getOne(id).get());
		profileService.changePassword(p);
		return "views/doctorPages/profile";
	}
    
    @GetMapping("{id}/changeProfile")
    public String changeProfile(@PathVariable(value = "id") String id, Model model) {
    	model.addAttribute("pId", id);
    	ChangeProfile cp = new ChangeProfile();
    	Profile p = profileService.getOne(id).get();
    	cp.setAddress(p.getAddress());
    	cp.setMail(p.getEmail());
    	cp.setTelephone(p.getCellNumber());
		model.addAttribute("profile", cp);
		return "views/doctorPages/changeProfile";
	}
    @PostMapping("/{id}/changeProfile")
    public String saveChangedProfile(@PathVariable(value = "id") String id, @Valid ChangeProfile p, BindingResult br, Model model) {
		
		if (br.hasErrors()) {
			System.out.println(br.getAllErrors());

			return "views/doctorPages/changeProfile";
		}

    	Profile pr = profileService.getOne(id).get();
    	pr.setEmail(p.getMail());
    	pr.setCellNumber(p.getTelephone());
    	pr.setAddress(p.getAddress());
		profileService.saveOne(pr);
		model.addAttribute("profile", pr);
		return "views/doctorPages/profile";
	}
    
    
}
