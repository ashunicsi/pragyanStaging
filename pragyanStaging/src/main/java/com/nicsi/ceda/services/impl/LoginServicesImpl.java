package com.nicsi.ceda.services.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.nicsi.ceda.dao.ILoginDao;
import com.nicsi.ceda.model.Login;
import com.nicsi.ceda.services.ILoginServices;


@Service
public class LoginServicesImpl implements ILoginServices
{
	@Autowired
	private ILoginDao loginDao;
	
	public String login(Login login, HttpSession session, HttpServletResponse response, ModelMap map, HttpServletRequest request) 
	{
		return loginDao.login(login, session, response, map, request);
	}
	

}
