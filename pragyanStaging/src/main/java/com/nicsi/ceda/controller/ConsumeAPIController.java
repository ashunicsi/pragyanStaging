package com.nicsi.ceda.controller;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nicsi.ceda.util.AesEncryptor;
import com.nicsi.ceda.model.DataFrequency;
import com.nicsi.ceda.model.DataGranularityDetails;
import com.nicsi.ceda.model.Dim_Time;
import com.nicsi.ceda.model.KPI_PORTED_DATA_REPLICA;
import com.nicsi.ceda.model.MLevel;
import com.nicsi.ceda.model.RecvApiDataLogs;
import com.nicsi.ceda.model.RecvApiDataLogsToDisplay;
import com.nicsi.ceda.model.TableDataTemp;
import com.nicsi.ceda.model.Tbl_DataPort_KPI_PORTED_DATA;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.repository.DataFrequencyRepo;
import com.nicsi.ceda.repository.DataGranularityDetailsRepo;
import com.nicsi.ceda.repository.DepartmentRepo;
import com.nicsi.ceda.repository.Dim_TimeRepo;
import com.nicsi.ceda.repository.ListOfOrganisationRepo;
import com.nicsi.ceda.repository.MLevelRepo;
import com.nicsi.ceda.repository.MSchemeDepMinistrySecMapRepo;
import com.nicsi.ceda.repository.MinistryRepo;
import com.nicsi.ceda.repository.RecvApiDataLogsRepo;
import com.nicsi.ceda.repository.RecvApiDataLogsToDisplayRepo;
import com.nicsi.ceda.repository.SectorRepo;
import com.nicsi.ceda.repository.Tbl_DataPort_KPI_PORTED_DATARepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.repository.Tbl_Project_KPI_Detail_IntrimRepo;

@RestController
public class ConsumeAPIController 
{
	@Autowired
	private AesEncryptor aes;
	@Autowired
	private Tbl_DataPort_KPI_PORTED_DATARepo dataRepo;
	@Autowired
	private MinistryRepo minRepo;
	@Autowired
	private ListOfOrganisationRepo orgRepo;
	@Autowired
	private MLevelRepo levelRepo;
	@Autowired
	private SectorRepo sectorRepo;
	@Autowired
	private DepartmentRepo deptRepo;
	@Autowired
	private MSchemeDepMinistrySecMapRepo schemeRepo;
	@Autowired
	private DataGranularityDetailsRepo granularityRepo;
	@Autowired
	private RecvApiDataLogsRepo logRepo;
	@Autowired
	private Dim_TimeRepo timeRepo;
	@Autowired
	private Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private DataFrequencyRepo freqRepo;
	@Autowired
	private RecvApiDataLogsToDisplayRepo dispRepo;
	
	
	
	
	
	private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Value("${token.key}")
    private String tokenKey;
	
