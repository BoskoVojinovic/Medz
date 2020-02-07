package com.skenons.med.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.standard.InstantFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.skenons.med.MedApplication;
import com.skenons.med.data.Clinic;
import com.skenons.med.data.Diagnosis;
import com.skenons.med.data.Exam;
import com.skenons.med.data.ExamPrice;
import com.skenons.med.data.ExamReport;
import com.skenons.med.data.ExamType;
import com.skenons.med.data.Medicine;
import com.skenons.med.data.Prescription;
import com.skenons.med.data.Profile;
import com.skenons.med.data.Records;
import com.skenons.med.data.Room;
import com.skenons.med.data.enums.BloodType;
import com.skenons.med.data.enums.ProfileType;
import com.skenons.med.service.ClinicRatingService;
import com.skenons.med.service.ClinicService;
import com.skenons.med.service.DiagnosisService;
import com.skenons.med.service.DoctorRatingService;
import com.skenons.med.service.ExamPriceService;
import com.skenons.med.service.ExamReportService;
import com.skenons.med.service.ExamService;
import com.skenons.med.service.ExamTypeService;
import com.skenons.med.service.LeaveRequestService;
import com.skenons.med.service.MedicineService;
import com.skenons.med.service.PrescriptionService;
import com.skenons.med.service.ProfileService;
import com.skenons.med.service.RecordsService;
import com.skenons.med.service.RoomService;

