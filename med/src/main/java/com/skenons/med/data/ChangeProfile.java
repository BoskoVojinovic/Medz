package com.skenons.med.data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ChangeProfile {
	@NotEmpty @Email
	private String mail;
	@NotEmpty @Size(max = 100)

	private String address;
	@NotEmpty @Size(min = 9, max = 11)
	private String telephone;
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public ChangeProfile() {
		
	}
	
}
