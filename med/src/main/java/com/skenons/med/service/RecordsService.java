package com.skenons.med.service;

import org.springframework.stereotype.Service;

import com.skenons.med.data.Records;
import com.skenons.med.repo.IRecordsRepo;
import com.skenons.med.service.generic.ISSService;

@Service
public class RecordsService extends ISSService<IRecordsRepo, Records, Long>
{
	
}
