package com.nicsi.ceda.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nicsi.ceda.model.DataPushRequest;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.repository.DataPushRequestRepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class AdminController 
{
	@Autowired(required = true)
	private Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private DataPushRequestRepo pushRepo;
	
	@GetMapping("getProjectCode")
	public String getProjectCode(@RequestParam("username") String username)
	{
		List<Tbl_Project_Detail_Intrim> proj = projRepo.findByUsernameAndProjectApprovalStatus(username, 1);
		JSONArray levelList = new JSONArray();
		for(Tbl_Project_Detail_Intrim p : proj)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("projectCode", p.getProjectCode());
			levelList.put(jsonobj);
		}
		
		return levelList.toString();
	}
	@GetMapping("callProcedure")
	public String pushData(@RequestParam("schemeCode") int projectCode, @RequestParam("username") String username)
	{
		String status = "";
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = currentDate.format(formatter);
		java.sql.Date sqlDate = java.sql.Date.valueOf(formattedDate);
		int attempts = pushRepo.countNoOfAttempts(projectCode, sqlDate);
		if(attempts == 2)
		{
			status = "You have already pushed the data 2 times in a day, try in next date...";
		}
		else
		{
			
			pushRepo.callProcedureForProjectCode(projectCode);
			
			DataPushRequest data = new DataPushRequest();
			
			data.setProjectCode(projectCode);
			data.setPushedDate(sqlDate);
			data.setUsername(username);
			pushRepo.save(data);
			status = "Data pushed successfully...";
		}
		return status;
	}
	
}
