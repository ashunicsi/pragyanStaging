package com.nicsi.ceda.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.nicsi.ceda.model.ChangePassword;
import com.nicsi.ceda.model.Registration;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.services.IRegistrationService;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class RegistrationController 
{
	@Autowired
	private IRegistrationService regService;
	//@ApiIgnore
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	
	
	
	@GetMapping("registration")
	public String registration(@RequestParam("lang") String lang, ModelMap map, HttpSession session)
	{
		session.setAttribute("lang", lang);
		return "registration";
	}
	
	@PostMapping("registration")
	@ResponseBody
    @CrossOrigin
	public String registration(@RequestBody Registration reg, ModelMap map, HttpSession session)
	{
		logger.info("Data for Registration = "+reg);
		
		System.out.println("Data for Registration =  "+reg);
		
		regService.registration(reg, map, session);
		return "sucess";
	}
	@PostMapping("changePassword")
	public String changePassword(@RequestBody ChangePassword cp, ModelMap map, HttpSession session)
	{
		return regService.changePassword(cp, map, session);
	}
	@GetMapping("validateUsername")
	public @ResponseBody String validateUsername(@RequestParam("emailId") String email)
	{
		String status = regService.validateUsername(email);
		String temp = "0";
		if(status == "success")
		{
			temp = "0";
		}
		else
		{
			temp = "1";
		}
		return temp;
	}
	@GetMapping("validateCurrentPass")
	public @ResponseBody String validateCurrentPass(@RequestParam("emailId") String email, @RequestParam("currentPassword") String currentPassword)
	{
		String status = regService.validateCurrentPass(email, currentPassword);
		String temp = "0";
		if(status == "success")
		{
			temp = "0";
		}
		else
		{
			temp = "1";
		}
		return temp;
	}
	@GetMapping("validateEmail")
	public @ResponseBody String validateEmail(@RequestParam("emailId") String email)
	{
		String status = regService.isEmailAvailable(email);
		String temp = "0";
		if(status == "success")
		{
			temp = "1";
		}
		else
		{
			temp = "0";
		}
		return temp;
	}
	@GetMapping("validateMobile")
	public @ResponseBody String validateMobile(@RequestParam("mobileNumber") String mobile)
	{
		String status = regService.isMobileAvailable(mobile);
		String temp = "0";
		if(status == "success")
		{
			temp = "1";
		}
		else
		{
			temp = "0";
		}
		
		return temp;
	}
	@GetMapping("validatePhoneNumber")
	public @ResponseBody String validatePhoneNumber(@RequestParam("phoneNumber") String phoneNumber)
	{
		String status = regService.isPhoneNumberAvailable(phoneNumber);
		String temp = "0";
		if(status == "success")
		{
			temp = "1";
		}
		else
		{
			temp = "0";
		}
		
		return temp;
	}
	@PostMapping("schemeRegistration")
	public String schemeRegistration(@ModelAttribute Tbl_Project_Detail_Intrim addScheme)
	{
		return "";
	}
	@GetMapping("getCurrentPass")
	public String getCurrentPass(@RequestParam("emailId") String emailId, ModelMap map, HttpSession session)
	{
		String tempPass = regService.getCurrentPass(emailId, map, session);
		
		return tempPass;
	}
}
