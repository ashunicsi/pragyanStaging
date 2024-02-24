package com.nicsi.ceda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController 
{
	@GetMapping("error")
	public String errorPage()
	{
		return "error";
	}
	@GetMapping("/")
	public String errorPage1()
	{
		return "error";
	}
}
