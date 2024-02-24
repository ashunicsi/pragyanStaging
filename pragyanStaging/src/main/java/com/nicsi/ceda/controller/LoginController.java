package com.nicsi.ceda.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nicsi.ceda.model.Login;
import com.nicsi.ceda.services.ILoginServices;

import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController

public class LoginController 
{
	@Autowired
	private ILoginServices logService;
	
	
	@GetMapping("login")
	public String login(@RequestParam("lang") String lang, ModelMap map, HttpSession session)
	{
		session.setAttribute("lang", lang);
		return "login";
	}
	@PostMapping(value = "login", consumes = "application/json", produces = "application/json" )
	@ResponseBody
    @CrossOrigin
	public String login(@RequestBody Login login, HttpSession session, HttpServletResponse response, ModelMap map, HttpServletRequest request)
	{
		return logService.login(login, session, response, map, request);
		
	}
	/*
	 * @GetMapping("ashu") public String Ashu(@RequestParam("email") String email,
	 * ModelMap map, HttpSession session) { session.setAttribute("lang", email);
	 * return "login"; }
	 */
	
	
}
