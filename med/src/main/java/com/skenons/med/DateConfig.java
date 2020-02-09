package com.skenons.med;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.skenons.med.data.LeaveRequest;
import com.skenons.med.data.Profile;
import com.skenons.med.service.LeaveRequestService;

public class DateConfig
{
	@Autowired
	static LeaveRequestService lrs;
	
	public static Date parseFromURL(String s) throws ParseException
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		return df.parse(s);
	}
	
	public static boolean includesDay(Date day, Date start, Date end)
	{
		return day.compareTo(start) >= 0 && day.compareTo(end) <= 0;
	}
	
	public static boolean isOnLeave(Profile p, Date day)
	{
		for(LeaveRequest lr : lrs.getAllForEmployee(p))
		{
			if(lr.getApproved() && includesDay(day, lr.getStart(), lr.getEnd()))
			{
				return true;
			}
		}
		return false;
	}

}
