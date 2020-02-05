package com.skenons.med.data.enums;

public enum ProfileType
{
	PATIENT("PATINET"),
	NURSE("NURSE"),
	DOCTOR("DOCTOR"),
	ADMIN_CLINIC("ADMIN_CLINIC"),
	ADMIN_CENTER("ADMIN_CENTER");
	
	String name;
	private ProfileType(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
