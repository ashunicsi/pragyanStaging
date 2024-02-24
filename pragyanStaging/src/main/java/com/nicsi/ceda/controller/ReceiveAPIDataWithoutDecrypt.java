package com.nicsi.ceda.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nicsi.ceda.util.AesEncryptor;
import com.nicsi.ceda.model.Registration;
import com.nicsi.ceda.model.TableDataTemp;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.repository.RegistrationRepo;
import com.nicsi.ceda.repository.TableDataTempRepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;

@RestController
public class ReceiveAPIDataWithoutDecrypt 
{
	
	
	private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Value("${token.key}")
    private String tokenKey;
	@Autowired
	private TableDataTempRepo dataRepo;
	@Autowired
	private Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private AesEncryptor aes;
	@Autowired
	private RegistrationRepo regRepo;
	private static final Logger logger = LoggerFactory.getLogger(ReceiveAPIDataWithoutDecrypt.class);
	
	@PostMapping(value="recvUserApiData", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getUserApiData(@RequestBody  List<TableDataTemp> table) throws JsonProcessingException, ParseException
	{ 
		
		logger.info("Size of Received Data = "+table.size());
		logger.info("Received Data = "+table);
		System.out.println("Size of Received Data is "+table.size());
		System.out.println("Size of Received Data is "+table);
		int status = 0;
		for(TableDataTemp t : table)
		{
			if(t.getToken() != null)
			{
				
				//int projectCode = 1011;//(Integer) aes.convertToEntityAttribute(t.getProjectCode());
				//Tbl_Project_Detail_Intrim projDetails = projRepo.findByProjectCode(projectCode);
				Registration reg = regRepo.findByTokenKey(t.getToken());
				if(reg.getTokenKey().equals(t.getToken()))
				{
					status = 1;
					break;
				}
				else
				{
					status = 0;
					break;
				}
			}
			else
			{
				status = 2;
			}
		}
		try
		{
			//System.out.println("Status = "+status);
			if(status == 1)
			{
				List<TableDataTemp> newList = table.stream()
                        .map(obj -> {
                            obj.setApiLanguageId(2);
                            return obj;
                        }).collect(Collectors.toList());
				java.util.Date newdate = new java.util.Date();
				Date timeStamp = new Date(newdate.getTime());
				//System.out.println("timeStamp = "+timeStamp);
				newList = table.stream()
                        .map(obj -> {
                            obj.setEntryDate(new Timestamp(timeStamp.getTime()));
                            return obj;
                        }).collect(Collectors.toList());
				
				dataRepo.saveAll(newList);
				
				return new ResponseEntity<>("Success", HttpStatus.OK);
			}
			else if(status == 0)
			{
				return new ResponseEntity<>("Fail", HttpStatus.BAD_GATEWAY);
			}
			else
			{
				
				return new ResponseEntity<>("No Data", HttpStatus.BAD_GATEWAY);
			}
			
		}
		catch (Exception e) 
		{
			return new ResponseEntity<>("Fail", HttpStatus.BAD_GATEWAY);

		}
		
	}
	@PostMapping(value="recvJavaUserApiData", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getJavaUserApiData(@RequestBody  List<TableDataTemp> table) throws JsonProcessingException, ParseException
	{ 
		
		logger.info("Size of Received Data = "+table.size());
		logger.info("Received Data = "+table);
		System.out.println("Size of Received Data is "+table.size());
		System.out.println("Size of Received Data is "+table);
		
		int status = 0;
		for(TableDataTemp t : table)
		{
			if(t.getToken() != null)
			{
				
				//int projectCode = 1011;//(Integer) aes.convertToEntityAttribute(t.getProjectCode());
				//Tbl_Project_Detail_Intrim projDetails = projRepo.findByProjectCode(projectCode);
				Registration reg = regRepo.findByTokenKey(t.getToken());
				
				if(reg.getTokenKey().equals(t.getToken()))
				{
					status = 1;
					break;
				}
				else
				{
					status = 0;
					break;
				}
			}
			else
			{
				status = 2;
			}
		}
		try
		{
			
			if(status == 1)
			{
				List<TableDataTemp> newList = table.stream()
                        .map(obj -> {
                            obj.setApiLanguageId(1);
                            return obj;
                        }).collect(Collectors.toList());
				java.util.Date newdate = new java.util.Date();
				Date timeStamp = new Date(newdate.getTime());
				//System.out.println("timeStamp = "+timeStamp);
				newList = table.stream()
                        .map(obj -> {
                            obj.setEntryDate(new Timestamp(timeStamp.getTime()));
                            return obj;
                        }).collect(Collectors.toList());
				
				dataRepo.saveAll(newList);
				
				return new ResponseEntity<>("Success", HttpStatus.OK);
			}
			else if(status == 0)
			{
				return new ResponseEntity<>("Fail", HttpStatus.BAD_GATEWAY);
			}
			else
			{
				
				return new ResponseEntity<>("No Data", HttpStatus.BAD_GATEWAY);
			}
			
		}
		catch (Exception e) 
		{
			return new ResponseEntity<>("Fail", HttpStatus.BAD_GATEWAY);

		}
		
	}
	
}	
