package com.skenons.med.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.Clinic;
import com.skenons.med.repo.IClinicRepo;

@Service
public class ClinicService {

	@Autowired
	private IClinicRepo clinicRepo;
	
	public List<Clinic> getAll() {
		return clinicRepo.findAll();
	}

	public Clinic getById(Long id) {
		if(!clinicRepo.existsById(id)) {
			return null;
		}
		Clinic clinic = clinicRepo.findById(id).orElse(null);
		return clinic;
	}
	
	public Boolean save(Clinic clinic) {
		if(clinicRepo.existsByName(clinic.getName())) {
			return false;
		}
		clinicRepo.save(clinic);
		return true;
	}
	
	public Boolean delete(Long id) {
		if(!clinicRepo.existsById(id)) {
			return false;
		}
		clinicRepo.deleteById(id);
		return true;
	}
	
	public Boolean update(Long id, Clinic clinic) {
		if(!clinicRepo.existsById(id)) {
			return false;
		}
		Clinic oldClinic = clinicRepo.findById(id).orElse(null);
		if(oldClinic != null) {
			oldClinic.setName(clinic.getName());
			oldClinic.setAddress(clinic.getAddress());
			oldClinic.setDescription(clinic.getDescription());
			clinicRepo.save(oldClinic);
			return true;
		}else {
			return false;
		}
	}

}
