package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Profile;

public interface IProfileRepo extends JpaRepository<Profile, String>
{
  	
}
