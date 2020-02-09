package com.skenons.med;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skenons.med.data.Exam;
import com.skenons.med.data.LeaveRequest;
import com.skenons.med.data.Profile;
import com.skenons.med.service.ExamService;
import com.skenons.med.service.LeaveRequestService;

@Component
public class DateConfig
{
	@Autowired
	LeaveRequestService lrs;
	@Autowired
	ExamService es;
	
	public Date parseFromURL(String s) throws ParseException
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		return df.parse(s);
	}
	
	public boolean includesDay(Date day, Date start, Date end)
	{
		return day.compareTo(start) >= 0 && day.compareTo(end) <= 0;
	}
	
	public boolean isOnLeave(Profile p, Date day)
	{
		if(lrs==null)
		{
			System.out.println("asdfsadfsafasfsafsadf");
		}
		for(LeaveRequest lr : lrs.getAllForEmployee(p))
		{
			if(lr.getApproved() != null && lr.getApproved() && includesDay(day, lr.getStart(), lr.getEnd()))
			{
				return true;
			}
		}
		return false;
	}
	
	public List<Exam> examsOnDay(Profile doctor, Date day)
	{
		List<Exam> examsOn = es.getForDoctor(doctor).stream().filter(e->
		{		
			return e.getStart().getYear()==day.getYear() && e.getStart().getMonth()==day.getMonth() && e.getStart().getDate()==day.getDate();
		}).collect(Collectors.toList());
		return examsOn;
	}
	
	public boolean diff30mins(Date d1, Date d2)
	{
		return d2.getTime()-d1.getTime()>1000*60*30; //Min interval 30mins
	}
	
	public Map<Date, Date> getFreeIntervals(Profile doctor, Date day)
	{
		Map<Date, Date> map = new HashMap<Date, Date>();
		if(isOnLeave(doctor, day))
		{
			return map;
		}
		if(examsOnDay(doctor, day).isEmpty())
		{
			map.put(doctor.getWorkingHoursStart(), doctor.getWorkingHoursEnd());
			return map;
		}
		//To turn off this entire feature, add at least 1 hardcoded mapping!
		List<Exam> sorted = examsOnDay(doctor, day).stream().sorted((e1,e2)-> //assumed no existing overlaps
		{
			return e1.getStart().compareTo(e2.getStart());
		}).collect(Collectors.toList());
		Date running = new Date(day.getTime());
		running.setHours(doctor.getWorkingHoursStart().getHours()); //I KNOW, TRUST ME I KNOW!
		running.setMinutes(doctor.getWorkingHoursEnd().getMinutes());
		for(Exam e : sorted)
		{
			if(diff30mins(running, e.getStart()))
			{
				map.put(running, e.getStart());
			}
			running = new Date(day.getTime());
			running.setHours(e.getFinish().getHours());
			running.setMinutes(e.getFinish().getMinutes());
		}
		Date fin = new Date(day.getTime());
		fin.setHours(doctor.getWorkingHoursEnd().getHours());
		fin.setMinutes(doctor.getWorkingHoursEnd().getMinutes());
		if(diff30mins(running,fin))
		{
			map.put(running, fin);
		}
		return map;
	}
}