	@PostMapping(value="recvApidata", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> recvApidata(@RequestBody  List<TableDataTemp> table) throws JsonProcessingException, ParseException
	{
		
		int flag = 1;
		RecvApiDataLogs log = new RecvApiDataLogs();
		RecvApiDataLogsToDisplay display = new RecvApiDataLogsToDisplay();
		for(TableDataTemp t : table)
		{
			
			//String token = aes.convertToEntityAttribute(t.getToken()).toString();
			//System.out.println("Token From API = "+token);
			
			if(1 == 1)
			{
				  Tbl_DataPort_KPI_PORTED_DATA data = new Tbl_DataPort_KPI_PORTED_DATA();
				 
				  data.setData_Group_Id(Integer.parseInt(aes.convertToEntityAttribute(t.getGroupId()).toString()));
				  data.setInstance_Id(Integer.parseInt(aes.convertToEntityAttribute(t.getInstanceCode()).toString())); 
				  data.setIsComplete(1);
				  data.setKpi_Data(Double.parseDouble(aes.convertToEntityAttribute(t.getKpiValue()).toString()));
				  data.setKpiId(Integer.parseInt(aes.convertToEntityAttribute(t.getKpiId()).toString()));
				  
				  data.setLevel1Code(Integer.parseInt(aes.convertToEntityAttribute(t.getLevel1Code()).toString()));
				  data.setLevel2Code(Integer.parseInt(aes.convertToEntityAttribute(t.getLevel2Code()).toString()));
				  data.setLevel3Code(Integer.parseInt(aes.convertToEntityAttribute(t.getLevel3Code()).toString()));
				  data.setLevel4Code(Integer.parseInt(aes.convertToEntityAttribute(t.getLevel4Code()).toString()));
				  data.setLevel5Code(Integer.parseInt(aes.convertToEntityAttribute(t.getLevel5Code()).toString()));
				  data.setLevel6Code(Integer.parseInt(aes.convertToEntityAttribute(t.getLevel6Code()).toString()));
				  data.setLevel7Code(Integer.parseInt(aes.convertToEntityAttribute(t.getLevel7Code()).toString()));
				  data.setLevel8Code(Integer.parseInt(aes.convertToEntityAttribute(t.getLevel8Code()).toString()));
				  data.setLevel9Code(Integer.parseInt(aes.convertToEntityAttribute(t.getLevel9Code()).toString()));
				  data.setLevel10Code(Integer.parseInt(aes.convertToEntityAttribute(t.getLevel10Code()).toString()));
				  data.setMigrated(0);
				  data.setMinistry_Code(Integer.parseInt(aes.convertToEntityAttribute(t.getMinistryCode()).toString()));
				  data.setDept_code((Integer.parseInt(aes.convertToEntityAttribute(t.getDepartmentCode()).toString())));
				  data.setMode_frequency_id(Integer.parseInt(aes.convertToEntityAttribute(t.getFrequencyId()).toString()));
				  data.setProjectCode(Long.parseLong(aes.convertToEntityAttribute(t.getProjectCode()).toString()));
				  data.setSec_code(Integer.parseInt(aes.convertToEntityAttribute(t.getSectorCode()).toString()));
				  data.setSimilarity(0.0);
				  String fromDate = aes.convertToEntityAttribute(t.getFromDurationOfData()).toString();
				 // fromDate = fromDate + " 00:00:00";
				  Timestamp ts = Timestamp.valueOf(fromDate);
				  data.setDatadt(ts);
				  String todate = aes.convertToEntityAttribute(t.getToDurationOfData()).toString();
				  ts = Timestamp.valueOf(todate);
				  data.setEntrydt(ts);
				  data.setAtmpt(0);
				  
				  try
				  {
					  if(Double.parseDouble(aes.convertToEntityAttribute(t.getKpiValue()).toString()) != 0)
					  {  
						  //dataRepo.save(data);
					  }
					  Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(data.getProjectCode().intValue());
					  data.setUsername(proj.getUsername());
					  dataRepo.save(data);
					  
					  //get logs information
					  log.setInstance_Code(1);
					  log.setInstance_Lvl_Code(1);
					  log.setInstance_TypeCode_main(1);
					  log.setInstance_TypeCode_Sub(1);
					  log.setLevel1_Code(data.getLevel1Code());
					  log.setLevel2_Code(data.getLevel2Code());
					  log.setLevel3_Code(data.getLevel3Code());
					  log.setLevel4_Code(data.getLevel4Code());
					  log.setLevel5_Code(data.getLevel5Code());
					  log.setLevel6_Code(data.getLevel6Code());
					  log.setLevel7_Code(data.getLevel7Code());
					  log.setLevel8_Code(data.getLevel8Code());
					  log.setLevel9_Code(data.getLevel9Code());
					  log.setLevel10_Code(data.getLevel10Code());
					  log.setSec_Code(data.getSec_code());
					  log.setMinistry_Code(data.getMinistry_Code());
					  log.setDept_Code(data.getDept_code());
					  log.setProjectCode(data.getProjectCode().intValue());
					  
					  LocalDate currentDate = LocalDate.now();
				      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				      String sqlFormattedDate = currentDate.format(formatter);
				      java.sql.Date date=Date.valueOf(sqlFormattedDate);
				      log.setData_Port_Start_Date(date);
					  //get portingFreqId and name from scheme reg
					  
					  log.setData_Port_Description_Id(proj.getPortingFrequency());
					  
					  log.setData_Port_Description_Name(freqRepo.getDataFrequencyName(Long.valueOf(proj.getPortingFrequency())));
					  LocalDateTime ldt = LocalDateTime.now();
					  Calendar calendar = Calendar.getInstance();
				      calendar.add(Calendar.MONTH, -1);
				      int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				      calendar.set(Calendar.DAY_OF_MONTH, max);
				      SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				      
				      
				      //based on freqId set to and from date
				      //log.setData_From_Date(date);
				      LocalTime currentTime = LocalTime.now();
				      formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
				      String sqlFormattedTime = currentTime.format(formatter);
				      Time t1 = Time.valueOf(sqlFormattedTime);
				      log.setData_From_Time(t1);
				      log.setData_To_Time(t1);
				      currentDate = LocalDate.now();
				      formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				        String formattedDate = currentDate.format(formatter);
				     //get financial year name from dim_time table
				      String todayDate = format1.format(calendar.getTime());
				      Date d = Date.valueOf(formattedDate);
				      Dim_Time dimTime = timeRepo.findByDdate(d);
				      log.setData_Port_Description_Detail(freqRepo.getDataFrequencyName(Long.valueOf(log.getData_Port_Description_Id()))+" ("+dimTime.getFinYearFull()+")");
				     
				      
				     
				      
				      
				     
				      //get porting schedule date (Based on porting frequency)
				      //get porting frequency id
				     int freqId = proj.getPortingFrequency();
				     if(freqId == 1)
				     {
				    	 //daily
				    	 currentDate = LocalDate.now();
					     formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					     sqlFormattedDate = currentDate.format(formatter);
					     date=Date.valueOf(sqlFormattedDate);
					     
					     log.setData_Port_Schedule_Date(date);
					     log.setData_Port_Schedule_Time(t1);
					     Calendar cal  = Calendar.getInstance();
					     //subtracting a day
					     cal.add(Calendar.DATE, -1);
					     SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
					     String result = s.format(new Date(cal.getTimeInMillis()));
					     date=Date.valueOf(result);
					     log.setData_To_Date(date);
					     log.setData_From_Date(date);
					     
				     }
				     if(freqId == 2)
				     {
				    	 //weekly-get week 1st date and last date
				    	 
				    	 log.setData_Port_Schedule_Date(dimTime.getFirstDateOfWeek());
					     log.setData_Port_Schedule_Time(t1);
					     final ZonedDateTime input = ZonedDateTime.now();
					     final ZonedDateTime startOfLastWeek = input.minusWeeks(1).with(DayOfWeek.MONDAY);
					     LocalDate localDate = startOfLastWeek.toLocalDate();
					     // Convert LocalDate to java.sql.Date
					     Date lastWkFirstDate = Date.valueOf(localDate);
					   	 final ZonedDateTime endOfLastWeek = startOfLastWeek.plusDays(6);
					   	 localDate = endOfLastWeek.toLocalDate();
					   	 Date lastWkLastDate = Date.valueOf(localDate);
					   	 
					   	 log.setData_From_Date(lastWkFirstDate);
					     log.setData_To_Date(lastWkLastDate);
					    
				    	 
				     }
				     if(freqId == 3)
				     {
				    	 //monthly
				    	 log.setData_Port_Schedule_Date(dimTime.getFirstDateOfMonth());
					     log.setData_Port_Schedule_Time(t1);
					     String prevMonthLastDate = format1.format(calendar.getTime());
					     String prevMonthFirstDate =  format1.format(toDate(LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth())));
					     date=Date.valueOf(prevMonthFirstDate);
					     log.setData_From_Date(date);
					     date=Date.valueOf(prevMonthLastDate);
					     log.setData_To_Date(date);
					      
				     }
				     if(freqId == 4)
				     {
				    	//quarterly

					      int finQtr = dimTime.getQrter();
					    	 String dt = "";
					    	 if(finQtr == 1)
					    	 {
					    		 dt = dimTime.getFinYear()+"-"+"07"+"-"+"01";
					    		 d=Date.valueOf(dt);
					    		 log.setData_Port_Schedule_Date(d);
							     log.setData_Port_Schedule_Time(t1);
					    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"04"+"-"+"01");
					    		 log.setData_From_Date(date);
					    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"06"+"-"+"30");
					    		 log.setData_To_Date(date);
					    	 }
					    	 else if(finQtr == 2)
					    	 {
					    		 dt = dimTime.getFinYear()+"-"+"10"+"-"+"01";
					    		 d=Date.valueOf(dt);
					    		 log.setData_Port_Schedule_Date(d);
							     log.setData_Port_Schedule_Time(t1);
					    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"07"+"-"+"01");
					    		 log.setData_From_Date(date);
					    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"09"+"-"+"30");
					    		 log.setData_To_Date(date);
					    	 }
					    	 else if(finQtr == 3)
					    	 {
					    		 int nextYrName = dimTime.getFinYear();
					    		 nextYrName =  nextYrName + 1;
					    		 dt = nextYrName+"-"+"01"+"-"+"01";
					    		 d=Date.valueOf(dt);
					    		 log.setData_Port_Schedule_Date(d);
							     log.setData_Port_Schedule_Time(t1);
					    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"10"+"-"+"01");
					    		 log.setData_From_Date(date);
					    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"12"+"-"+"31");
					    		 log.setData_To_Date(date);
					    	 }
					    	 else
					    	 {
					    		 int nextYrName = dimTime.getFinYear();
					    		 nextYrName =  nextYrName + 1;
					    		 dt = nextYrName+"-"+"04"+"-"+"01";
					    		 d=Date.valueOf(dt);
					    		 log.setData_Port_Schedule_Date(d);
							     log.setData_Port_Schedule_Time(t1);
					    		 date=Date.valueOf(nextYrName+"-"+"01"+"-"+"01");
					    		 log.setData_From_Date(date);
					    		 date=Date.valueOf(nextYrName+"-"+"03"+"-"+"31");
					    		 log.setData_To_Date(date);
					    	 }
				    	
					   
				     }
				     if(freqId == 5)
				     {
				    	 //half yearly(Apr-Sept)(Oct-March) oct/apr
				    	 int finQtr = dimTime.getFinquarter();
				    	 String dt = "";
				    	 if(finQtr == 1 || finQtr == 2)
				    	 {
				    		 dt = dimTime.getFinYear()+"-"+"10"+"-"+"01";
				    		 d=Date.valueOf(dt);
				    		 log.setData_Port_Schedule_Date(d);
						     log.setData_Port_Schedule_Time(t1);
				    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"04"+"-"+"01");
				    		 log.setData_From_Date(date);
				    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"09"+"-"+"30");
				    		 log.setData_To_Date(date);
				    	 }
				    	 else if(finQtr == 3 || finQtr == 4)
				    	 {
				    		 dt = dimTime.getFinYear()+"-"+"04"+"-"+"01";
				    		 d=Date.valueOf(dt);
				    		 log.setData_Port_Schedule_Date(d);
						     log.setData_Port_Schedule_Time(t1);
				    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"10"+"-"+"01");
				    		 log.setData_From_Date(date);
				    		 int nextYrName = dimTime.getFinYear();
				    		 nextYrName =  nextYrName + 1;
				    		 date = Date.valueOf(nextYrName+"-"+"03"+"-"+"31");
				    		 log.setData_To_Date(date);
				    	 }
				    	 
				    	
				     }
				     if(freqId == 6)
				     {
				    	 //yearly
				    	 
				    	 String dt = dimTime.getFinYear()+"-"+"04"+"-"+"01";
			    		 d=Date.valueOf(dt);
			    		 log.setData_Port_Schedule_Date(d);
					     log.setData_Port_Schedule_Time(t1);
					     int nextYrName = dimTime.getFinYear();
			    		 nextYrName =  nextYrName - 1;
			    		 date = Date.valueOf(nextYrName+"-"+"10"+"-"+"01");
			    		 log.setData_From_Date(date);
			    		 date = Date.valueOf(dimTime.getFinYear()+"-"+"03"+"-"+"31");
			    		 log.setData_To_Date(date);
				     }
				     
				     
				     log.setStatus(1);
					 log.setFootprint_given(1);
					 log.setFootPrint_opted(1);
					 log.setData_Group_ID(1);
					 log.setDataStatus(1);
					 log.setEntry_by_User_Code(1);
					 currentDate = LocalDate.now();
				     formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				     sqlFormattedDate = currentDate.format(formatter);
				     date=Date.valueOf(sqlFormattedDate);
					 log.setEntryDate(date);
					 log.setModify_by_User_Code(1);
					 log.setModify_By_User_Type_Code(1);
					 
					 log.setModify_Date(date);
					 
				  }
				  catch (Exception e) 
				  {
					  e.printStackTrace();
					 
				  }
				//getMax Id
				  Long id = dataRepo.getMaxId();
				  display.setId(id.intValue());
				  java.sql.Date d = log.getData_From_Date();
				  SimpleDateFormat newFormater = new SimpleDateFormat("dd-MM-yyyy");
				  
				  display.setPrevMonthFirstDate(newFormater.format(d));
				  d = log.getData_To_Date();
				  display.setPrevMonthLastDate(newFormater.format(d));
				  LocalDate ld = LocalDate.parse(log.getData_To_Date().toString());
				  
				  Month monthName = ld.getMonth();
				  display.setMonthName(monthName.toString());
				  Dim_Time t1 = timeRepo.findByDdate(log.getData_From_Date());
				  display.setFinYear(t1.getFinYearFull());
				  display.setPortingFrequencyName(log.getData_Port_Description_Name());
				  
				  
				  int gId = projRepo.findByProjectCode(log.getProjectCode()).getProject_Data_Granularity_ID();
				  DataGranularityDetails d1 = granularityRepo.findByDataGranularityId(gId);
				  display.setGranularityName(d1.getData_Granularity_Name_e());
				  display.setScheduledOn(newFormater.format(log.getData_Port_Schedule_Date()));
				  
				  display.setPortedOn(newFormater.format(log.getEntryDate()));
				  
				  LocalDate localDate1 = log.getData_Port_Schedule_Date().toLocalDate();
				  LocalDate localDate2 = log.getEntryDate().toLocalDate();
			      long daysDifference = ChronoUnit.DAYS.between(localDate1, localDate2);
			      Tbl_Project_Detail_Intrim proj= projRepo.findByProjectCode(log.getProjectCode());

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
			      display.setStatus(status);
			      display.setProjectCode(log.getProjectCode());
			     
				  
			}
			else
			{
				flag = 0;
				
			}
		}
		logRepo.save(log);


		dispRepo.save(display);


		if(flag == 1)
		{
			return new ResponseEntity<>("Success", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("Fail", HttpStatus.OK);
		}
		
	}
	@PostMapping(value="recvApiDataBlank", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> recvApiDataBlank(@RequestBody  TableDataTemp table) throws JsonProcessingException, ParseException
	{


		String token = aes.convertToEntityAttribute(table.getToken()).toString();
		if(token.equals(tokenKey))
		{
			  Tbl_DataPort_KPI_PORTED_DATA data = new Tbl_DataPort_KPI_PORTED_DATA();
			  RecvApiDataLogsToDisplay display = new RecvApiDataLogsToDisplay();
			  int projectCode = Integer.parseInt(aes.convertToEntityAttribute(table.getProjectCode()).toString());
			  Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(projectCode);
			  LocalDate currentDate = LocalDate.now();
		      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		      String sqlFormattedDate = currentDate.format(formatter);
		      java.sql.Date date=Date.valueOf(sqlFormattedDate);
		     
			 
			  SimpleDateFormat newFormater = new SimpleDateFormat("dd-MM-yyyy");
			  //check data Frequency
			  int freqId = proj.getPortingFrequency();
		      Dim_Time dimTime = timeRepo.findByDdate(date);

			     if(freqId == 1)
			     {
			    	 //daily
			    	 display.setPrevMonthFirstDate(newFormater.format(date).toString());
					 display.setPrevMonthLastDate(newFormater.format(date).toString());
					 display.setScheduledOn(newFormater.format(date).toString());
				     
			     }
			     if(freqId == 2)
			     {
			    	 //weekly-get week 1st date and last date
			    	 String dt = dimTime.getFinYear()+"-"+"07"+"-"+"01";
		    		 Date d=Date.valueOf(dt);
		    		 
			    	 display.setScheduledOn(newFormater.format(dimTime.getFirstDateOfWeek()).toString());
					 final ZonedDateTime input = ZonedDateTime.now();
				     final ZonedDateTime startOfLastWeek = input.minusWeeks(1).with(DayOfWeek.MONDAY);
				     LocalDate localDate = startOfLastWeek.toLocalDate();
				     // Convert LocalDate to java.sql.Date
				     Date lastWkFirstDate = Date.valueOf(localDate);
				   	 final ZonedDateTime endOfLastWeek = startOfLastWeek.plusDays(6);
				   	 localDate = endOfLastWeek.toLocalDate();
				   	 
				   	 Date lastWkLastDate = Date.valueOf(localDate);
				   	 newFormater = new SimpleDateFormat("dd-MM-yyyy");
				   	
				   	 display.setPrevMonthFirstDate(newFormater.format(lastWkFirstDate).toString());
				   	 display.setPrevMonthLastDate(newFormater.format(lastWkFirstDate).toString());
				   	 
				   	
				    
			    	 
			     }
			     if(freqId == 3)
			     {
			    	 //monthly
			    	 display.setScheduledOn(newFormater.format(dimTime.getFirstDateOfMonth()).toString());
			    	 SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			    	 Calendar calendar = Calendar.getInstance();
				      calendar.add(Calendar.MONTH, -1);
				      int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				      calendar.set(Calendar.DAY_OF_MONTH, max);
				      display.setScheduledOn(newFormater.format(date).toString());
				     String prevMonthLastDate = format1.format(calendar.getTime());
				     String prevMonthFirstDate =  format1.format(toDate(LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth())));
				     
				     display.setScheduledOn(newFormater.format(date).toString());
				     date=Date.valueOf(prevMonthFirstDate);
				     display.setPrevMonthFirstDate(newFormater.format(date).toString());
				     date=Date.valueOf(prevMonthLastDate);
				     display.setPrevMonthLastDate(newFormater.format(date).toString());
				      
			     }
			     if(freqId == 4)
			     {
			    	//quarterly

				      int finQtr = dimTime.getQrter();
				    	 String dt = "";
				    	 if(finQtr == 1)
				    	 {
					    	 display.setScheduledOn(newFormater.format(dimTime.getFirstDateOfMonth()).toString());
					    	 dt = dimTime.getFinYear()+"-"+"07"+"-"+"01";
				    		
					    	 display.setScheduledOn(newFormater.format(Date.valueOf(dt)).toString());

				    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"04"+"-"+"01");
				    		 display.setPrevMonthFirstDate(newFormater.format(date).toString());
				    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"06"+"-"+"30");
				    		 display.setPrevMonthLastDate(newFormater.format(date).toString());
				    	 }
				    	 else if(finQtr == 2)
				    	 {
				    		 dt = dimTime.getFinYear()+"-"+"10"+"-"+"01";
					    	 display.setScheduledOn(newFormater.format(Date.valueOf(dt)).toString());

				    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"07"+"-"+"01");
				    		 display.setPrevMonthFirstDate(newFormater.format(date).toString());
				    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"09"+"-"+"30");
				    		 display.setPrevMonthLastDate(newFormater.format(date).toString());
				    	 }
				    	 else if(finQtr == 3)
				    	 {
				    		 int nextYrName = dimTime.getFinYear();
				    		 nextYrName =  nextYrName + 1;
				    		 dt = nextYrName+"-"+"01"+"-"+"01";
					    	 display.setScheduledOn(newFormater.format(Date.valueOf(dt)).toString());

				    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"10"+"-"+"01");
				    		 display.setPrevMonthFirstDate(newFormater.format(date).toString());
				    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"12"+"-"+"31");
				    		 display.setPrevMonthLastDate(newFormater.format(date).toString());
				    	 }
				    	 else
				    	 {
				    		 int nextYrName = dimTime.getFinYear();
				    		 nextYrName =  nextYrName + 1;
				    		 dt = nextYrName+"-"+"04"+"-"+"01";
					    	 display.setScheduledOn(newFormater.format(Date.valueOf(dt)).toString());

				    		 date=Date.valueOf(nextYrName+"-"+"01"+"-"+"01");
				    		 display.setPrevMonthFirstDate(newFormater.format(date).toString());
				    		 date=Date.valueOf(nextYrName+"-"+"03"+"-"+"31");
				    		 display.setPrevMonthLastDate(newFormater.format(date).toString());				    	 
				    	}
			    	
				   
			     }
			     if(freqId == 5)
			     {
			    	 //half yearly(Apr-Sept)(Oct-March) oct/apr
			    	 int finQtr = dimTime.getFinquarter();
			    	 String dt = "";
			    	 if(finQtr == 1 || finQtr == 2)
			    	 {
			    		 dt = dimTime.getFinYear()+"-"+"10"+"-"+"01";
			    		
				    	 display.setScheduledOn(newFormater.format(Date.valueOf(dt)).toString());

			    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"04"+"-"+"01");
			    		 display.setPrevMonthFirstDate(newFormater.format(date).toString());
			    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"09"+"-"+"30");
			    		 display.setPrevMonthLastDate(newFormater.format(date).toString());				    	 
			    	}
			    	
			    	 else if(finQtr == 3 || finQtr == 4)
			    	 {
			    		 dt = dimTime.getFinYear()+"-"+"04"+"-"+"01";
			    		 display.setScheduledOn(newFormater.format(Date.valueOf(dt)).toString());
			    		 
			    		 date=Date.valueOf(dimTime.getFinYear()+"-"+"10"+"-"+"01");
			    		 display.setPrevMonthFirstDate(newFormater.format(date).toString());
			    		 int nextYrName = dimTime.getFinYear();
			    		 nextYrName =  nextYrName + 1;
			    		 date = Date.valueOf(nextYrName+"-"+"03"+"-"+"31");
			    		 display.setPrevMonthLastDate(newFormater.format(date).toString());				    	 
			    	 }
			    	 
			    	
			     }
			     if(freqId == 6)
			     {
			    	 //yearly
			    	 
			    	 String dt = dimTime.getFinYear()+"-"+"04"+"-"+"01";
			    	 display.setScheduledOn(newFormater.format(Date.valueOf(dt)).toString());
				     int nextYrName = dimTime.getFinYear();
		    		 nextYrName =  nextYrName - 1;
		    		 date = Date.valueOf(nextYrName+"-"+"10"+"-"+"01");
		    		 display.setPrevMonthFirstDate(newFormater.format(date).toString());
		    		 date = Date.valueOf(dimTime.getFinYear()+"-"+"03"+"-"+"31");
		    		 display.setPrevMonthLastDate(newFormater.format(date).toString());				    	 
			     }


			  
			  LocalDate ld = LocalDate.parse(date.toString());
			  
			  Month monthName = ld.getMonth();
			  display.setMonthName(monthName.toString());
			  
			  Dim_Time t1 = timeRepo.findByDdate(date);
			  
			  display.setFinYear(t1.getFinYearFull());
			  int gId = projRepo.findByProjectCode(projectCode).getProject_Data_Granularity_ID();
			  DataGranularityDetails d1 = granularityRepo.findByDataGranularityId(gId);
			  display.setGranularityName(d1.getData_Granularity_Name_e());
			  display.setPortingFrequencyName(freqRepo.getDataFrequencyName(Long.valueOf(proj.getPortingFrequency())));
			  
			  
			 

		      String status;
		      status = "failure";
				
		      display.setStatus(status);
		      display.setProjectCode(projectCode);
		      dispRepo.save(display);
		}
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}
	private static java.util.Date parseTimestamp(String timestamp) 
	{
		
	    try 
	    {
	        return DATE_TIME_FORMAT.parse(timestamp);
	    } 
	    catch (ParseException e) 
	    {
	        throw new IllegalArgumentException(e);
	    }
	}
	@GetMapping("recvApidata")
	@ResponseBody
	public String Apidata(@ModelAttribute Object obj)
	{
		
		return "success";
	}
	
	@GetMapping("getLog")
	public String getLog()
	{
		
		LocalDate ld = LocalDate.parse("2023-06-30");
        Month monthName = ld.getMonth();
        
        
		Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);
       
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        String prevMonthLastDate = format1.format(calendar.getTime());
        String prevMonthFirstDate =  format1.format(toDate(LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth())));
        
        
        
        format1 = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = format1.format(calendar.getTime());
        Date d=Date.valueOf(todayDate);
        Dim_Time dimTime = timeRepo.findByDdate(d);
      
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalTime currentTime = LocalTime.now();
	      formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	      String sqlFormattedTime = currentTime.format(formatter);
	      Time t1 = Time.valueOf(sqlFormattedTime);
	      
	      int finQtr = dimTime.getQrter();
	    	 String dt = "";
	    	 if(finQtr == 1)
	    	 {
	    		 dt = dimTime.getFinYear()+"-"+"07"+"-"+"01";
	    		 //d=Date.valueOf(dt);
	    	 }
	    	 else if(finQtr == 2)
	    	 {
	    		 dt = dimTime.getFinYear()+"-"+"10"+"-"+"01";
	    		 d=Date.valueOf(dt);
	    	 }
	    	 else if(finQtr == 3)
	    	 {
	    		 dt = dimTime.getFinYear()+"-"+"01"+"-"+"01";
	    		 //d=Date.valueOf(dt);
	    	 }
	    	 else
	    	 {
	    		 dt = dimTime.getFinYear()+"-"+"04"+"-"+"01";
	    		// d=Date.valueOf(dt);
	    	 }
	    	
		return "";
	}
	 public static java.util.Date toDate(LocalDate date) {
	        Instant instant = date.atStartOfDay()
	                .atZone(ZoneId.systemDefault()).toInstant();
	        return Date.from(instant);
	    }

	    public static java.util.Date toDate(LocalDateTime date) {
	        Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
	        return Date.from(instant);
	    }
	
}
