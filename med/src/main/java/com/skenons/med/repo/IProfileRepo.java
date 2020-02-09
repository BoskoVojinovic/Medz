package com.skenons.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.ExamType;
import com.skenons.med.data.Profile;
import com.skenons.med.data.enums.ProfileType;

public interface IProfileRepo extends JpaRepository<Profile, String>
{
	List<Profile> findByNameLike(String name); //some magic shit will know what this is...
	List<Profile> findByType(ProfileType type);
	List<Profile> findBySpecialty(ExamType type);
}
