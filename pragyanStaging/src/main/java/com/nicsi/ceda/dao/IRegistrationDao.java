package com.nicsi.ceda.dao;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import com.nicsi.ceda.model.ChangePassword;
import com.nicsi.ceda.model.Registration;


public interface IRegistrationDao 
{
	String registration(Registration reg,  ModelMap map, HttpSession session);
	public String validateUsername(String email);
	public String validateCurrentPass(String email, String currentPassword);
	public String isEmailAvailable(String email);
	public String isMobileAvailable(String mobile);
	public String isPhoneNumberAvailable(String phoneNumber);
	public String changePassword(ChangePassword cp, ModelMap map, HttpSession session);
	public String getCurrentPass(String email, ModelMap map, HttpSession session);

	
}
