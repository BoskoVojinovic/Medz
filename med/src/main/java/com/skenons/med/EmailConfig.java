package com.skenons.med;

import java.util.List;
import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.skenons.med.data.LeaveRequest;
import com.skenons.med.data.Profile;

@Component
public class EmailConfig
{	
	private static JavaMailSenderImpl sender = null;
	
	private static void init()
	{
		sender = new JavaMailSenderImpl();
		sender.setHost("smtp.gmail.com");
		sender.setPort(587);
		sender.setUsername("noreply.medz@gmail.com");
		sender.setPassword("rniyomfuecqgkeke");
		Properties p = sender.getJavaMailProperties();
		p.put("mail.transport.protocol", "smtp");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "true");
		//p.put("mail.debug", "true");
	}
	
	public static void sendVerificationMail(Profile p)
	{
		sendMail(p.getEmail(), "Verify your MEDz account",
				"To start using our services, please click on the link below:\n"+
				"http://localhost:8080/verify/"+p.getIDNum()+"/"+SecurityConfig.getVerifyToken(p));
	}
	
	public static void sendLeaveApprovalMail(Profile p)
	{
		sendMail(p.getEmail(), "Leave request",
				"Request for absence/vacation has been approved!");
	}
	public static void sendLeaveRejectionMail(Profile p, String s)
	{
		sendMail(p.getEmail(), "Leave request",
				"Request for absence/vacation has been rejected! Reason for rejection : " + s);
	}
	
	public static void sendNewExamMail(List<Profile> p, Profile doctor)
	{
		for (Profile profile : p) {

			sendMail(profile.getEmail(), "New exam request",
					"Doctor" + doctor.getName() + " " + doctor.getLastName() + " sent new exam request");
		}
	}
	
	public static void sendNewLeaveRequestMail(List<Profile> p, LeaveRequest lr)
	{
		for (Profile profile : p) {

			sendMail(profile.getEmail(), "New leave request",
					"Doctor" + lr.getEmployee().getName() + " " + lr.getEmployee().getLastName() + " sent new leave request");
		}
	}
	
	public static void sendMail(String address, String subject, String text)
	{
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(address);
		msg.setSubject(subject);
		msg.setText(text);
		sendMail(msg);
	}
	
	
	public static void sendMail(SimpleMailMessage mail)
	{
		if(sender == null)
		{
			init();
		}
		sender.send(mail);
	}
}
