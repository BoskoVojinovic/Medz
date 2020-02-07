package com.skenons.med.api;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.skenons.med.SecurityConfig;
import com.skenons.med.data.PasswordChange;
import com.skenons.med.data.Profile;
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
		return "views/profile/profile";
	}
}
