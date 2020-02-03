package com.skenons.med.api;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skenons.med.data.Profile;
import com.skenons.med.service.ProfileService;


//@RestController
//@RequestMapping("/profiles")
public class ProfileController
{
	
	ProfileService profileService;

    //@Autowired
    public void setUserService(ProfileService profileService) {
        this.profileService = profileService;
    }

    //@GetMapping()
	public ResponseEntity<List<Profile>> getAll(){
		if(profileService.getAll() == null) {
			return new ResponseEntity<List<Profile>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Profile>>(profileService.getAll(), HttpStatus.OK);
	}
}
