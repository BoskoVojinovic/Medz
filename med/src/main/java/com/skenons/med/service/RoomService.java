package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.Room;
import com.skenons.med.repo.IRoomRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class RoomService extends ISSService<IRoomRepo, Room, Long>
{
	
}
