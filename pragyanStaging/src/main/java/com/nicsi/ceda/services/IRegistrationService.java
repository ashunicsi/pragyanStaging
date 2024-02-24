package com.nicsi.ceda.services;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.nicsi.ceda.model.ChangePassword;
import com.nicsi.ceda.model.Registration;

@Component(value = "iRegistrationService")
public interface IRegistrationService 
{
	public String registration(Registration reg, ModelMap map, HttpSession session);
	public String validateUsername(String email);
	public String isEmailAvailable(String email);
	public String isMobileAvailable(String mobile);
	public String isPhoneNumberAvailable(String phoneNumber);
	public String changePassword(ChangePassword cp, ModelMap map, HttpSession session);
	public String validateCurrentPass(String email, String currentPassword);
	public String getCurrentPass(String email, ModelMap map, HttpSession session);
}
