package com.skenons.med.data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PasswordChange {
	@Size(min = 13, max = 13)
	private String IDNum;
	@NotEmpty @Size(min = 8)
	private String currentPassword;
	@NotEmpty @Size(min = 8)
	private String newPassword;
	@NotEmpty @Size(min = 8)
	private String repeatedNewPassword;
	
	public PasswordChange() {
		
	}
	
	public PasswordChange(String idNum,@NotEmpty String cP, @NotEmpty String nP, @NotEmpty String rNP) {
		super();
		this.currentPassword = cP;
		this.IDNum = idNum;
		this.newPassword=nP;
		this.repeatedNewPassword = rNP;
	}

	public String getIDNum() {
		return IDNum;
	}

	public void setIDNum(String iDNum) {
		IDNum = iDNum;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatedNewPassword() {
		return repeatedNewPassword;
	}

	public void setRepeatedNewPassword(String repeatedNewPassword) {
		this.repeatedNewPassword = repeatedNewPassword;
	}
	
}
