package com.skenons.med;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MedApplication
{
	private static ConfigurableApplicationContext ctx = null;
	
	public static void main(String[] args)
	{
		ctx = SpringApplication.run(MedApplication.class, args);
	}
	
	public static void stop()
	{
		ctx.close();
	}
}
