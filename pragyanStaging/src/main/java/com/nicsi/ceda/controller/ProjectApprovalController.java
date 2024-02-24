package com.nicsi.ceda.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nicsi.ceda.model.SchemeDetailsDTO;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.services.IDataService;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class ProjectApprovalController 
{
	@Autowired
	private IDataService dataService;
	
	@Autowired
	private Tbl_Project_Detail_IntrimRepo projRepo;
	
	@GetMapping("getSubmittedScheme")
	@ResponseBody
    @CrossOrigin
	public String getSubmittedScheme(ModelMap map, HttpServletRequest request, HttpSession session)
	{
		List<SchemeDetailsDTO> data =dataService.findByProject_Approval_StatusAndIsRejected(0, 0, map, request, session);
		JSONArray levelList = new JSONArray();
		int i = 1;
		for(SchemeDetailsDTO s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", i++);
			 jsonobj.put("id", s.getId());
			 jsonobj.put("ministryCode", s.getMinistryCode());
			 jsonobj.put("ministryName",s.getMinistryName());
			 jsonobj.put("departmentCode", s.getDepartmentCode());
			 jsonobj.put("departmentName",s.getDepartmentName());
			 jsonobj.put("schemeName", s.getSchemeName());
			 jsonobj.put("schemeCode",s.getSchemeCode());
			 jsonobj.put("cordEmail",s.getCordEmail());
			 jsonobj.put("headEmail",s.getHeadEmail());
			 jsonobj.put("adminEmail",s.getAdminEmail());
			 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			 String date = f.format(s.getEntryDate());
			 date = StringUtils.abbreviate(date, 18);
			 jsonobj.put("submittedDate", date);
			 
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	
	@GetMapping("getRejectedScheme")
	@ResponseBody
    @CrossOrigin
	public String getRejectedScheme(ModelMap map, HttpServletRequest request, HttpSession sessions)
	{
		List<SchemeDetailsDTO> data =dataService.findByIsRejected(1);		
		JSONArray levelList = new JSONArray();
		int i = 1;
		for(SchemeDetailsDTO s : data)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", i++);
			 jsonobj.put("id", s.getId());
			 jsonobj.put("ministryCode", s.getMinistryCode());
			 jsonobj.put("ministryName",s.getMinistryName());
			 jsonobj.put("departmentCode", s.getDepartmentCode());
			 jsonobj.put("departmentName",s.getDepartmentName());
			 jsonobj.put("schemeName", s.getSchemeName());
			 jsonobj.put("schemeCode",s.getSchemeCode());
			 jsonobj.put("cordEmail",s.getCordEmail());
			 jsonobj.put("headEmail",s.getHeadEmail());
			 jsonobj.put("adminEmail",s.getAdminEmail());
			 jsonobj.put("rejectMessage",s.getRejectMessage());
			 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			 String date = f.format(s.getProjectRejectionDate());
			 date = StringUtils.abbreviate(date, 18);
			 jsonobj.put("rejectDate",date);
			 
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("approveScheme")
	@ResponseBody
    @CrossOrigin
	public String approveProject(@RequestParam("username") String username, @RequestParam("schemeCode") int projectCode,  ModelMap map, HttpServletRequest request, HttpSession sessions)
	{
		Tbl_Project_Detail_Intrim proj =projRepo.findByProjectCode(projectCode);		
		proj.setStatus(1);
		proj.setIsRejected(0);
		proj.setProject_Approval_Status(1);

		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		proj.setProject_Approved_Date(new Timestamp(timeStamp.getTime()));
		proj.setLastUpdateDate(new Timestamp(timeStamp.getTime()));
		projRepo.save(proj);
		return "Project Appoved Successfully";
	}
	@GetMapping("rejectScheme")
	@ResponseBody
    @CrossOrigin
	public String rejectProject(@RequestParam("username") String username, @RequestParam("schemeCode") int projectCode, @RequestParam("rejectMessage") String rejectMessage,   ModelMap map, HttpServletRequest request, HttpSession sessions)
	{
		
		Tbl_Project_Detail_Intrim proj =projRepo.findByProjectCode(projectCode);		
		proj.setStatus(0);
		proj.setIsRejected(1);
		proj.setProject_Approval_Status(0);

		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		proj.setProjectRejectionDate(new Timestamp(timeStamp.getTime()));
		proj.setRejectMessage(rejectMessage);
		proj.setLastUpdateDate(new Timestamp(timeStamp.getTime()));
		projRepo.save(proj);
		return "Project Rejected Successfully";
	}
	@GetMapping("undoRejectedScheme")
	@ResponseBody
    @CrossOrigin
	public String undoRejectedScheme(@RequestParam("username") String username, @RequestParam("schemeCode") int projectCode,   ModelMap map, HttpServletRequest request, HttpSession sessions)
	{
		Tbl_Project_Detail_Intrim proj =projRepo.findByProjectCode(projectCode);		
		proj.setStatus(0);
		proj.setIsRejected(0);
		proj.setProject_Approval_Status(0);
		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		proj.setRejectMessage("");
		proj.setLastUpdateDate(new Timestamp(timeStamp.getTime()));
		projRepo.save(proj);
		return "Project Undo Successfully";
	}
}
