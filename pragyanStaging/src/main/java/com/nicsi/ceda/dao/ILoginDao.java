package com.nicsi.ceda.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.nicsi.ceda.model.Login;

@Component
public interface ILoginDao 
{
	String login(Login login, HttpSession session, HttpServletResponse response, ModelMap map, HttpServletRequest request);
	
}
