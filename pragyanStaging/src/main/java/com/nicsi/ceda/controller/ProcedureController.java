package com.nicsi.ceda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicsi.ceda.repository.DataPushRequestRepo;

@Controller
public class ProcedureController 
{
	@Autowired
	private DataPushRequestRepo repo;
	@GetMapping("callProcedureByProjectCode")
	public void callProcedureByProjectCode(@RequestParam("projectCode") int projectCode)
	{
		repo.callProcedureForProjectCode(projectCode);
	}
	@GetMapping("callingProcedure")
	public void callProcedure()
	{
		System.out.println("Before callingProcedure");
		repo.callProcedure();
		System.out.println("After callingProcedure");
	}
}