@Controller
public class DEBUGController//Handling debug options
{
	@Autowired ClinicRatingService s1;
	@Autowired ClinicService s2;
	@Autowired DiagnosisService s3;
	@Autowired DoctorRatingService s4;
	@Autowired ExamPriceService s5;
	@Autowired ExamReportService s6;
	@Autowired ExamService s7;
	@Autowired ExamTypeService s8;
	@Autowired LeaveRequestService s9;
	@Autowired MedicineService s10;
	@Autowired PrescriptionService s11;
	@Autowired ProfileService s12;
	@Autowired RecordsService s13;
	@Autowired RoomService s14;
	
	
	@GetMapping("/debugFill")
	public String debugF(Model m)
	{
		if(!s12.getOne("1111111111111").isPresent())
		{
			s12.createProfileAs(new Profile("1111111111111", "skenons.mail@gmail.com", "123654789", "Bosko", "Vojinovic", "0604300501", "Adresica"), ProfileType.PATIENT);
			s12.createProfileAs(new Profile("1111111111112", "mail2@mail.com", "password2", "Name2", "LastName2", "060-cell-02", "Address 2"), ProfileType.PATIENT);
			s12.createProfileAs(new Profile("1111111111113", "mail3@mail.com", "password3", "Name3", "LastName3", "060-cell-03", "Address 3"), ProfileType.PATIENT);
			s12.createProfileAs(new Profile("1111111111114", "mail4@mail.com", "password4", "Name4", "LastName4", "060-cell-04", "Address 4"), ProfileType.NURSE);
			s12.createProfileAs(new Profile("1111111111115", "mail5@mail.com", "password5", "Name5", "LastName5", "060-cell-05", "Address 5"), ProfileType.DOCTOR);
			s12.createProfileAs(new Profile("1111111111116", "mail6@mail.com", "password6", "Name6", "LastName6", "060-cell-06", "Address 6"), ProfileType.DOCTOR);
			s12.createProfileAs(new Profile("1111111111117", "mail7@mail.com", "password7", "Name7", "LastName7", "060-cell-07", "Address 7"), ProfileType.ADMIN_CLINIC);
			
			Clinic c;
			c = new Clinic("Clinic1", "Adress1", "The best clinic in the world 1"); s2.saveOne(c);
			c = new Clinic("Clinic2", "Adress2", "The best clinic in the world 2"); s2.saveOne(c);
			c = new Clinic("Clinic3", "Adress3", "The best clinic in the world 3"); s2.saveOne(c);
			c = new Clinic("Clinic4", "Adress4", "The best clinic in the world 4"); s2.saveOne(c);
			
			ExamType e;
			e = new ExamType("Head exam", "idk"); e.addPrice(new ExamPrice(null, s2.getOne(1L).get(), 100)); 	s8.saveOne(e);
			e = new ExamType("Arm exam", "idk"); e.addPrice(new ExamPrice(null, s2.getOne(2L).get(), 150)); 	s8.saveOne(e);
			e = new ExamType("Hand exam", "idk"); e.addPrice(new ExamPrice(null, s2.getOne(2L).get(), 200)); 	s8.saveOne(e);
			e = new ExamType("Foot exam", "idk"); e.addPrice(new ExamPrice(null, s2.getOne(3L).get(), 250)); 	s8.saveOne(e);

			Profile p;
			p = s12.getOne("1111111111111").get(); p.setVerified(true); s12.saveOne(p);
			p = s12.getOne("1111111111115").get(); p.setClinic(s2.getOne(1L).get()); p.setSpecialty(e); s12.saveOne(p);
			p = s12.getOne("1111111111116").get(); p.setClinic(s2.getOne(3L).get()); p.setSpecialty(e); s12.saveOne(p);
			p = s12.getOne("1111111111117").get(); p.setClinic(s2.getOne(3L).get()); p.setSpecialty(e); p.setVerified(true); s12.saveOne(p);
			
			Room r;
			r = new Room(s2.getOne(1L).get(), 1, 101); s14.saveOne(r);
			r = new Room(s2.getOne(1L).get(), 2, 201); s14.saveOne(r);
			r = new Room(s2.getOne(1L).get(), 2, 202); s14.saveOne(r);
			r = new Room(s2.getOne(2L).get(), 1, 101); s14.saveOne(r);
			r = new Room(s2.getOne(2L).get(), 2, 201); s14.saveOne(r);
			r = new Room(s2.getOne(2L).get(), 2, 202); s14.saveOne(r);
			r = new Room(s2.getOne(3L).get(), 3, 301); s14.saveOne(r);
			r = new Room(s2.getOne(4L).get(), 4, 401); s14.saveOne(r);
			
			
			Records re = new Records(s12.getOne("1111111111111").get(), 185, 80, "FTN", BloodType.A); s13.saveOne(re);
			
			Exam ex;
			Diagnosis d;
			ExamReport exr;
			Prescription pr;
			Medicine md;
			
			
			d = new Diagnosis("Smoritis extremus", "idk"); s3.saveOne(d);
			md = new Medicine("Lil pill", "Rainbow colored"); s10.saveOne(md);
			pr = new Prescription(md, "Take once per day until you die"); s11.saveOne(pr);
			exr = new ExamReport(d, pr, re); s6.saveOne(exr);
			ex = new Exam(s12.getOne("1111111111115").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), r, e, 20D, 120D); ex.setPatient(s12.getOne("1111111111111").get()); 
			ex.setReport(exr); s7.saveOne(ex);
			
			
			d = new Diagnosis("Smoritis maximus extremus", "idk"); s3.saveOne(d);			
			md = new Medicine("Lil pill 2", "Red colored"); s10.saveOne(md);
			pr = new Prescription(md, "Take three times per day until you die"); s11.saveOne(pr);
			exr = new ExamReport(d, pr, re); s6.saveOne(exr);
			ex = new Exam(s12.getOne("1111111111115").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), r, e, 25D, 300D); ex.setPatient(s12.getOne("1111111111111").get());
			ex.setReport(exr); s7.saveOne(ex);
			
			
			d = new Diagnosis("Smoritis serious extremus", "idk"); s3.saveOne(d);			
			md = new Medicine("Lil pill 3", "Blue colored"); s10.saveOne(md);
			pr = new Prescription(md, "Take twice per day until you die"); s11.saveOne(pr);
			exr = new ExamReport(d, pr, re); s6.saveOne(exr);
			ex = new Exam(s12.getOne("1111111111116").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), r, e, 15D, 150D); ex.setPatient(s12.getOne("1111111111111").get());
			ex.setReport(exr); s7.saveOne(ex);
			
			
			d = new Diagnosis("Smoritis mortalis extremus", "idk"); s3.saveOne(d);			
			md = new Medicine("Lil pill 4", "Green colored"); s10.saveOne(md);
			pr = new Prescription(md, "Take four times per day until you die"); s11.saveOne(pr);
			exr = new ExamReport(d, pr, re); s6.saveOne(exr);
			ex = new Exam(s12.getOne("1111111111116").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), r, e, 10D, 20D);  ex.setPatient(s12.getOne("1111111111111").get());
			ex.setReport(exr); s7.saveOne(ex);
			
			
			
			
			m.addAttribute("title","DEBUG MSG");
			m.addAttribute("msg","Database loaded!");
			return "views/index/index";
		}
		m.addAttribute("title","DEBUG MSG");
		m.addAttribute("msg","Database already full!");
		return "views/index/index";
	}
	
	
	//@GetMapping("/debugBurn")
	public String debugB(Model m)
	{
		
		s13.deleteAll();
		s7.deleteAll();
		s12.deleteAll();
		s9.deleteAll();
		s5.deleteAll();
		s6.deleteAll();
		s11.deleteAll();
		s14.deleteAll();
		s8.deleteAll();
		s2.deleteAll();
		s1.deleteAll();
		s4.deleteAll();
		s3.deleteAll();
		s10.deleteAll();
		
		
		m.addAttribute("title","DEBUG MSG");
		m.addAttribute("msg","Database burned, restart the server!");
		
		MedApplication.stop();
		
		return "views/index/index";
	}
}
