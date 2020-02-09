package com.skenons.med.api;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.skenons.med.MedApplication;
import com.skenons.med.data.Clinic;
import com.skenons.med.data.ClinicRating;
import com.skenons.med.data.Diagnosis;
import com.skenons.med.data.DoctorRating;
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
		Clinic c;
		c = new Clinic("Two pines clinic", 			"Adress Line 1", 	"The best clinic in the world 1"); 		s2.saveOne(c);
		c = new Clinic("Novi Sad general hospital", "Adress Line 11", 	"The best clinic in the world 2"); 		s2.saveOne(c);
		c = new Clinic("Medica", 					"Adress Line 12", 	"The best clinic in the world 3"); 		s2.saveOne(c);
		c = new Clinic("General health",			"Adress Line 13",	"The best clinic in the world 4"); 		s2.saveOne(c);
		c = new Clinic("General medicine", 			"Adress Line 14", 	"The best clinic in the world 4"); 		s2.saveOne(c);
		
		if(!s12.getOne("1111111111111").isPresent())
		{
			s12.createProfileAs(new Profile("1111111111111", "skenons.mail@gmail.com", "123654789", "Bosko", "Vojinovic", "0604300501", "Adresica"), ProfileType.PATIENT, true);
			s12.createProfileAs(new Profile("1111111111112", "skenons.mail@gmail.com", "password2", "Nikola", "Lazic", "060-cell-02", "Address 2"), ProfileType.PATIENT, true);
			s12.createProfileAs(new Profile("1111111111113", "skenons.mail@gmail.com", "password3", "Dusica", "Lukac", "060-cell-03", "Address 3"), ProfileType.PATIENT, true);
			s12.createProfileAs(new Profile("1111111111114", "skenons.mail@gmail.com", "password3", "Petar", "Markovic", "060-cell-03", "Address 3"), ProfileType.PATIENT, true);
			
			s12.createProfileAs(new Profile("1111111111115", "skenons.mail@gmail.com", "password4", "Stefan", "Marinkovic", "060-cell-04", "Address 4"), ProfileType.NURSE, true);
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 1970);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 7);
			cal.set(Calendar.MINUTE, 0);
			Calendar cal1 = Calendar.getInstance();
			cal1.set(Calendar.YEAR, 2200);
			cal1.set(Calendar.MONTH, 0);
			cal1.set(Calendar.DAY_OF_MONTH, 1);
			cal1.set(Calendar.HOUR_OF_DAY, 16);
			cal1.set(Calendar.MINUTE, 0);
			s12.createProfileAs(new Profile("1111111111116", "skenons.mail@gmail.com", "password5", "Gregory", "House", "060-cell-05", "Address 5", cal.getTime(), cal1.getTime()), ProfileType.DOCTOR, true);
			s12.createProfileAs(new Profile("1111111111117", "skenons.mail@gmail.com", "password5", "Petar", "Bucan", "060-cell-05", "Address 5", cal.getTime(), cal1.getTime()), ProfileType.DOCTOR, true);
			s12.createProfileAs(new Profile("1111111111118", "skenons.mail@gmail.com", "password5", "Nikolina", "Jeremic", "060-cell-05", "Address 5", cal.getTime(), cal1.getTime()), ProfileType.DOCTOR, true);
			s12.createProfileAs(new Profile("1111111111119", "behemoth1616@gmail.com", "password6", "Igor", "Bogojevic", "060-cell-06", "Address 6", cal.getTime(), cal1.getTime()), ProfileType.DOCTOR, true);
			s12.createProfileAs(new Profile("1111111111120", "skenons.mail@gmail.com", "password6", "Branislav", "Petrovic", "060-cell-06", "Address 6", cal.getTime(), cal1.getTime()), ProfileType.DOCTOR, true);
			s12.createProfileAs(new Profile("1111111111121", "skenons.mail@gmail.com", "password6", "Slavica", "Jovanovic", "060-cell-06", "Address 6", cal.getTime(), cal1.getTime()), ProfileType.DOCTOR, true);
			s12.createProfileAs(new Profile("1111111111122", "skenons.mail@gmail.com", "password6", "Goran", "Ivanovic", "060-cell-06", "Address 6", cal.getTime(), cal1.getTime()), ProfileType.DOCTOR, true);
			s12.createProfileAs(new Profile("1111111111123", "skenons.mail@gmail.com", "password6", "Milan", "Todorov", "060-cell-06", "Address 6", cal.getTime(), cal1.getTime()), ProfileType.DOCTOR, true);
			
			s12.createProfileAs(new Profile("1111111111124", "behemoth1616@gmail.com", "password7", "Marija", "Stefanovic", "060-cell-07", "Address 7"), ProfileType.ADMIN_CLINIC, true);
			
			
			ExamType e;
			e = new ExamType("Head exam", "Head trauma exam");
				e.addPrice(new ExamPrice(null, s2.getOne(1L).get(), 150.00));
				e.addPrice(new ExamPrice(null, s2.getOne(3L).get(), 120.00));
				e.addPrice(new ExamPrice(null, s2.getOne(4L).get(), 130.00));
				s8.saveOne(e);
			e = new ExamType("Head x-ray", "X-ray imaging of the head");
				e.addPrice(new ExamPrice(null, s2.getOne(2L).get(), 500.00));
				e.addPrice(new ExamPrice(null, s2.getOne(4L).get(), 450.00));
				s8.saveOne(e);
			e = new ExamType("Chest x-ray", "X-ray imaging of the chest.");
				e.addPrice(new ExamPrice(null, s2.getOne(2L).get(), 500.00));
				e.addPrice(new ExamPrice(null, s2.getOne(4L).get(), 450.00));
				s8.saveOne(e);
			e = new ExamType("Full Body x-ray", "X-ray imaging of the entire body.");
				e.addPrice(new ExamPrice(null, s2.getOne(2L).get(), 500.00));
				s8.saveOne(e);
			e = new ExamType("Eye scan", "Eye pressure and prescription exam.");
				e.addPrice(new ExamPrice(null, s2.getOne(1L).get(), 50.00));
				e.addPrice(new ExamPrice(null, s2.getOne(3L).get(), 45.00));
				s8.saveOne(e);
			e = new ExamType("Phisical", "An exam of reneral phisical health.");
				e.addPrice(new ExamPrice(null, s2.getOne(1L).get(), 70.00));
				e.addPrice(new ExamPrice(null, s2.getOne(2L).get(), 75.00));
				e.addPrice(new ExamPrice(null, s2.getOne(3L).get(), 67.00));
				e.addPrice(new ExamPrice(null, s2.getOne(4L).get(), 80.00));
				e.addPrice(new ExamPrice(null, s2.getOne(5L).get(), 75.00));
				s8.saveOne(e);
			e = new ExamType("Blood test", "Lab testing of a blood sample.");
				e.addPrice(new ExamPrice(null, s2.getOne(1L).get(), 30.00));
				e.addPrice(new ExamPrice(null, s2.getOne(3L).get(), 35.00));
				e.addPrice(new ExamPrice(null, s2.getOne(4L).get(), 50.00));
				e.addPrice(new ExamPrice(null, s2.getOne(5L).get(), 39.99));
				s8.saveOne(e);
				
				
				
			Profile p;
			p = s12.getOne("1111111111116").get(); p.setClinic(s2.getOne(1L).get()); p.setSpecialty(s8.getOne(1L).get()); s12.saveOne(p);
			p = s12.getOne("1111111111117").get(); p.setClinic(s2.getOne(2L).get()); p.setSpecialty(s8.getOne(4L).get()); s12.saveOne(p);
			p = s12.getOne("1111111111118").get(); p.setClinic(s2.getOne(2L).get()); p.setSpecialty(s8.getOne(2L).get()); s12.saveOne(p);
			p = s12.getOne("1111111111119").get(); p.setClinic(s2.getOne(3L).get()); p.setSpecialty(s8.getOne(5L).get()); s12.saveOne(p);
			p = s12.getOne("1111111111120").get(); p.setClinic(s2.getOne(3L).get()); p.setSpecialty(s8.getOne(7L).get()); s12.saveOne(p);
			p = s12.getOne("1111111111121").get(); p.setClinic(s2.getOne(4L).get()); p.setSpecialty(s8.getOne(7L).get()); s12.saveOne(p);
			p = s12.getOne("1111111111122").get(); p.setClinic(s2.getOne(4L).get()); p.setSpecialty(s8.getOne(1L).get()); s12.saveOne(p);
			p = s12.getOne("1111111111123").get(); p.setClinic(s2.getOne(5L).get()); p.setSpecialty(s8.getOne(6L).get()); s12.saveOne(p);
			p = s12.getOne("1111111111124").get(); p.setClinic(s2.getOne(3L).get()); s12.saveOne(p);

			
			for(Clinic cl : s2.getAll())
			{
				for(int i = 0; i < 10; i++)
				{
					cl.addRoom(new Room(null, 1, 100+i));
					if(i%2==0)
					{
						cl.addRoom(new Room(null, 2, 200+i/2));
					}
				}
				s2.saveOne(cl);
			}
			
			
			Medicine me;
			me = new Medicine("Paracetamol", "50mg"); 				s10.saveOne(me);
			me = new Medicine("Paracetamol", "100mg");				s10.saveOne(me);
			me = new Medicine("Aspirin", "10mg"); 					s10.saveOne(me);
			me = new Medicine("Magnesium pills", "100mg"); 			s10.saveOne(me);
			me = new Medicine("Vitamin A pills", "100mg"); 			s10.saveOne(me);
			me = new Medicine("Vitamin C pills", "100mg"); 			s10.saveOne(me);
			me = new Medicine("Vitamin B6 pills", "100mg"); 		s10.saveOne(me);
			me = new Medicine("Vitamin B12 pills", "100mg"); 		s10.saveOne(me);
			me = new Medicine("Vitamin D pills", "100mg"); 			s10.saveOne(me);
			me = new Medicine("Vicodin", "50mg"); 					s10.saveOne(me);
			me = new Medicine("Vicodin", "100mg"); 					s10.saveOne(me);
			
			Diagnosis d;
			d = new Diagnosis("Chronic muscle pain", "idk"); 	s3.saveOne(d);
			d = new Diagnosis("Head swelling", "idk"); 			s3.saveOne(d);	
			d = new Diagnosis("Broken rib", "idk"); 			s3.saveOne(d);
			d = new Diagnosis("Cracked skull", "idk"); 			s3.saveOne(d);
			d = new Diagnosis("High ocular pressure", "idk"); 	s3.saveOne(d);
			d = new Diagnosis("Broken tibia bone", "idk"); 		s3.saveOne(d);
			d = new Diagnosis("Sore throat", "idk"); 			s3.saveOne(d);
			d = new Diagnosis("Skoliosis", "idk"); 				s3.saveOne(d);
			
			
			Prescription pr;
			pr = new Prescription(s10.getOne(1L).get(), "Take three times per day"); 	s11.saveOne(pr);
			pr = new Prescription(s10.getOne(2L).get(), "Take twice per day"); 			s11.saveOne(pr);
			pr = new Prescription(s10.getOne(3L).get(), "Take four times per day"); 	s11.saveOne(pr);
			pr = new Prescription(s10.getOne(4L).get(), "Take three times per day"); 	s11.saveOne(pr);
			pr = new Prescription(s10.getOne(5L).get(), "Take once per"); 				s11.saveOne(pr);
			pr = new Prescription(s10.getOne(6L).get(), "Take twice per day"); 			s11.saveOne(pr);
			pr = new Prescription(s10.getOne(7L).get(), "Take once before bed"); 		s11.saveOne(pr);
			pr = new Prescription(s10.getOne(8L).get(), "Take after every meal"); 		s11.saveOne(pr);
			
			
			Records re;
			re = new Records(s12.getOne("1111111111111").get(), 185, 80, "FTN", 			BloodType.A, 	true); 		s13.saveOne(re);
			re = new Records(s12.getOne("1111111111112").get(), 180, 110,"Dogs,cats", 		BloodType.B, 	false); 	s13.saveOne(re);
			re = new Records(s12.getOne("1111111111113").get(), 187, 88, "Paracetamol", 	BloodType.AB, 	true);		s13.saveOne(re);
			re = new Records(s12.getOne("1111111111114").get(), 177, 75, "Dairy,Gluten", 	BloodType.O, 	false); 	s13.saveOne(re);
			
			
			Exam ex;
			ExamReport exr;
			
			ex = new Exam(s12.getOne("1111111111116").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(1L).get().getRooms().get(2), s8.getOne(1L).get(), 0D, 120D); ex.setPatient(s12.getOne("1111111111111").get());
			exr = new ExamReport(s3.getOne(1L).get(), s11.getOne(1L).get(), s13.getOne("1111111111111").get()); s6.saveOne(exr);
			ex.setReport(exr); s7.saveOne(ex);
			
			ex = new Exam(s12.getOne("1111111111117").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(2L).get().getRooms().get(3), s8.getOne(4L).get(), 15D, 300D); ex.setPatient(s12.getOne("1111111111111").get());
			exr = new ExamReport(s3.getOne(2L).get(), s11.getOne(2L).get(), s13.getOne("1111111111111").get()); s6.saveOne(exr);
			ex.setReport(exr); s7.saveOne(ex);
			
			ex = new Exam(s12.getOne("1111111111118").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(2L).get().getRooms().get(2), s8.getOne(2L).get(), 15D, 150D); ex.setPatient(s12.getOne("1111111111111").get());
			exr = new ExamReport(s3.getOne(3L).get(), s11.getOne(3L).get(), s13.getOne("1111111111111").get()); s6.saveOne(exr);
			ex.setReport(exr); s7.saveOne(ex);
			
			ex = new Exam(s12.getOne("1111111111119").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(3L).get().getRooms().get(2) , s8.getOne(5L).get(), 10D, 50D);  ex.setPatient(s12.getOne("1111111111111").get());
			exr = new ExamReport(s3.getOne(4L).get(), s11.getOne(4L).get(), s13.getOne("1111111111111").get()); s6.saveOne(exr);
			ex.setReport(exr); s7.saveOne(ex);
			
			ex = new Exam(s12.getOne("1111111111120").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(3L).get().getRooms().get(3) , s8.getOne(7L).get(), 10D, 80D);  ex.setPatient(s12.getOne("1111111111111").get());
			exr = new ExamReport(s3.getOne(5L).get(), s11.getOne(5L).get(), s13.getOne("1111111111111").get()); s6.saveOne(exr);
			ex.setReport(exr); s7.saveOne(ex);
			
			ex = new Exam(s12.getOne("1111111111121").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(4L).get().getRooms().get(2) , s8.getOne(7L).get(), 0D, 110D);  ex.setPatient(s12.getOne("1111111111111").get());
			exr = new ExamReport(s3.getOne(6L).get(), s11.getOne(6L).get(), s13.getOne("1111111111111").get()); s6.saveOne(exr);
			ex.setReport(exr); s7.saveOne(ex);
			
			ex = new Exam(s12.getOne("1111111111122").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(4L).get().getRooms().get(4) , s8.getOne(1L).get(), 0D, 120D);  ex.setPatient(s12.getOne("1111111111111").get());
			exr = new ExamReport(s3.getOne(7L).get(), s11.getOne(7L).get(), s13.getOne("1111111111111").get()); s6.saveOne(exr);
			ex.setReport(exr); s7.saveOne(ex);
			
			ex = new Exam(s12.getOne("1111111111123").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(5L).get().getRooms().get(2) , s8.getOne(6L).get(), 0D, 200D);  ex.setPatient(s12.getOne("1111111111111").get());
			exr = new ExamReport(s3.getOne(8L).get(), s11.getOne(8L).get(), s13.getOne("1111111111111").get()); s6.saveOne(exr);
			ex.setReport(exr); s7.saveOne(ex);
			
			//predefined
			ex = new Exam(s12.getOne("1111111111116").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(1L).get().getRooms().get(2), e, 20D, 130D); s7.saveOne(ex);
			ex = new Exam(s12.getOne("1111111111117").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(2L).get().getRooms().get(3), e, 30D, 150D); s7.saveOne(ex);
			ex = new Exam(s12.getOne("1111111111118").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(2L).get().getRooms().get(4), e, 25D, 160D); s7.saveOne(ex);
			ex = new Exam(s12.getOne("1111111111119").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(3L).get().getRooms().get(2), e, 10D, 120D); s7.saveOne(ex);
			ex = new Exam(s12.getOne("1111111111120").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(3L).get().getRooms().get(3), e, 10D, 140D); s7.saveOne(ex);
			ex = new Exam(s12.getOne("1111111111121").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(4L).get().getRooms().get(2), e, 10D, 140D); s7.saveOne(ex);
			ex = new Exam(s12.getOne("1111111111122").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(4L).get().getRooms().get(3), e, 10D, 140D); s7.saveOne(ex);
			ex = new Exam(s12.getOne("1111111111123").get(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), s2.getOne(5L).get().getRooms().get(2), e, 10D, 140D); s7.saveOne(ex);
			
			
			ClinicRating cr;
			
			cr = new ClinicRating(s12.getOne("1111111111111").get(), s2.getOne(1L).get(), 10); s1.saveOne(cr);
			cr = new ClinicRating(s12.getOne("1111111111111").get(), s2.getOne(2L).get(), 8); s1.saveOne(cr);
			cr = new ClinicRating(s12.getOne("1111111111111").get(), s2.getOne(3L).get(), 9); s1.saveOne(cr);
			s2.calculateReviewAll();
			
			DoctorRating dr;
			
			dr = new DoctorRating(s12.getOne("1111111111111").get(), s12.getOne("1111111111116").get(), 7); s4.saveOne(dr);
			dr = new DoctorRating(s12.getOne("1111111111111").get(), s12.getOne("1111111111117").get(), 8); s4.saveOne(dr);
			dr = new DoctorRating(s12.getOne("1111111111111").get(), s12.getOne("1111111111118").get(), 10); s4.saveOne(dr);
			dr = new DoctorRating(s12.getOne("1111111111111").get(), s12.getOne("1111111111119").get(), 9); s4.saveOne(dr);
			dr = new DoctorRating(s12.getOne("1111111111111").get(), s12.getOne("1111111111120").get(), 8); s4.saveOne(dr);
			
			
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
