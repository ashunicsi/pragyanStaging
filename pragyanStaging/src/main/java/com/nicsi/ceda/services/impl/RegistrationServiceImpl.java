package com.nicsi.ceda.services.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.nicsi.ceda.dao.IRegistrationDao;
import com.nicsi.ceda.model.ChangePassword;
import com.nicsi.ceda.model.Registration;
import com.nicsi.ceda.services.IRegistrationService;

@Service
public class RegistrationServiceImpl implements IRegistrationService
{
	@Autowired
	private IRegistrationDao regDao;
	
	public String registration(Registration reg,  ModelMap map, HttpSession session)
	{
		return regDao.registration(reg, map, session);
	}
	
	public String validateUsername(String email)
	{
		return regDao.validateUsername(email);
	}	
	public String validateCurrentPass(String email, String currentPassword)
	{
		return regDao.validateCurrentPass(email, currentPassword);
	}
	public String isEmailAvailable(String email)
	{
		return regDao.isEmailAvailable(email);
	}
	public String isMobileAvailable(String mobile)
	{
		return regDao.isMobileAvailable(mobile);
	}
	public String isPhoneNumberAvailable(String phoneNumber) 
	{
		return regDao.isPhoneNumberAvailable(phoneNumber);
	}
	public String changePassword(ChangePassword cp, ModelMap map, HttpSession session)
	{
		return regDao.changePassword(cp, map, session);
	}
	public String getCurrentPass(String emailId, ModelMap map, HttpSession session)
	{
		return regDao.getCurrentPass(emailId, map, session);
	}
	
}
