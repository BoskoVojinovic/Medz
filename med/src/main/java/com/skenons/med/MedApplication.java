package com.skenons.med;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.skenons.med.repo.IProfileRepo;

@SpringBootApplication
public class MedApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(MedApplication.class, args);
	}
}
