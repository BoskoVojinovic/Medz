package com.skenons.med.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skenons.med.data.Room;

public interface IRoomRepo extends JpaRepository<Room, String>
{

	boolean existsById(Long id);

	Optional<Room> findById(Long id);

	boolean existsByNumber(Integer number);

	void deleteById(Long id);
  	
}