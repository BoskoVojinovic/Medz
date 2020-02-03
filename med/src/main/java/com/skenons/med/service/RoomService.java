package com.skenons.med.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skenons.med.data.Room;
import com.skenons.med.repo.IRoomRepo;

@Service
public class RoomService {
	@Autowired
	private IRoomRepo roomRepo;
	
	public List<Room> getAll() {
		return roomRepo.findAll();
	}

	public Room getById(Long id) {
		if(!roomRepo.existsById(id)) {
			return null;
		}
		Room room = roomRepo.findById(id).orElse(null);
		return room;
	}
	
	public Boolean save(Room room) {
		if(roomRepo.existsByNumber(room.getNumber())) {
			return false;
		}
		roomRepo.save(room);
		return true;
	}
	
	public Boolean delete(Long id) {
		if(!roomRepo.existsById(id)) {
			return false;
		}
		roomRepo.deleteById(id);
		return true;
	}
	
	public Boolean update(Long id, Room room) {
		if(!roomRepo.existsById(id)) {
			return false;
		}
		Room oldRoom = roomRepo.findById(id).orElse(null);
		if(oldRoom != null) {
			oldRoom.setNumber(room.getNumber());
			oldRoom.setFloor(room.getFloor());
			roomRepo.save(oldRoom);
			return true;
		}else {
			return false;
		}
	}
}
