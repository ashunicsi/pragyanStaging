package com.nicsi.ceda.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicsi.ceda.model.CleanDataRequestDTO;
import com.nicsi.ceda.model.DataCleanRequest;
import com.nicsi.ceda.model.ListOfUpdatedKPI;
import com.nicsi.ceda.model.ListOfUpdatedScheme;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.repository.DataCleanRequestRepo;
import com.nicsi.ceda.repository.ListOfUpdatedKPIRepo;
import com.nicsi.ceda.repository.ListOfUpdatedSchemeRepo;
import com.nicsi.ceda.repository.Tbl_DataPort_KPI_PORTED_DATADeletedRepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.util.GlobalVariable;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class DataCleanController 
{
	@Autowired
	Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private ListOfUpdatedSchemeRepo updatedSchemeRepo;
	@Autowired
	private ListOfUpdatedKPIRepo updatedKpiRepo;
	@Autowired
	private DataCleanRequestRepo cleanRepo;
	
	@Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Autowired
    @Lazy
    private Tbl_DataPort_KPI_PORTED_DATADeletedRepo deletedRepo;
    @Value("${dataCleanApiLinkByProjectCode}")
    private String dataCleanApiLinkByProjectCode;
    @Value("${dataCleanApiLinkByProjectCodeAndKpiId}")
    private String dataCleanApiLinkByProjectCodeAndKpiId;
    
    @GetMapping("countDataCleanRequest")
	public String countDataCleanRequest()
	{
		int count = updatedSchemeRepo.countCleanRequest(0);
		String status = Integer.toString(count);
		
		return status;
	}
    @GetMapping("countDataCleanedRequest")
	public String countDataCleanedRequest()
	{
		int count = updatedSchemeRepo.countCleanRequest(1);
		String status = Integer.toString(count);
		
		return status;
	}
	@GetMapping("countKpiNameUpdateRequest")
	public String countKpiNameUpdateRequest()	
	{
		int count = updatedKpiRepo.countKpiNameRequest(0);
		String status = Integer.toString(count);
		
		return status;
	}
	@GetMapping("countKpiNameUpdatedRequest")
	public String countKpiNameUpdatedRequest()	
	{
		int count = updatedKpiRepo.countKpiNameRequest(1);
		String status = Integer.toString(count);
		
		return status;
	}
	@GetMapping("getAllSchemeCleanRequest")
	public String getAllSchemeCleanRequest()
	{
		List<ListOfUpdatedScheme> data = updatedSchemeRepo.findByStatus(0);
		data.sort(Comparator.comparingInt(ListOfUpdatedScheme::getId));
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(ListOfUpdatedScheme s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			
			 jsonobj.put("requestTime", f.format(s.getUpdatedDate()));
			 
			 jsonobj.put("projectCode", s.getProjectCode());
			 if(s.getStatus() == 0)
			 {
				 jsonobj.put("status", "New Request");
			 }
			 else if(s.getStatus() == 1)
			 {
				 jsonobj.put("status", "Approved");
			 }
			 
			 jsonobj.put("reasion",s.getReasion());
			 
			 
			
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("getAllSchemeCleanedRequest")
	public  String  getAllSchemeCleanedRequest()
	{
		List<ListOfUpdatedScheme> data = updatedSchemeRepo.findByStatus(1);
		data.sort(Comparator.comparingInt(ListOfUpdatedScheme::getId));
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(ListOfUpdatedScheme s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			
			 jsonobj.put("requestTime", f.format(s.getUpdatedDate()));
			 jsonobj.put("responseTime", f.format(s.getActionDate()));
			 jsonobj.put("projectCode", s.getProjectCode());
			 if(s.getStatus() == 0)
			 {
				 jsonobj.put("status", "New Request");
			 }
			 else if(s.getStatus() == 1)
			 {
				 jsonobj.put("status", "Data Cleaned Successfully");
			 }
			 
			 jsonobj.put("reasion",s.getReasion());
			 
			 
			
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("getAllKpiCleaneRequest")
	public  String  getAllKpiCleaneRequest()
	{
		List<ListOfUpdatedKPI> data = updatedKpiRepo.findByStatus(0);
		data.sort(Comparator.comparingInt(ListOfUpdatedKPI::getId));

		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(ListOfUpdatedKPI s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			
			 jsonobj.put("requestTime", f.format(s.getUpdatedDate()));
			 
			 jsonobj.put("projectCode", s.getProjectCode());
			 jsonobj.put("kpiId", s.getKpiId());
			 if(s.getStatus() == 0)
			 {
				 jsonobj.put("status", "New Request");
			 }
			 else if(s.getStatus() == 1)
			 {
				 jsonobj.put("status", "Data Cleaned Successfully");
			 }
			 
			 jsonobj.put("reasion",s.getReasion());
			 
			 
			
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("getAllKpiCleanedRequest")
	public String  getAllKpiCleanedRequest()
	{
		List<ListOfUpdatedKPI> data = updatedKpiRepo.findByStatus(1);
		data.sort(Comparator.comparingInt(ListOfUpdatedKPI::getId));
		JSONArray levelList = new JSONArray();
		int i = 1;
		 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		for(ListOfUpdatedKPI s : data)
		{
			
			 JSONObject jsonobj = new JSONObject();
			
			 jsonobj.put("requestTime", f.format(s.getUpdatedDate()));
			 jsonobj.put("responseTime", f.format(s.getActionDate()));
			 
			 jsonobj.put("projectCode", s.getProjectCode());
			 jsonobj.put("kpiId", s.getKpiId());
			 if(s.getStatus() == 0)
			 {
				 jsonobj.put("status", "New Request");
			 }
			 else if(s.getStatus() == 1)
			 {
				 jsonobj.put("status", "Data Cleaned Successfully");
			 }
			 
			 jsonobj.put("reasion",s.getReasion());
			 
			 
			
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("cleanDataByProjectCode")
	public String cleanDataByProjectCode(@RequestParam("username") String username, @RequestParam("projectCode") int projectCode)
	{
		List<Tbl_Project_Detail_Intrim> proj = projRepo.findByUsername(username);
		String status = "";
		if(proj == null)
		{
			status = "Invalid Access";
		}
		else
		{
			java.util.Date newdate = new java.util.Date();
			Date timeStamp = new Date(newdate.getTime());
			ListOfUpdatedScheme data = updatedSchemeRepo.findByProjectCodeAndStatus(projectCode, 0);
			data.setActionDate(new Timestamp(timeStamp.getTime()));
			data.setStatus(1);
			/*
			GlobalVariable.projectCode = projectCode;
			JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis()).toJobParameters();
			try 
			{
				
				jobLauncher.run(job, jobParameters);
			} 
			catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) 
			{

				e.printStackTrace();
			}
			*/
			// call api to data clean
			CleanDataRequestDTO d = new CleanDataRequestDTO();
			d.setProjectCode(projectCode);
			String url = dataCleanApiLinkByProjectCode+"cleanDataRequestByProjectCode";
			try 
			{
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				String json = new ObjectMapper().writeValueAsString(d);
				HttpEntity<String> entity = new HttpEntity<>(json, headers);
				ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
				String res = r.getBody().toString();
				if(res.equals("Success"))
				{

					status = "Data Cleaned Successfully";
				}
				else
				{
					
					status = "Data Cleaned Unsuccessfuly";
				}
				
				


			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			updatedSchemeRepo.save(data);
			//deletedRepo.deleteByProjectCode(Long.valueOf(data.getProjectCode()));
			
			
		}
		return status;
	}
	@GetMapping("cleanDataByKpiId")
	public String cleanDataByKpiId(@RequestParam("username") String username, @RequestParam("kpiId") int kpiId, @RequestParam("projectCode") int projectCode)
	{
		List<Tbl_Project_Detail_Intrim> proj = projRepo.findByUsername(username);
		
		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		ListOfUpdatedKPI data = updatedKpiRepo.findByKpiIdAndStatusAndProjectCode(kpiId, 0, projectCode);
		data.setActionDate(new Timestamp(timeStamp.getTime()));
		data.setStatus(1);
		
		String status = "";
		if(proj == null)
		{
			status = "Invalid Access";
		}
		else
		{
		
			CleanDataRequestDTO d = new CleanDataRequestDTO();
			d.setProjectCode(projectCode);
			String url = dataCleanApiLinkByProjectCodeAndKpiId+"cleanDataByProjectCodeAndKpiId";
			try 
			{	
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				String json = new ObjectMapper().writeValueAsString(d);
				HttpEntity<String> entity = new HttpEntity<>(json, headers);
				ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
				String res = r.getBody().toString();
				if(res.equals("Success"))
				{
					
					status = "Data Cleaned Successfully";
				}
				else
				{
					
					status = "Data Cleaned Unsuccessfully";
				}
				
				


			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		/*	GlobalVariable.kpiId = kpiId;
			GlobalVariable.projectCode = projectCode;
			JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis()).toJobParameters();
			try 
			{
				
				jobLauncher.run(job, jobParameters);
			} 
			catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) 
			{

				e.printStackTrace();
			}
			*/
			updatedKpiRepo.save(data);
			//deletedRepo.deleteByProjectCode(Long.valueOf(0));
		
			
		}
		return status;
	}
	
}
