package com.skenons.med.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.Exam;
import com.skenons.med.data.Profile;
import com.skenons.med.data.Room;
import com.skenons.med.data.enums.ProfileType;
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
		return !repo.findAll().stream().filter(x -> room.getFloor() == x.getFloor() && room.getNumber() == x.getNumber() && room.getClinic().getId() == x.getClinic().getId()).collect(Collectors.toList()).isEmpty();
		
	}
	public List<Room> getSearchByFloorAndNumber(Integer floor, Integer number, Long id)
	{
		System.out.println(floor + "   " + number + "   "+ id);
		
		return repo.findAll().stream().filter(x -> (number != null ? x.getNumber() == number : true)&& (floor != null ? x.getFloor() == floor : true) && (x.getClinic() != null ? x.getClinic().getId() == id : false)).collect(Collectors.toList());
	}
	
	public List<Room> findAvailable(Date start, Date finish, Long clinicId){
		List<Exam> exams = examRepo.findAll().stream().filter(x -> x.getDoctor().getClinic().getId() == clinicId && ((start.equals(x.getStart())) || finish.equals(x.getFinish()) || (start.before(x.getStart()) && finish.after(x.getStart())) || (start.before(x.getFinish()) && finish.after(x.getFinish())) || (start.after(x.getStart()) && finish.before(x.getFinish())) || (start.before(x.getStart()) && finish.after(x.getFinish())))).collect(Collectors.toList());
		List<Room> ids = new ArrayList<Room>();
		for (Exam long1 : exams) {
			ids.add(long1.getRoom());
		}
		
		List<Room> r = repo.findByClinic(clinicRepo.findById(clinicId).get());
		r.removeAll(ids);
		Room d = null;
		for (Room room : r) {
			if (room.getNumber() == 0) {
				d = room;
			}
		}
		r.remove(d);
		return r;
	}
	
	public boolean seeIfAvailable(Long roomId) {
		Date date = Calendar.getInstance().getTime();
		if(!examRepo.findAll().stream().filter(x -> (x.getRoom() != null ? x.getRoom().getId() == roomId : false) && x.getStart().after(date)).collect(Collectors.toList()).isEmpty()){
			return false;
		} else {
			return true;
		}
	}
	
	
}