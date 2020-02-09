package com.skenons.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Clinic;
import com.skenons.med.data.Room;

public interface IRoomRepo extends JpaRepository<Room, Long>
{

	List<Room> findByClinic(Clinic clinic);
	List<Room> findByFloorLikeAndNumberLike(Integer floor, Integer number);

}