package com.skenons.med.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.Room;
import com.skenons.med.repo.IClinicRepo;
import com.skenons.med.repo.IRoomRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class AdminRoomService extends ISSService<IRoomRepo, Room, Long> {
	
	@Autowired
	IClinicRepo clinicRepo;
	public List<Room> getRoomsByClinic(Long id){
		return repo.findByClinic(clinicRepo.findById(id).get());
	}
}