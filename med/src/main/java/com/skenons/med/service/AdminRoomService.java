package com.skenons.med.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.Exam;
import com.skenons.med.data.Room;
import com.skenons.med.repo.IClinicRepo;
import com.skenons.med.repo.IExamRepo;
import com.skenons.med.repo.IRoomRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class AdminRoomService extends ISSService<IRoomRepo, Room, Long> {
	@Autowired
	IExamRepo examRepo;
	@Autowired
	IClinicRepo clinicRepo;
	public List<Room> getRoomsByClinic(Long id){
		return repo.findByClinic(clinicRepo.findById(id).get());
	}
	
	public boolean checkIfExists(Room room) {
		return !repo.findAll().stream().filter(x -> room.getFloor() == x.getFloor() && room.getNumber() == x.getNumber()).collect(Collectors.toList()).isEmpty();
		
	}
	
	public List<Room> findAvailable(Date start, Date finish, Long clinicId){
		List<Exam> exams = examRepo.findAll().stream().filter(x -> x.getDoctor().getClinic().getId() == clinicId && (start.before(x.getStart()) && finish.after(x.getStart())) || (start.before(x.getDuration()) && finish.after(x.getDuration())) || (start.after(x.getStart()) && finish.before(x.getDuration())) || (start.before(x.getStart()) && finish.after(x.getDuration()))).collect(Collectors.toList());
		List<Room> ids = new ArrayList<Room>();
		for (Exam long1 : exams) {
			ids.add(long1.getRoom());
		}
		
		List<Room> r = repo.findByClinic(clinicRepo.findById(clinicId).get());
		r.removeAll(ids);
		return r;
	}
}