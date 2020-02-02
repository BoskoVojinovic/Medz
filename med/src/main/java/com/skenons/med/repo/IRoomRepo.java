package com.skenons.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Room;

public interface IRoomRepo extends JpaRepository<Room, String>
{
  	
}