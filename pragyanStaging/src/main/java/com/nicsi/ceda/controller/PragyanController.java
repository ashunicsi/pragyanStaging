package com.nicsi.ceda.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PragyanController 
{
	@GetMapping
	public String pragyan()
	{
		System.out.println("Test");
		return "";
	}
	@GetMapping
	public String pragyan1()
	{
		System.out.println("Test");
		return ""; 
	}  
}
