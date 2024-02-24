package com.nicsi.ceda.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nicsi.ceda.model.DataGranularityDetails;
import com.nicsi.ceda.model.Dim_Time;
import com.nicsi.ceda.model.DisplayDataLog;
import com.nicsi.ceda.model.KPI_PORTED_DATA_REPLICA;
import com.nicsi.ceda.model.SchemeDetailsDTO;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.repository.DataGranularityDetailsRepo;
import com.nicsi.ceda.repository.Dim_TimeRepo;
import com.nicsi.ceda.repository.KpiPortedDataReplicaRepo;
import com.nicsi.ceda.repository.MLevelRepo;
import com.nicsi.ceda.repository.Tbl_DataPort_KPI_PORTED_DATARepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;

@RestController
@ResponseBody
@CrossOrigin
public class ViewDataLogController 
{
	@Autowired
	private Tbl_DataPort_KPI_PORTED_DATARepo dataRepo;
	@Autowired
	private Dim_TimeRepo timeRepo;
	@Autowired
	Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private DataGranularityDetailsRepo granularityRepo;
	@Autowired
	private KpiPortedDataReplicaRepo kpiRepo;
	@Autowired
	private MLevelRepo levelRepo;
	
	@GetMapping("pragyanDataLog")
	public String getLogData( @RequestParam("schemeCode") int projectCode) throws JsonProcessingException
	{
		//String fromDate;
		//String toDate;
		//System.out.println("pragyanDataLog = "+projectCode);
		long longValue = projectCode;
		List<DisplayDataLog> logData = new ArrayList<DisplayDataLog>();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    Tbl_Project_Detail_Intrim proj= projRepo.findByProjectCode(projectCode);
	   
		
		List<Timestamp> data = dataRepo.getDistinctDateWhereProjectCode(longValue);
		Tbl_Project_Detail_Intrim p = projRepo.findByProjectCode(projectCode);
		//Integer dataFrequncyId = dataRepo.getDistinctModeFrequencyIdWhereProjectCode(longValue);
		Integer dataFrequncyId = p.getPortingFrequency();
		if(dataFrequncyId == null)
		{
			DisplayDataLog log = new DisplayDataLog();
			log.setProjectCode(projectCode);
			log.setStatus("Invalid Project Code");
			
			logData.add(log);
		}
		else if(dataFrequncyId == 1)
		{
			//daily
			//System.out.println("Getting Daily Data");
			if(data.size() > 0)
			{
				for(Timestamp ts : data)
				{
					DisplayDataLog log = new DisplayDataLog();
					log.setPortingFrequencyName("Daily");
					log.setProjectCode(proj.getProjectCode());
					int gId = projRepo.findByProjectCode(proj.getProjectCode()).getProject_Data_Granularity_ID();
					DataGranularityDetails d1 = granularityRepo.findByDataGranularityId(gId);
					log.setGranularityName(d1.getData_Granularity_Name_e());
					java.util.Date date = new java.util.Date(ts.getTime());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate = sdf.format(date);
					java.util.Date utilDate;
					
					try 
					{
						utilDate = sdf.parse(formattedDate);
						java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
						Dim_Time t1 = timeRepo.findByDdate(sqlDate);
						log.setFinYear(t1.getFinYearFull());
						LocalDate localDate = sqlDate.toLocalDate();
						
						
	
				        // Print the formatted date
				       
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						//String fDate = fromDate.format(formatter);
						//String tDate = toDate.format(formatter);
						log.setFromDate(formattedDate);
						log.setToDate(formattedDate);
						log.setScheduledOn(formattedDate);
						log.setMonthName(t1.getMonthNameShort());
						formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
		                
		               //entry date
		               
		                //check day diff
		                //get distinct record from ported_kpi based on project_code and entry date
						
						Timestamp entryDate =  dataRepo.getDistinctReocrdByProjectCodeAndEntryDate(longValue, date);
						java.util.Date portedDate = new java.util.Date(entryDate.getTime());
						String portDate = sdf.format(portedDate);
						formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						log.setFromDate(formattedDate);
			            log.setPortedOn(portDate);
			           
			            //schedule date is current date
			            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			            //ported = entryDt
			            LocalDate d = LocalDate.parse(log.getScheduledOn(), formatter);
			          //port date
			            LocalDate portDt = LocalDate.parse(log.getPortedOn(), formatter);
			            localDate = sqlDate.toLocalDate();
			            LocalDate increasedDate = d.plusDays(proj.getWaitingDays());
			          
			            date = new java.util.Date(ts.getTime());
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						formattedDate = sdf.format(date);
						d = LocalDate.parse(formattedDate, formatter);
						//check day diff
						long daysDifference = ChronoUnit.DAYS.between(portDt, increasedDate);
						String status;
						Timestamp timestamp  = dataRepo.getMaxDateByDataDate(proj.getProjectCode(), Date.valueOf(increasedDate));
						
		               
					    if(daysDifference == 0)
						{
								//Green
							status = "success";
						}
						else if(daysDifference <= proj.getWaitingDays())
						{
							//Light Yellow
							status = "withinWaitingDays";
						}
						else if(daysDifference > proj.getWaitingDays())
						{
							//Light Red
							status = "DIEP_delay";
								
						}
						else
						{
							//Red
							status = "failure";
						}
					    log.setStatus(status);
					   
		                //Timestamp entryDate = kpiData.getEntrydt();
					    String dtFormat = log.getToDate();
					    LocalDate ddMMyyyFormat = LocalDate.parse(dtFormat);
					    DateTimeFormatter desiredFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setToDate(formattedDate);
					    
					    dtFormat = log.getFromDate();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setFromDate(formattedDate);
					    
					    dtFormat = log.getPortedOn();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setPortedOn(formattedDate);
					    
					    dtFormat = log.getScheduledOn();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setScheduledOn(formattedDate);
				        // Parse the original date string
				        
	
				        // Define the desired output format
				        
	
				        // Format the date using the desired format
				        
		                logData.add(log);
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				DisplayDataLog log = new DisplayDataLog();
				log.setPortingFrequencyName("Daily");
				log.setProjectCode(proj.getProjectCode());
				log.setStatus("failure");
				log.setProjectCode(proj.getProjectCode());
				int gId = projRepo.findByProjectCode(proj.getProjectCode()).getProject_Data_Granularity_ID();
				DataGranularityDetails d1 = granularityRepo.findByDataGranularityId(gId);
				log.setGranularityName(d1.getData_Granularity_Name_e());
				logData.add(log);
			}
				
			
		}
		else if(dataFrequncyId == 2)
		{
			//weekly
			//daily
			//System.out.println("Getting Daily Data");
			
			if(data.size() > 0)
			{
				for(Timestamp ts : data)
				{
					DisplayDataLog log = new DisplayDataLog();
					log.setPortingFrequencyName("Weekly");
					log.setProjectCode(proj.getProjectCode());
					int gId = projRepo.findByProjectCode(proj.getProjectCode()).getProject_Data_Granularity_ID();
					DataGranularityDetails d1 = granularityRepo.findByDataGranularityId(gId);
					log.setGranularityName(d1.getData_Granularity_Name_e());
					java.util.Date date = new java.util.Date(ts.getTime());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate = sdf.format(date);
					java.util.Date utilDate;
					
					try 
					{
						utilDate = sdf.parse(formattedDate);
						java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
						//get week information from DimTime table
						Dim_Time t1 = timeRepo.findByDdate(sqlDate);
						log.setFinYear(t1.getFinYearFull());
						
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						//String fDate = fromDate.format(formatter);
						//String tDate = toDate.format(formatter);
						log.setFromDate(t1.getFirstDateOfWeek().toString());
						log.setToDate(t1.getLastDateOfWeek().toString());
						log.setScheduledOn(t1.getLastDateOfWeek().toString());
						log.setMonthName(t1.getMonthNameShort());
						formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		                
		               //entry date
		               
		                //check day diff
		                //get distinct record from ported_kpi based on project_code and entry date
						
						Timestamp entryDate =  dataRepo.getDistinctReocrdByProjectCodeAndEntryDate(longValue, date);
						java.util.Date portedDate = new java.util.Date(entryDate.getTime());
						String portDate = sdf.format(portedDate);
						formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						log.setFromDate(formattedDate);
			            log.setPortedOn(portDate);
			           
			            //schedule date is current date
			            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			            LocalDate d = LocalDate.parse(log.getScheduledOn(), formatter);
			          //port date
			            LocalDate portDt = LocalDate.parse(log.getPortedOn(), formatter);
			            LocalDate localDate = sqlDate.toLocalDate();
			            LocalDate increasedDate = d.plusDays(proj.getWaitingDays());
			            date = new java.util.Date(ts.getTime());
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						formattedDate = sdf.format(date);
						d = LocalDate.parse(formattedDate, formatter);
						long daysDifference = ChronoUnit.DAYS.between(portDt, increasedDate);
						String status;
						Timestamp timestamp  = dataRepo.getMaxDateByDataDate(proj.getProjectCode(), Date.valueOf(increasedDate));
						
		               
					    if(daysDifference == 0)
						{
								//Green
							status = "success";
						}
						else if(daysDifference <= proj.getWaitingDays())
						{
							//Light Yellow
							status = "withinWaitingDays";
						}
						else if(daysDifference > proj.getWaitingDays())
						{
							//Light Red
							status = "DIEP_delay";
								
						}
						else
						{
							//Red
							status = "failure";
						}
					    
					    log.setStatus(status);
					   
					    String dtFormat = log.getToDate();
					    LocalDate ddMMyyyFormat = LocalDate.parse(dtFormat);
					    DateTimeFormatter desiredFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setToDate(formattedDate);
					    
					    dtFormat = log.getFromDate();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setFromDate(formattedDate);
					    
					    dtFormat = log.getPortedOn();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setPortedOn(formattedDate);
					    
					    dtFormat = log.getScheduledOn();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setScheduledOn(formattedDate);
		                
		                logData.add(log);
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				DisplayDataLog log = new DisplayDataLog();
				log.setPortingFrequencyName("Weekly");
				log.setProjectCode(proj.getProjectCode());
				log.setStatus("failure");
				log.setProjectCode(proj.getProjectCode());
				int gId = projRepo.findByProjectCode(proj.getProjectCode()).getProject_Data_Granularity_ID();
				DataGranularityDetails d1 = granularityRepo.findByDataGranularityId(gId);
				log.setGranularityName(d1.getData_Granularity_Name_e());
				logData.add(log);
			}
			
			
		}
		else if(dataFrequncyId == 3)
		{
			//monthly
			if(data.size() > 0)
			{
				for(Timestamp ts : data)
				{
					DisplayDataLog log = new DisplayDataLog();
					log.setPortingFrequencyName("Monthly");
					log.setProjectCode(proj.getProjectCode());
					int gId = projRepo.findByProjectCode(proj.getProjectCode()).getProject_Data_Granularity_ID();
					DataGranularityDetails d1 = granularityRepo.findByDataGranularityId(gId);
					log.setGranularityName(d1.getData_Granularity_Name_e());
					java.util.Date date = new java.util.Date(ts.getTime());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate = sdf.format(date);
					java.util.Date utilDate;
					
					try 
					{
						utilDate = sdf.parse(formattedDate);
						java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
						//get week information from DimTime table
						Dim_Time t1 = timeRepo.findByDdate(sqlDate);
						log.setFinYear(t1.getFinYearFull());
						
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						//String fDate = fromDate.format(formatter);
						//String tDate = toDate.format(formatter);
						
						log.setFromDate(t1.getFirstDateOfMonth().toString());
						log.setToDate(t1.getLastDateOfMonth().toString());
						log.setScheduledOn(t1.getLastDateOfMonth().toString());
						log.setMonthName(t1.getMonthNameShort());
						formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		                
		               //entry date
		               
		                //check day diff
		                //get distinct record from ported_kpi based on project_code and entry date
						
						Timestamp entryDate =  dataRepo.getDistinctReocrdByProjectCodeAndEntryDate(longValue, date);
						java.util.Date portedDate = new java.util.Date(entryDate.getTime());
						String portDate = sdf.format(portedDate);
						formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						
			            log.setPortedOn(portDate);
			           
			            //schedule date is current date
			            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			            LocalDate d = LocalDate.parse(log.getScheduledOn(), formatter);
			            //port date
			            LocalDate portDt = LocalDate.parse(log.getPortedOn(), formatter);
			            
			            LocalDate localDate = sqlDate.toLocalDate();
			            LocalDate increasedDate = d.plusDays(proj.getWaitingDays());
			            
			           
			            date = new java.util.Date(ts.getTime());
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						formattedDate = sdf.format(date);
						d = LocalDate.parse(formattedDate, formatter);
						long daysDifference = ChronoUnit.DAYS.between(portDt, increasedDate);
						
						String status;
						Timestamp timestamp  = dataRepo.getMaxDateByDataDate(proj.getProjectCode(), Date.valueOf(increasedDate));
		               
					    if(daysDifference == 0)
						{
								//Green
							status = "success";
						}
						else if(daysDifference <= proj.getWaitingDays())
						{
							//Light Yellow
							status = "withinWaitingDays";
						}
						else if(daysDifference > proj.getWaitingDays())
						{
							//Light Red
							status = "DIEP_delay";
								
						}
						else
						{
							//Red
							status = "failure";
						}
					    
					    log.setStatus(status);
					   
					    String dtFormat = log.getToDate();
					    LocalDate ddMMyyyFormat = LocalDate.parse(dtFormat);
					    DateTimeFormatter desiredFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setToDate(formattedDate);
					    
					    dtFormat = log.getFromDate();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setFromDate(formattedDate);
					    
					    dtFormat = log.getPortedOn();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setPortedOn(formattedDate);
					    
					    dtFormat = log.getScheduledOn();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setScheduledOn(formattedDate);
		                
		                logData.add(log);
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				DisplayDataLog log = new DisplayDataLog();
				log.setPortingFrequencyName("Monthly");
				log.setProjectCode(proj.getProjectCode());
				log.setStatus("failure");
				log.setProjectCode(proj.getProjectCode());
				int gId = projRepo.findByProjectCode(proj.getProjectCode()).getProject_Data_Granularity_ID();
				DataGranularityDetails d1 = granularityRepo.findByDataGranularityId(gId);
				log.setGranularityName(d1.getData_Granularity_Name_e());
				logData.add(log);
			}
		}	
		else if(dataFrequncyId == 4)
		{
			
			//quaterly
			if(data.size() > 0)
			{
				for(Timestamp ts : data)
				{
					DisplayDataLog log = new DisplayDataLog();
					log.setPortingFrequencyName("Quarterly");
					log.setProjectCode(proj.getProjectCode());
					int gId = projRepo.findByProjectCode(proj.getProjectCode()).getProject_Data_Granularity_ID();
					DataGranularityDetails d1 = granularityRepo.findByDataGranularityId(gId);
					log.setGranularityName(d1.getData_Granularity_Name_e());
					java.util.Date date = new java.util.Date(ts.getTime());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    String formattedDate = sdf.format(date);
				    java.util.Date utilDate;
				   
					try 
					{
						utilDate = sdf.parse(formattedDate);
						java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
						Dim_Time t1 = timeRepo.findByDdate(sqlDate);
						log.setFinYear(t1.getFinYearFull());
						LocalDate localDate = sqlDate.toLocalDate();
						//System.out.println("localDate = "+localDate);
						LocalDate fromDate = LocalDate.of(localDate.getYear(), 4, 1);
						LocalDate toDate = LocalDate.of(localDate.getYear(), 6, 30);
						
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						String fDate = fromDate.format(formatter);
						String tDate = toDate.format(formatter);
				
						
						if (localDate.isAfter(fromDate.minusDays(1)) && localDate.isBefore(toDate.plusDays(1)))
		                {
							
							log.setFromDate(fDate);
							log.setToDate(tDate);
							log.setScheduledOn(tDate);
							log.setMonthName("APRIL - JUNE");
		                } 
						
						fromDate = LocalDate.of(localDate.getYear(), 7, 1);
						toDate = LocalDate.of(localDate.getYear(), 9, 30);
						fDate = fromDate.format(formatter);
						tDate = toDate.format(formatter);
						
		                if(localDate.isAfter(fromDate.minusDays(1)) && localDate.isBefore(toDate.plusDays(1)))
		                {
		                	log.setMonthName("JULY - SEPT");
		                	log.setScheduledOn(tDate);
		                	log.setFromDate(fDate);
							log.setToDate(tDate);
		                }
		                
		                fromDate = LocalDate.of(localDate.getYear(), 10, 1);
						toDate = LocalDate.of(localDate.getYear(), 12, 31);
						fDate = fromDate.format(formatter);
						tDate = toDate.format(formatter);
						//System.out.println(fromDate.minusDays(1));
						//System.out.println(toDate.plusDays(1));
						//System.out.println(localDate.isAfter(fromDate.minusDays(1)) && localDate.isBefore(toDate.plusDays(1)));
		                if(localDate.isAfter(fromDate.minusDays(1)) && localDate.isBefore(toDate.plusDays(1)))
		                {
		                	log.setMonthName("OCT - DEC");
		                	log.setScheduledOn(tDate);
		                	log.setFromDate(fDate);
							log.setToDate(tDate);
		                }
		                fromDate = LocalDate.of(localDate.getYear(), 01, 1);
						toDate = LocalDate.of(localDate.getYear(), 03, 31);
						fDate = fromDate.format(formatter);
						tDate = toDate.format(formatter);
		                if(localDate.isAfter(fromDate.minusDays(1)) && localDate.isBefore(toDate.plusDays(1)))
		                {
		                	log.setMonthName("JAN - MARCH");
		                	log.setScheduledOn(tDate);
		                	log.setFromDate(fDate);
							log.setToDate(tDate);
		                }
		                
		               // System.out.println("date = "+date);
		                Timestamp entryDate = null;
		                try
		                {
		                	entryDate =  dataRepo.getDistinctReocrdByProjectCodeAndEntryDate(longValue, date);
		                }
		                catch (Exception e) 
		                {
							System.out.println("Exception");
							e.printStackTrace();
						}
						java.util.Date portedDate = new java.util.Date(entryDate.getTime());
						String portDate = sdf.format(portedDate);
						
						//formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						log.setPortedOn(portDate);
			           
			            //schedule date is current date
			           formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			            LocalDate d = LocalDate.parse(log.getScheduledOn(), formatter);
			          //port date
			            LocalDate portDt = LocalDate.parse(log.getPortedOn(), formatter);
			            localDate = sqlDate.toLocalDate();
			            LocalDate increasedDate = d.plusDays(proj.getWaitingDays());
			           
			            date = new java.util.Date(ts.getTime());
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						formattedDate = sdf.format(date);
						d = LocalDate.parse(formattedDate, formatter);
						
						long daysDifference = ChronoUnit.DAYS.between(portDt, increasedDate);
						String status;
						
						
					    if(daysDifference == 0)
						{
								//Green
							status = "success";
						}
						else if(daysDifference <= proj.getWaitingDays())
						{
							//Light Yellow
							status = "withinWaitingDays";
						}
						else if(daysDifference > proj.getWaitingDays())
						{
							//Light Red
							status = "DIEP_delay";
								
						}
						else
						{
							//Red
							status = "failure";
						}
					    log.setStatus(status);
					    String dtFormat = log.getToDate();
					    LocalDate ddMMyyyFormat = LocalDate.parse(dtFormat);
					    DateTimeFormatter desiredFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setToDate(formattedDate);
					    
					    dtFormat = log.getFromDate();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setFromDate(formattedDate);
					    
					    dtFormat = log.getPortedOn();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setPortedOn(formattedDate);
					    
					    dtFormat = log.getScheduledOn();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setScheduledOn(formattedDate);
		                
		                logData.add(log);
					    
					} 
					catch (ParseException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			           
					
				}
			}
			else
			{
				DisplayDataLog log = new DisplayDataLog();
				log.setPortingFrequencyName("Quarterly");
				log.setProjectCode(proj.getProjectCode());
				log.setStatus("failure");
				log.setProjectCode(proj.getProjectCode());
				int gId = projRepo.findByProjectCode(proj.getProjectCode()).getProject_Data_Granularity_ID();
				DataGranularityDetails d1 = granularityRepo.findByDataGranularityId(gId);
				log.setGranularityName(d1.getData_Granularity_Name_e());
				logData.add(log);
			}
		}
		else if(dataFrequncyId == 5)
		{
			//System.out.println("Half Yearly="+data.size());
			//half yearly
			if(data.size() > 0)
			{
				for(Timestamp ts : data)
				{
					DisplayDataLog log = new DisplayDataLog();
					log.setPortingFrequencyName("Half Yearly");
					log.setProjectCode(proj.getProjectCode());
					int gId = projRepo.findByProjectCode(proj.getProjectCode()).getProject_Data_Granularity_ID();
					DataGranularityDetails d1 = granularityRepo.findByDataGranularityId(gId);
					log.setGranularityName(d1.getData_Granularity_Name_e());
					java.util.Date date = new java.util.Date(ts.getTime());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate = sdf.format(date);
					java.util.Date utilDate;
					
					try 
					{
						utilDate = sdf.parse(formattedDate);
						java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
						Dim_Time t1 = timeRepo.findByDdate(sqlDate);
						log.setFinYear(t1.getFinYearFull());
						LocalDate localDate = sqlDate.toLocalDate();
						
						LocalDate fromDate = LocalDate.of(localDate.getYear(), 4, 1);
						LocalDate toDate = LocalDate.of(localDate.getYear(), 9, 30);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						String fDate = fromDate.format(formatter);
						String tDate = toDate.format(formatter);
						if (localDate.isAfter(fromDate.minusDays(1)) && localDate.isBefore(toDate.plusDays(1)))
		                {
							
							log.setFromDate(fDate);
							log.setToDate(tDate);
							log.setScheduledOn(tDate);
							log.setMonthName("APRIL - SEPT");
							log.setScheduledOn(toDate.toString());
		                } 
						else
		                { 
							localDate = LocalDate.now();
							LocalDate nextYearDate = localDate.plusYears(1);
							fromDate = LocalDate.of(localDate.getYear()-1, 10, 1);
							toDate = LocalDate.of(localDate.getYear(), 3, 31);
							
							formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
							fDate = fromDate.format(formatter);
							tDate = toDate.format(formatter);
							
							log.setFromDate(fDate);
							log.setToDate(tDate);
							log.setScheduledOn(tDate);
							log.setMonthName("OCT - MARCH");
							log.setScheduledOn(toDate.toString());
		                } 
						//get week information from DimTime table
						log.setFinYear(t1.getFinYearFull());
						
						//String fDate = fromDate.format(formatter);
						//String tDate = toDate.format(formatter);

						
						
						
					
						formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		                
		               //entry date
		               
		                //check day diff
		                //get distinct record from ported_kpi based on project_code and entry date
						
						Timestamp entryDate =  dataRepo.getDistinctReocrdByProjectCodeAndEntryDate(longValue, date);
						java.util.Date portedDate = new java.util.Date(entryDate.getTime());
						String portDate = sdf.format(portedDate);
						formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						//log.setFromDate(formattedDate);
			            log.setPortedOn(portDate);
			           
			            //schedule date is current date
			            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			            LocalDate d = LocalDate.parse(log.getScheduledOn(), formatter);
			          //port date
			            LocalDate portDt = LocalDate.parse(log.getPortedOn(), formatter);
			            localDate = sqlDate.toLocalDate();
			            LocalDate increasedDate = d.plusDays(proj.getWaitingDays());
			            date = new java.util.Date(ts.getTime());
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						formattedDate = sdf.format(date);
						d = LocalDate.parse(formattedDate, formatter);
						System.out.println("portDt= "+portDt);
						System.out.println("increasedDate= "+increasedDate);
						long daysDifference = ChronoUnit.DAYS.between(portDt, increasedDate);
						System.out.println("daysDifference = "+daysDifference);
						String status;
						Timestamp timestamp  = dataRepo.getMaxDateByDataDate(proj.getProjectCode(), Date.valueOf(increasedDate));
		               
					    if(daysDifference == 0)
						{
								//Green
							status = "success";
						}
					    else if (portDt.isBefore(increasedDate))
					    {
					    	status = "success";
					    }
						else if(daysDifference <= proj.getWaitingDays()  &&  daysDifference > 0)
						{
							//Light Yellow
							status = "withinWaitingDays";
						}
						else if(daysDifference > proj.getWaitingDays() || daysDifference < 0)
						{
							//Light Red
							status = "DIEP_delay";
								
						}
						else
						{
							//Red
							status = "failure";
						}
					    
					    log.setStatus(status);
					    DateTimeFormatter desiredFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					   
					    
					    String dtFormat = log.getFromDate();
					    
					    dtFormat = log.getPortedOn();
					    LocalDate  ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setPortedOn(formattedDate);
					    
					    dtFormat = log.getScheduledOn();
					    ddMMyyyFormat = LocalDate.parse(dtFormat);
					    formattedDate = ddMMyyyFormat.format(desiredFormat);
					    log.setScheduledOn(formattedDate);
		                
		                logData.add(log);
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				DisplayDataLog log = new DisplayDataLog();
				log.setPortingFrequencyName("Weekly");
				log.setProjectCode(proj.getProjectCode());
				log.setStatus("failure");
				log.setProjectCode(proj.getProjectCode());
				int gId = projRepo.findByProjectCode(proj.getProjectCode()).getProject_Data_Granularity_ID();
				DataGranularityDetails d1 = granularityRepo.findByDataGranularityId(gId);
				log.setGranularityName(d1.getData_Granularity_Name_e());
				logData.add(log);
			}
		}
		else
		{
			//yearly
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(logData);

		
		return json;
	}
	
	@GetMapping("viewRawDataByAdmin")
	public String getRawDataForLogByAdmin(@RequestParam("schemeCode") int schemeCode, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate)
	{
		//System.out.println("viewRawDataByAdmin");
		//System.out.println(schemeCode);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date utilDate;
        String jsonString = "";
		try 
		{
			utilDate = dateFormat.parse(fromDate);
			Date fDate = new java.sql.Date(utilDate.getTime());
			utilDate = dateFormat.parse(toDate);
			Date tDate = new java.sql.Date(utilDate.getTime());
			//System.out.println("From Date = "+fDate);
			//System.out.println("To Date = "+tDate);
			List<KPI_PORTED_DATA_REPLICA> data = kpiRepo.findByProjectCodeAndDataDateBetween(schemeCode, fDate, tDate);
			//System.out.println("Data = "+data);
			Gson gson = new Gson();
			jsonString = gson.toJson(data);
			
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("jsonString = "+jsonString);
		return jsonString;
	}
	@GetMapping("viewRawDataByUser")
	public String getRawDataForLog(@RequestParam("schemeCode") int schemeCode, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,  @RequestParam("username") String username)
	{
		
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCodeAndUsername(schemeCode, username);
		System.out.println("Project = "+proj);
		String jsonString = "";
		if(proj == null)
		{
			
		}
		else
		{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	        java.util.Date utilDate;
	       
			try 
			{
				utilDate = dateFormat.parse(fromDate);
				Date fDate = new java.sql.Date(utilDate.getTime());
				utilDate = dateFormat.parse(toDate);
				Date tDate = new java.sql.Date(utilDate.getTime());
				List<KPI_PORTED_DATA_REPLICA> data = kpiRepo.findByProjectCodeAndDataDateBetween(schemeCode, fDate, tDate);
				Gson gson = new Gson();
				jsonString = gson.toJson(data);
				
			} 
			catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonString;
	}
	@GetMapping("viewSchemeDetailsForDataLog")
	public String viewScheme()
	{
		//System.out.println("viewActivatedScheme calling");
		//map.addAttribute("data",dataService.findActivatedScheme(map, request, sessions));
		List<Tbl_Project_Detail_Intrim> data =projRepo.findByStatus(1);
		//sort based on project code
		data.sort(Comparator.comparing(Tbl_Project_Detail_Intrim :: getProjectCode));
		
		JSONArray levelList = new JSONArray();
		int sn = 1;
		for(Tbl_Project_Detail_Intrim s : data)
		{
			 
			  
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", sn++);
			 jsonobj.put("id", s.getId());
			 jsonobj.put("schemeCode", s.getProjectCode());
			 jsonobj.put("schemeName", s.getProject_name_e());
			 jsonobj.put("ministryCode", s.getMinistry_code());
			 jsonobj.put("departmentCode", s.getDept_code());
			
			 String ministryName = levelRepo.getMinistryName(s.getMinistry_code(), 22);
			 String deptName = levelRepo.getDepartmentName(s.getDept_code(), 21);
			 
			 jsonobj.put("ministryName",ministryName);
			 jsonobj.put("departmentName",deptName);
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("getSchemeNameAndFrequencyName")
	public String getSchemeNameAndFrequencyName(@RequestParam("schemeCode") int schemeCode)
	{
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(schemeCode);
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("schemeName", proj.getProject_name_e());
		int portFreq = proj.getPortingFrequency();
		String freqName = "";
		if(portFreq == 1)
		{
			freqName = "Daily";
		}
		else if(portFreq == 2)
		{
			freqName = "Weekly";
		}
		else if(portFreq == 3)
		{
			freqName = "Monthly";
		}
		else if(portFreq == 4)
		{
			freqName = "Quarterly";
		}
		else if(portFreq == 5)
		{
			freqName = "Half Yearly";
			
		}
		else 
		{
			freqName = "Yearly";
		}
		
		jsonobj.put("frequencyName", freqName);
		
		return jsonobj.toString();
	}
}
