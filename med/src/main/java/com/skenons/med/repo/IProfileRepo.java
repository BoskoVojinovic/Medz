package com.skenons.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Profile;

public interface IProfileRepo extends JpaRepository<Profile, String>
{

	List<Profile> findByNameLike(String name); //some magic shit will know what this is...
  	
}
