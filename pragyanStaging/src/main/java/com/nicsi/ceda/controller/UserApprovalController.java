package com.nicsi.ceda.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicsi.ceda.model.ApproveUserDTO;
import com.nicsi.ceda.model.Login;
import com.nicsi.ceda.model.Registration;
import com.nicsi.ceda.model.Sector;
import com.nicsi.ceda.repository.DepartmentRepo;
import com.nicsi.ceda.repository.LoginRepo;
import com.nicsi.ceda.repository.MLevelRepo;
import com.nicsi.ceda.repository.RegistrationRepo;
import com.nicsi.ceda.repository.SectorRepo;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
public class UserApprovalController 
{
	@Autowired
	private LoginRepo loginRepo;
	@Autowired
	private RegistrationRepo regRepo;
	@Autowired
	private MLevelRepo levelRepo;
	@Autowired
	private SectorRepo sectorRepo;
	@Autowired
	private DepartmentRepo deptRepo;
	
	@GetMapping("approveUser")
	public String approveUser(@RequestParam("username")String username)
	{
		
		Login log = loginRepo.findByEmailIdAndIsApproved(username, 0);
		String status = "";
		if(log == null )
		{
			status = "fail";
		}
		else
		{
			log.setIsApproved(1);
			loginRepo.save(log);
			status = "success";
		}
		
		return status;
	}
	@GetMapping("rejectUser")
	@ResponseBody
    @CrossOrigin
	public String rejectUser(@RequestParam("username")String username)
	{
		
		Login log = loginRepo.findByEmailIdAndIsApproved(username, 0);
		String status = "";
		if(log == null )
		{
			status = "fail";
		}
		else
		{
			log.setIsApproved(2);
			loginRepo.save(log);
			status = "success";
		}
		return status;
	}
	@GetMapping("undoUser")
	@ResponseBody
    @CrossOrigin
	public String undoUser(@RequestParam("username")String username)
	{
		
		Login log = loginRepo.findByEmailIdAndIsApproved(username, 2);
		String status = "";
		if(log == null )
		{
			status = "fail";
		}
		else
		{
			log.setIsApproved(0);
			loginRepo.save(log);
			status = "success";
		}
		return status;
	}
	@GetMapping("getPendingUser")
	@ResponseBody
    @CrossOrigin
	public String getPendingUser(@RequestParam("username") String username) throws Exception
	{
		Login l = loginRepo.findByEmailId(username);
		JSONArray list = new JSONArray();
		if(l.getUserType().equals("superAdmin"))
		{
			List<Login> log = loginRepo.findByIsApproved(0);
			
			for(Login login : log)
			{
				Registration reg = regRepo.findByEmail(login.getEmailId());
				Sector sector = sectorRepo.findBySectorId(reg.getSectorCode());	
				String ministryName = levelRepo.getMinistryName(reg.getMinistryCode(), 22);
				String deptName = levelRepo.getDepartmentName(reg.getDepartmentCode(), 23);
				
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("name", reg.getName());
				jsonobj.put("username", reg.getEmail());
				jsonobj.put("sectorName", sector.getSector_Name_e());
				jsonobj.put("ministryName", ministryName);
				jsonobj.put("departmentName", deptName);
				jsonobj.put("createdDate", login.getCreatedDate());
				jsonobj.put("rejectedDate", "");
				list.put(jsonobj);
				
			}
			
		}
		

		return list.toString();
	}
	@GetMapping("getRejectedUser")
	@ResponseBody
    @CrossOrigin
	public String getRejectedUser(@RequestParam("username") String username) throws Exception
	{
		Login l = loginRepo.findByEmailId(username);
		JSONArray list = new JSONArray();
		if(l.getUserType().equals("superAdmin"))
		{
			List<Login> log = loginRepo.findByIsApproved(2);
			
			for(Login login : log)
			{
				Registration reg = regRepo.findByEmail(login.getEmailId());
				Sector sector = sectorRepo.findBySectorId(reg.getSectorCode());	
				String ministryName = levelRepo.getMinistryName(reg.getMinistryCode(), 22);
				String deptName = levelRepo.getDepartmentName(reg.getDepartmentCode(), 23);
				
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("name", reg.getName());
				jsonobj.put("username", reg.getEmail());
				jsonobj.put("sectorName", sector.getSector_Name_e());
				jsonobj.put("ministryName", ministryName);
				jsonobj.put("departmentName", deptName);
				jsonobj.put("createdDate", login.getCreatedDate());
				jsonobj.put("rejectedDate", "");
				list.put(jsonobj);
				
			}
			
		}
		

		return list.toString();
	}
}
