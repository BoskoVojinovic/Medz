package com.skenons.med;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

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
