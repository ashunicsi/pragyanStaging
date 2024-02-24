package com.nicsi.ceda.controller;

import java.sql.Date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nicsi.ceda.config.BatchConfiguration;
import com.nicsi.ceda.model.DataCleanRequest;
import com.nicsi.ceda.model.DataPushRequest;
import com.nicsi.ceda.model.ListOfUpdatedScheme;
import com.nicsi.ceda.model.SchemeDetailsDTO;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.repository.DataCleanRequestRepo;
import com.nicsi.ceda.repository.DataPushRequestRepo;
import com.nicsi.ceda.repository.ListOfUpdatedSchemeRepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.services.IDataService;
import com.nicsi.ceda.util.GlobalVariable;


@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class DataCleanRequestController 
{
	@Autowired
	Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private DataCleanRequestRepo cleanRepo;
	@Autowired
	private DataPushRequestRepo pushRepo;
	
	
	@Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Autowired
	private IDataService dataService;
    @Autowired
    private BatchConfiguration batch;
    @Autowired
	private ListOfUpdatedSchemeRepo updatedSchemeRepo;
    
    
	
    //user code
    @GetMapping("countUserDataCleanRequest")
	public String countUserDataCleanRequest(String username)
	{
		int count = cleanRepo.countDataCleanRequest(username);
		String status = Integer.toString(count);
		
		return status;
	}
	@GetMapping("countUserRejectedCleanRequest")
	public String countUserRejectedCleanRequest(String username)	
	{
		int count = cleanRepo.countRejectedCleanRequest(username);
		String status = Integer.toString(count);
		
		return status;
	}
	@GetMapping("countUserAcceptedCleanRequest")
	public String countUserAcceptedCleanRequest(String username)	
	{
		int count = cleanRepo.countAcceptedCleanRequest(username);
		String status = Integer.toString(count);
		
		return status;
	}
	
    @GetMapping("getUserDataCleanRequest1")
    public String getUserDataCleanRequest1(@RequestParam("username") String username)
    {
    	List<DataCleanRequest> data = cleanRepo.findByUsername(username);
    	data.sort((DataCleanRequest d1, DataCleanRequest d2)->d2.getId()-d1.getId()); 
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataCleanRequest s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("id", s.getId());
			 jsonobj.put("sn", i++);
			 if(s.getActionTime() != null)
			 {
				 jsonobj.put("actionTime", f.format(s.getRequestTime()));
			 }
			 else
			 {
				 jsonobj.put("actionTime", "");
			 }
			 
			 jsonobj.put("projectCode", s.getProjectCode());
			 jsonobj.put("requestTime",f.format(s.getRequestTime()));
			 if(s.getStatus() == 0)
			 {
				 jsonobj.put("status", "In process...");
			 }
			 else if(s.getStatus() == 1)
			 {
				 jsonobj.put("status", "Approved");
			 }
			 else
			 {
				 jsonobj.put("status", "Rejected");
			 }
			 jsonobj.put("username",s.getUsername());
			 jsonobj.put("actionTakenBy",s.getActionTakenBy());
			 jsonobj.put("isRejected",s.getIsRejected());
			 
			 if(s.getRejectedMessage() == null && s.getIsRejected() == 0 && s.getStatus() == 0)
			 {
				 jsonobj.put("rejectedMessage","Request Sumitted Successfully");
			 }
			 else if(s.getRejectedMessage() == null && s.getStatus() == 1)
			 {
				 jsonobj.put("rejectedMessage","Request Approved Successfully");
			 }
			 else
			 {
				 jsonobj.put("rejectedMessage",s.getRejectedMessage());
			 }
			 
			
			 levelList.put(jsonobj);
		}
		return levelList.toString();
    }
    
	@GetMapping("sendDataCleanRequest")
	public String cleanDataRequest(@RequestParam("username") String username, @RequestParam("projectCode") int projectCode)
	{
		//get active scheme information
		Tbl_Project_Detail_Intrim proj = projRepo.findByUsernameAndProjectCodeAndStatus(username, projectCode, 1);
		String status = "";
		if(proj == null)
		{
			status = "Invalid Access";
		}
		else
		{
			DataCleanRequest clean = cleanRepo.findByUsernameAndProjectCodeAndStatus(username, projectCode, 0);
			if(clean != null)
			{
				status = "Clean Request Already in Process...";
			}
			else
			{
				java.util.Date newdate = new java.util.Date();
				Date timeStamp = new Date(newdate.getTime());
				//create clean request in DataCleanRequest table
				DataCleanRequest data = new DataCleanRequest();
				data.setProjectCode(projectCode);
				data.setRequestTime(new Timestamp(timeStamp.getTime()));
				data.setStatus(0);
				data.setIsRejected(0);
				data.setUsername(username);
				cleanRepo.save(data);
				status = "Clean Request Submitted Successfully";
			}
		}
		return status;
	}
	@GetMapping("getActivatedScheme")
	@ResponseBody
    @CrossOrigin
	public String getActivatedScheme(@RequestParam("username") String username, ModelMap map, HttpServletRequest request, HttpSession sessions)
	{
		//System.out.println("viewActivatedScheme calling");
		//map.addAttribute("data",dataService.findActivatedScheme(map, request, sessions));
		List<SchemeDetailsDTO> data =dataService.findByUsernameAndStatus(username, 1, map, request, sessions);
		JSONArray levelList = new JSONArray();
		int sn = 1;
		for(SchemeDetailsDTO s : data)
		{
			 
			  
			 JSONObject jsonobj = new JSONObject();
			
			 jsonobj.put("schemeName", s.getSchemeName());
			 jsonobj.put("schemeCode",s.getSchemeCode());
			
			 
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	//Admin Action Code

	@GetMapping("countAllDataCleanRequest")
	public String countAllDataCleanRequest()
	{
		int count = cleanRepo.countDataCleanRequest();
		String status = Integer.toString(count);
		
		return status;
	}
	@GetMapping("countAllRejectedCleanRequest")
	public String countAllRejectedCleanRequest()	
	{
		int count = cleanRepo.countRejectedCleanRequest();
		String status = Integer.toString(count);
		
		return status;
	}
	@GetMapping("countAllAcceptedCleanRequest")
	public String countAllAcceptedCleanRequest()	
	{
		int count = cleanRepo.countAcceptedCleanRequest();
		String status = Integer.toString(count);
		
		return status;
	}
	
	
	@GetMapping("getAllDataCleanRequest")
    public String getAllDataCleanRequest()
    {
    	List<DataCleanRequest> data = cleanRepo.findByStatusAndIsRejected(0, 0);
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataCleanRequest s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", i++);
			 if(s.getActionTime() != null)
			 {
				 jsonobj.put("actionTime", f.format(s.getRequestTime()));
			 }
			 else
			 {
				 jsonobj.put("actionTime", "");
			 }
			 
			 jsonobj.put("projectCode", s.getProjectCode());
			 jsonobj.put("requestTime",f.format(s.getRequestTime()));
			 if(s.getStatus() == 0)
			 {
				 jsonobj.put("status", "New Request");
			 }
			 else if(s.getStatus() == 1)
			 {
				 jsonobj.put("status", "Approved");
			 }
			 else
			 {
				 jsonobj.put("status", "Rejected");
			 }
			 jsonobj.put("username",s.getUsername());
			 jsonobj.put("actionTakenBy",s.getActionTakenBy());
			 jsonobj.put("isRejected",s.getIsRejected());
			 
			 if(s.getRejectedMessage() == null && s.getIsRejected() == 0 && s.getStatus() == 0)
			 {
				 jsonobj.put("rejectedMessage","Request Sumitted Successfully");
			 }
			 else if(s.getRejectedMessage() == null && s.getStatus() == 1)
			 {
				 jsonobj.put("rejectedMessage","Request Approved Successfully");
			 }
			 else
			 {
				 jsonobj.put("rejectedMessage",s.getRejectedMessage());
			 }
			 
			
			 levelList.put(jsonobj);
		}
		return levelList.toString();
    }
	@GetMapping("getAllRejectedCleanRequest")
    public String getAllRejectedCleanRequest()
    {
    	List<DataCleanRequest> data = cleanRepo.findByStatusAndIsRejected(2, 1);
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataCleanRequest s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", i++);
			 if(s.getActionTime() != null)
			 {
				 jsonobj.put("actionTime", f.format(s.getRequestTime()));
			 }
			 else
			 {
				 jsonobj.put("actionTime", "");
			 }
			 
			 jsonobj.put("projectCode", s.getProjectCode());
			 jsonobj.put("requestTime",f.format(s.getRequestTime()));
			 if(s.getStatus() == 0)
			 {
				 jsonobj.put("status", "New Request");
			 }
			 else if(s.getStatus() == 1)
			 {
				 jsonobj.put("status", "Approved");
			 }
			 else
			 {
				 jsonobj.put("status", "Rejected");
			 }
			 jsonobj.put("username",s.getUsername());
			 jsonobj.put("actionTakenBy",s.getActionTakenBy());
			 jsonobj.put("isRejected",s.getIsRejected());
			 
			 if(s.getRejectedMessage() == null && s.getIsRejected() == 0 && s.getStatus() == 0)
			 {
				 jsonobj.put("rejectedMessage","Request Sumitted Successfully");
			 }
			 else if(s.getRejectedMessage() == null && s.getStatus() == 1)
			 {
				 jsonobj.put("rejectedMessage","Request Approved Successfully");
			 }
			 else
			 {
				 jsonobj.put("rejectedMessage",s.getRejectedMessage());
			 }
			 
			
			 levelList.put(jsonobj);
		}
		return levelList.toString();
    }
	
	@GetMapping("getAllAcceptedCleanRequest")
    public String getAcceptedCleanRequest()
    {
    	List<DataCleanRequest> data = cleanRepo.findByStatusAndIsRejected(1, 0);
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataCleanRequest s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", i++);
			 if(s.getActionTime() != null)
			 {
				 jsonobj.put("actionTime", f.format(s.getRequestTime()));
			 }
			 else
			 {
				 jsonobj.put("actionTime", "");
			 }
			 
			 jsonobj.put("projectCode", s.getProjectCode());
			 jsonobj.put("requestTime",f.format(s.getRequestTime()));
			 if(s.getStatus() == 0)
			 {
				 jsonobj.put("status", "New Request");
			 }
			 else if(s.getStatus() == 1)
			 {
				 jsonobj.put("status", "Approved");
			 }
			 else
			 {
				 jsonobj.put("status", "Rejected");
			 }
			 jsonobj.put("username",s.getUsername());
			 jsonobj.put("actionTakenBy",s.getActionTakenBy());
			 jsonobj.put("isRejected",s.getIsRejected());
			 
			 if(s.getRejectedMessage() == null && s.getIsRejected() == 0 && s.getStatus() == 0)
			 {
				 jsonobj.put("rejectedMessage","Request Sumitted Successfully");
			 }
			 else if(s.getRejectedMessage() == null && s.getStatus() == 1)
			 {
				 jsonobj.put("rejectedMessage","Request Approved Successfully");
			 }
			 else
			 {
				 jsonobj.put("rejectedMessage",s.getRejectedMessage());
			 }
			 
			
			 levelList.put(jsonobj);
		}
		return levelList.toString();
    }
	@GetMapping("rejectDataCleanRequest")
	public String rejectDataCleanRequest(@RequestParam("username") String username, @RequestParam("schemeCode") int projectCode, @RequestParam("rejectMessage") String rejectMessage)
	{
		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		DataCleanRequest data = cleanRepo.findByProjectCodeAndIsRejectedAndStatus(projectCode, 0, 0);
		data.setStatus(2);
		data.setActionTakenBy(username);
		data.setIsRejected(1);
		data.setActionTime(new Timestamp(timeStamp.getTime()));
		data.setRejectedMessage(rejectMessage);
		
		
		cleanRepo.save(data);
		
		
		return "Data Clean Request Rejected Successfully";
	}
	@GetMapping("approveDataCleanRequest")
	public String approveDataCleanRequest(@RequestParam("username") String username, @RequestParam("schemeCode") int projectCode)
	{
		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		DataCleanRequest data = cleanRepo.findByProjectCodeAndIsRejectedAndStatus(projectCode, 0, 0);
		data.setStatus(1);
		data.setActionTakenBy(username);
		data.setIsRejected(0);
		data.setActionTime(new Timestamp(timeStamp.getTime()));
		data.setRejectedMessage("");
		cleanRepo.save(data);
		
		ListOfUpdatedScheme d = new ListOfUpdatedScheme();
		data.setProjectCode(projectCode);
		d.setReasion("Requested By user");
		d.setIsDataLevelChanged(1);
		d.setUpdatedDate(new Timestamp(timeStamp.getTime()));
		d.setStatus(0);
		updatedSchemeRepo.save(d);
		
		return "Data Clean Request Approved Successfully";
	}
	@GetMapping("undoDataCleanRequest")
	public String undoDataCleanRequest(@RequestParam("username") String username, @RequestParam("schemeCode") int projectCode)
	{
		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		DataCleanRequest data = cleanRepo.findByProjectCodeAndIsRejectedAndStatus(projectCode, 1, 2);
		data.setStatus(0);
		data.setActionTakenBy(username);
		data.setIsRejected(0);
		data.setActionTime(new Timestamp(timeStamp.getTime()));
		data.setRejectedMessage("");
		cleanRepo.save(data);
		
		
		return "Data Clean Request Undo Successfully";
	}
	
	
	
	@GetMapping("getUserDataCleanRequest")
    public String getUserDataCleanRequest(@RequestParam("username") String username)
    {
    	List<DataCleanRequest> data = cleanRepo.findByStatusAndIsRejectedAndUsername(0, 0, username);
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataCleanRequest s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", i++);
			 if(s.getActionTime() != null)
			 {
				 jsonobj.put("actionTime", f.format(s.getRequestTime()));
			 }
			 else
			 {
				 jsonobj.put("actionTime", "");
			 }
			 
			 jsonobj.put("projectCode", s.getProjectCode());
			 jsonobj.put("requestTime",f.format(s.getRequestTime()));
			 if(s.getStatus() == 0)
			 {
				 jsonobj.put("status", "New Request");
			 }
			 else if(s.getStatus() == 1)
			 {
				 jsonobj.put("status", "Approved");
			 }
			 else
			 {
				 jsonobj.put("status", "Rejected");
			 }
			 jsonobj.put("username",s.getUsername());
			 jsonobj.put("actionTakenBy",s.getActionTakenBy());
			 jsonobj.put("isRejected",s.getIsRejected());
			 
			 if(s.getRejectedMessage() == null && s.getIsRejected() == 0 && s.getStatus() == 0)
			 {
				 jsonobj.put("rejectedMessage","Request Sumitted Successfully");
			 }
			 else if(s.getRejectedMessage() == null && s.getStatus() == 1)
			 {
				 jsonobj.put("rejectedMessage","Request Approved Successfully");
			 }
			 else
			 {
				 jsonobj.put("rejectedMessage",s.getRejectedMessage());
			 }
			 
			
			 levelList.put(jsonobj);
		}
		return levelList.toString();
    }
	@GetMapping("getUserRejectedCleanRequest")
    public String getUserRejectedCleanRequest(@RequestParam("username") String username)
    {
    	List<DataCleanRequest> data = cleanRepo.findByStatusAndIsRejectedAndUsername(2, 1, username);
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataCleanRequest s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", i++);
			 if(s.getActionTime() != null)
			 {
				 jsonobj.put("actionTime", f.format(s.getRequestTime()));
			 }
			 else
			 {
				 jsonobj.put("actionTime", "");
			 }
			 
			 jsonobj.put("projectCode", s.getProjectCode());
			 jsonobj.put("requestTime",f.format(s.getRequestTime()));
			 if(s.getStatus() == 0)
			 {
				 jsonobj.put("status", "New Request");
			 }
			 else if(s.getStatus() == 1)
			 {
				 jsonobj.put("status", "Approved");
			 }
			 else
			 {
				 jsonobj.put("status", "Rejected");
			 }
			 jsonobj.put("username",s.getUsername());
			 jsonobj.put("actionTakenBy",s.getActionTakenBy());
			 jsonobj.put("isRejected",s.getIsRejected());
			 
			 if(s.getRejectedMessage() == null && s.getIsRejected() == 0 && s.getStatus() == 0)
			 {
				 jsonobj.put("rejectedMessage","Request Sumitted Successfully");
			 }
			 else if(s.getRejectedMessage() == null && s.getStatus() == 1)
			 {
				 jsonobj.put("rejectedMessage","Request Approved Successfully");
			 }
			 else
			 {
				 jsonobj.put("rejectedMessage",s.getRejectedMessage());
			 }
			 
			
			 levelList.put(jsonobj);
		}
		return levelList.toString();
    }
	@GetMapping("getUserAcceptedCleanRequest")
    public String getUserAcceptedCleanRequest(@RequestParam("username") String username)
    {
    	List<DataCleanRequest> data = cleanRepo.findByStatusAndIsRejectedAndUsername(1, 0, username);
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataCleanRequest s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", i++);
			 if(s.getActionTime() != null)
			 {
				 jsonobj.put("actionTime", f.format(s.getRequestTime()));
			 }
			 else
			 {
				 jsonobj.put("actionTime", "");
			 }
			 
			 jsonobj.put("projectCode", s.getProjectCode());
			 jsonobj.put("requestTime",f.format(s.getRequestTime()));
			 if(s.getStatus() == 0)
			 {
				 jsonobj.put("status", "New Request");
			 }
			 else if(s.getStatus() == 1)
			 {
				 jsonobj.put("status", "Approved");
			 }
			 else
			 {
				 jsonobj.put("status", "Rejected");
			 }
			 jsonobj.put("username",s.getUsername());
			 jsonobj.put("actionTakenBy",s.getActionTakenBy());
			 jsonobj.put("isRejected",s.getIsRejected());
			 
			 if(s.getRejectedMessage() == null && s.getIsRejected() == 0 && s.getStatus() == 0)
			 {
				 jsonobj.put("rejectedMessage","Request Sumitted Successfully");
			 }
			 else if(s.getRejectedMessage() == null && s.getStatus() == 1)
			 {
				 jsonobj.put("rejectedMessage","Request Approved Successfully");
			 }
			 else
			 {
				 jsonobj.put("rejectedMessage",s.getRejectedMessage());
			 }
			 
			
			 levelList.put(jsonobj);
		}
		return levelList.toString();
    }
	
	@GetMapping("undoUserDataCleanRequest")
	public String undoUserDataCleanRequest(@RequestParam("username") String username, @RequestParam("schemeCode") int projectCode)
	{
		
		DataCleanRequest data = cleanRepo.findByProjectCodeAndIsRejectedAndStatusAndUsername(projectCode, 0, 0, username);
		cleanRepo.deleteById(data.getId());
		
		
		
		return "Data Clean Request Undo Successfully";
	}
	
	
	
	}
