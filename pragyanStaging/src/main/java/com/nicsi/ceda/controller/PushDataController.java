package com.nicsi.ceda.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nicsi.ceda.model.DataCleanRequest;
import com.nicsi.ceda.model.DataPushRequest;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.repository.DataCleanRequestRepo;
import com.nicsi.ceda.repository.DataPushRequestRepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.services.IDataService;
import com.nicsi.ceda.util.GlobalVariable;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class PushDataController 
{
	@Autowired
	private DataPushRequestRepo pushRepo;
	@Autowired
	Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private DataCleanRequestRepo cleanRepo;
	
	
	
	@Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Autowired
	private IDataService dataService;
	

	@GetMapping("deleteRequest")
	public String deleteRequest(@RequestParam("username") String username, @RequestParam("id") int id, @RequestParam("projectCode") int projectCode)
	{
		//get active scheme information
		Tbl_Project_Detail_Intrim proj = projRepo.findByUsernameAndProjectCode(username, projectCode);
		String status = "";
		if(proj == null)
		{
			status = "Invalid Access";
		}
		else
		{
			cleanRepo.deleteDataCleanRequest(username, id);
			status = "Data Clean Request Deleted Successfully";
		}
		return status;
	}
	
	
	
	@GetMapping("sendDataPushRequest")
	public String sendDataPushRequest(@RequestParam("username") String username, @RequestParam("projectCode") int projectCode)
	{
		List<Tbl_Project_Detail_Intrim> proj = projRepo.findByUsername(username);
		DataPushRequest data = new DataPushRequest();
		String status = "";
		if(proj == null)
		{
			status = "Invalid Access";
		}
		else
		{
			
			java.util.Date newdate = new java.util.Date();
			Date date = new Date(newdate.getTime());
			List<DataPushRequest> d = pushRepo.findByUsernameAndProjectCodeAndPushedDate(username, projectCode, date);
			
			if(d.size() < 2)
			{
				data.setPushedDate(date);
				Date timeStamp = new Date(newdate.getTime());
				data.setIsRejected(0);
				data.setNoOfAttempts(0);
				data.setProjectCode(projectCode);
				data.setRequestTime(new Timestamp(timeStamp.getTime()));
				data.setStatus(0);
				data.setUsername(username);
				pushRepo.save(data);
				status = "Data Push Request Submitted Successfully";
			}
			else
			{
				status = "You can raise data push request twice a day only...";
			}
			
		}
		

		return status;
	}
	
	@GetMapping("getDataPushedRequest")
    public String getDataPushedRequest(@RequestParam("username") String username)
    {
    	List<DataPushRequest> data = pushRepo.findByUsername(username);
    	data.sort((DataPushRequest d1, DataPushRequest d2)->d2.getId()-d1.getId()); 
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataPushRequest s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", i++);
			 jsonobj.put("pid", s.getId());
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
	
	@GetMapping("countUserDataPushRequest")
	public String countUserDataPushRequest(String username)
	{
		int count = pushRepo.countDataPushRequest(username);
		String status = Integer.toString(count);
		
		return status;
	}
	@GetMapping("countUserRejectedPushRequest")
	public String countUserRejectedPushRequest(String username)	
	{
		int count = pushRepo.countRejectedPushRequest(username);
		String status = Integer.toString(count);
		
		return status;
	}
	@GetMapping("countUserAcceptedPushRequest")
	public String countUserAcceptedPushRequest(String username)	
	{
		int count = pushRepo.countAcceptedPushRequest(username);
		String status = Integer.toString(count);
		
		return status;
	}
	
	@GetMapping("countAllDataPushRequest")
	public String countAllDataPushRequest()
	{
		int count = pushRepo.countDataPushRequest();
		String status = Integer.toString(count);
		
		return status;
	}
	@GetMapping("countAllRejectedPushRequest")
	public String countAllRejectedPushRequest()	
	{
		int count = pushRepo.countRejectedPushRequest();
		String status = Integer.toString(count);
		
		return status;
	}
	@GetMapping("countAllAcceptedPushRequest")
	public String countAllAcceptedPushRequest()	
	{
		int count = pushRepo.countAcceptedPushRequest();
		String status = Integer.toString(count);
		
		return status;
	}
	
	@GetMapping("getAllDataPushRequest")
    public String getAllDataPushedRequest()
    {
    	List<DataPushRequest> data = pushRepo.findByStatus(0);
    	data.sort((DataPushRequest d1, DataPushRequest d2)->d2.getId()-d1.getId()); 
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataPushRequest s : data)
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
	@GetMapping("getAllRejectedPushRequest")
    public String getAllRejectedPushRequest()
    {
    	List<DataPushRequest> data = pushRepo.findByStatus(2);
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataPushRequest s : data)
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
	
	@GetMapping("getAllAcceptedPushRequest")
    public String getAllAcceptedPushRequest()
    {
    	List<DataPushRequest> data = pushRepo.findByStatus(1);
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataPushRequest s : data)
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
	
	@GetMapping("rejectDataPushRequest")
	public String rejectDataPushRequest(@RequestParam("username") String username, @RequestParam("schemeCode") int projectCode, @RequestParam("rejectMessage") String rejectMessage)
	{
		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		DataPushRequest data = pushRepo.findByProjectCodeAndStatus(projectCode, 0);
		data.setStatus(0);
		data.setActionTakenBy(username);
		data.setIsRejected(2);
		data.setActionTime(new Timestamp(timeStamp.getTime()));
		data.setRejectedMessage(rejectMessage);
		
		
		pushRepo.save(data);
		
		
		return "Data Push Request Rejected Successfully";
	}
	@GetMapping("approveDataPushRequest")
	public String approveDataPushRequest(@RequestParam("username") String username, @RequestParam("schemeCode") int projectCode)
	{
		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		DataPushRequest data = pushRepo.findByProjectCodeAndStatus(projectCode, 0);
		data.setStatus(1);
		data.setActionTakenBy(username);
		data.setIsRejected(0);
		data.setActionTime(new Timestamp(timeStamp.getTime()));
		data.setRejectedMessage("");
		pushRepo.save(data);
		
		
		return "Data Push Request Approved Successfully";
	}
	@GetMapping("undoDataPushRequest")
	public String undoDataPushRequest(@RequestParam("username") String username, @RequestParam("schemeCode") int projectCode)
	{
		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		DataPushRequest data = pushRepo.findByProjectCodeAndStatus(projectCode, 2);
		data.setStatus(0);
		data.setActionTakenBy(username);
		data.setIsRejected(0);
		data.setActionTime(new Timestamp(timeStamp.getTime()));
		data.setRejectedMessage("");
		pushRepo.save(data);
		
		
		return "Data Push Request Undo Successfully";
	}
	
	@GetMapping("getUserDataPushRequest")
    public String getUserDataPushRequest(@RequestParam("username") String username)
    {
    	List<DataPushRequest> data = pushRepo.findByStatusAndUsername(0, username);
    	data.sort((DataPushRequest d1, DataPushRequest d2)->d2.getId()-d1.getId()); 
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataPushRequest s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", i++);
			 jsonobj.put("id", s.getId());
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
	
	
	@GetMapping("getUserAcceptedPushRequest")
    public String getUserAcceptedPushRequest(@RequestParam("username") String username)
    {
    	List<DataPushRequest> data = pushRepo.findByStatusAndUsername(0, username);
    	data.sort((DataPushRequest d1, DataPushRequest d2)->d2.getId()-d1.getId()); 
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataPushRequest s : data)
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
	@GetMapping("getUserRejectedPushRequest")
    public String getUserRejectedPushRequest(@RequestParam("username") String username)
    {
    	List<DataPushRequest> data = pushRepo.findByStatusAndUsername(2, username);
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(DataPushRequest s : data)
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
	@GetMapping("undoUserDataPushRequest")
	public String undoUserDataPushRequest(@RequestParam("username") String username, @RequestParam("projectCode") int projectCode, @RequestParam("id") int id)
	{
		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		//DataPushRequest data = pushRepo.findByProjectCodeAndStatus(projectCode, 0);
		DataPushRequest data = pushRepo.findById(id);
		
		data.setStatus(3);
		data.setActionTakenBy(username);
		data.setIsRejected(0);
		data.setActionTime(new Timestamp(timeStamp.getTime()));
		data.setRejectedMessage("");
		pushRepo.save(data);
		
		
		return "Data Push Request Undo Successfully";
	}
	@GetMapping("countDataPushed")
	public String countDataPushed()
	{
		int count = pushRepo.countDataPushed();
		String status = Integer.toString(count);
		
		return status;
	}
	//isPushCompleted
	@GetMapping("getCompeletedPushData")
	public String getCompeletedPushData(@RequestParam("username") String username)
	{
		
		List<DataPushRequest> data = pushRepo.findByIsPushCompleted("1");
		JSONArray levelList = new JSONArray();
		int i = 1;
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		for(DataPushRequest s : data)
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
			jsonobj.put("status", "Completed");
			
			 jsonobj.put("username",s.getUsername());
			 jsonobj.put("actionTakenBy",s.getActionTakenBy());
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
