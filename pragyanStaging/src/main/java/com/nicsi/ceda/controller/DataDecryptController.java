package com.nicsi.ceda.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
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
import java.util.Base64;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.batch.operations.JobRestartException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nicsi.ceda.model.DataGranularityDetails;
import com.nicsi.ceda.model.Dim_Time;
import com.nicsi.ceda.model.RecvApiDataLogs;
import com.nicsi.ceda.model.RecvApiDataLogsToDisplay;
import com.nicsi.ceda.model.Registration;
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
import com.nicsi.ceda.repository.RegistrationRepo;
import com.nicsi.ceda.repository.SectorRepo;
import com.nicsi.ceda.repository.TableDataTempRepo;
import com.nicsi.ceda.repository.Tbl_DataPort_KPI_PORTED_DATARepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.util.AesDecryptor;
import com.nicsi.ceda.util.AesEncryptor;
import com.nicsi.ceda.util.GlobalVariable;

@RestController
public class DataDecryptController 
{
	@Autowired
	private TableDataTempRepo tableDataRepo;
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
	@Autowired
	private RegistrationRepo regRepo;
	
	@Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
   
    
	@GetMapping("decryptData")
	@ResponseBody
    @CrossOrigin
	public int decryptData() throws UnsupportedEncodingException
	{
		int count = 1;
		List<TableDataTemp> data = tableDataRepo.findAll();//findByProjectCode(Integer.parseInt(projectCode));
		int size = data.size();
		System.out.println("Data Size = "+size);
		
		
		if(size  > 0)
		{
			RecvApiDataLogs log = new RecvApiDataLogs();
			
			//RecvApiDataLogsToDisplay display = new RecvApiDataLogsToDisplay();
			for(TableDataTemp t : data)
			{
				
				Registration reg = regRepo.findByTokenKey(t.getToken());
				//System.out.println("Reg = "+reg);
				String encryptionKey = reg.getEncryptionKey();
				//System.out.println("encryptionKey = "+encryptionKey);
				GlobalVariable.encryptionKey = reg.getEncryptionKey();
				Tbl_Project_Detail_Intrim projDet = projRepo.findByProjectCode(reg.getProjectCode());
				//System.out.println("projDet = "+projDet);
				if(projDet.getPortLanguageId() == 1)
				{
					//JAVA
					//System.out.println("java API");
					//Java User API
					//decrypt data
					 Tbl_DataPort_KPI_PORTED_DATA portedData = new Tbl_DataPort_KPI_PORTED_DATA();
					 //System.out.print(t.getToken());
					 //System.out.println(encryptionKey);
					 try
					 {
						 portedData.setData_Group_Id(Integer.parseInt(decryptJava(t.getGroupId(), encryptionKey).toString()));
						 portedData.setInstance_Id(Integer.parseInt(decryptJava(t.getInstanceCode().toString(), encryptionKey).toString()));
						 portedData.setIsComplete(1);
						
						 if(t.getKpiValue() != null)
						 { 
						 	portedData.setKpi_Data(Double.parseDouble(decryptJava(t.getKpiValue().toString(), encryptionKey).toString()));
							
						 }
						 else
						 {
							 portedData.setKpi_Data(null);
						 }
						 portedData.setKpiId(Integer.parseInt(decryptJava(t.getKpiId().toString(), encryptionKey).toString()));
						  
						 portedData.setLevel1Code(Integer.parseInt(decryptJava(t.getLevel1Code().toString(), encryptionKey).toString()));
						 if(t.getLevel2Code() != null)
						 {
							 portedData.setLevel2Code(Integer.parseInt(decryptJava(t.getLevel2Code().toString(), encryptionKey).toString()));
						 }
						 else
						 {
							 portedData.setLevel2Code(null);
						 }
						 if(t.getLevel3Code() != null)
						 {
							 portedData.setLevel3Code(Integer.parseInt(decryptJava(t.getLevel3Code().toString(), encryptionKey).toString()));
						 }
						 else 
						 {
							 portedData.setLevel3Code(null);
						 }
						 if(t.getLevel4Code() != null)
						 {
							 portedData.setLevel4Code(Integer.parseInt(decryptJava(t.getLevel4Code().toString(), encryptionKey).toString()));
						 }
						 else 
						 {
							 portedData.setLevel4Code(null);
						 }
						 if(t.getLevel5Code() != null)
						 {
							 portedData.setLevel5Code(Integer.parseInt(decryptJava(t.getLevel5Code().toString(), encryptionKey).toString()));
						 }
						 else 
						 {
							 portedData.setLevel5Code(null);
						 }
						 portedData.setLevel6Code(Integer.parseInt(decryptJava(t.getLevel6Code().toString(), encryptionKey).toString()));
						 portedData.setLevel7Code(Integer.parseInt(decryptJava(t.getLevel7Code().toString(), encryptionKey).toString()));
						 portedData.setLevel8Code(Integer.parseInt(decryptJava(t.getLevel8Code().toString(), encryptionKey).toString()));
						 portedData.setLevel9Code(Integer.parseInt(decryptJava(t.getLevel9Code().toString(), encryptionKey).toString()));
						 portedData.setLevel10Code(Integer.parseInt(decryptJava(t.getLevel10Code().toString(), encryptionKey).toString()));
						 portedData.setMigrated(0);
						 portedData.setMinistry_Code(Integer.parseInt(decryptJava(t.getMinistryCode().toString(), encryptionKey).toString()));
						 portedData.setDept_code(Integer.parseInt(decryptJava(t.getDepartmentCode().toString(), encryptionKey).toString()));
						 portedData.setMode_frequency_id(Integer.parseInt(decryptJava(t.getFrequencyId().toString(), encryptionKey).toString()));
						 portedData.setProjectCode(Long.parseLong(decryptJava(t.getProjectCode().toString(), encryptionKey).toString()));
						 portedData.setSec_code(Integer.parseInt(decryptJava(t.getSectorCode().toString(), encryptionKey).toString()));
						 portedData.setSimilarity(0.0);
						 
						 String dataDate = (decryptJava(t.getDataDate().toString(), encryptionKey)).toString();
						 String trimmedString = dataDate.substring(0, Math.min(dataDate.length(), 10));
					     String d = trimmedString  + " 00:00:00";
						 Timestamp ts = Timestamp.valueOf(d);
						 portedData.setDatadt(ts);
						 
						 String todate = (decryptJava(t.getToDurationOfData().toString(), encryptionKey)).toString();
						 ts = Timestamp.valueOf(todate);
						 todate = (decryptJava(t.getToDurationOfData().toString(), encryptionKey)).toString();
						 //getting current dat and time 
						 LocalDateTime currentDateTime = LocalDateTime.now();
					     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					     String formattedDateTime = currentDateTime.format(formatter);
					     Timestamp timestamp = Timestamp.valueOf(formattedDateTime);
					     // Get current date
					     LocalDate currentDate = LocalDate.now();
					     // Set time to midnight (00:00:00)
					     LocalTime midnight = LocalTime.MIDNIGHT;
					     // Combine date and time
					     LocalDateTime currentDateTimeMidnight = LocalDateTime.of(currentDate, midnight);
					     // Convert LocalDateTime to Timestamp
					     timestamp = Timestamp.valueOf(currentDateTimeMidnight);


					     portedData.setEntrydt(timestamp);
						 portedData.setAtmpt(0);
					 }
					 catch (Exception e) 
					 {
						 e.printStackTrace();
					 }
					  try
					  {
						 Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(portedData.getProjectCode().intValue());
						 portedData.setUsername(proj.getUsername());
						 System.out.println(portedData);
						 dataRepo.save(portedData);
						 tableDataRepo.deleteById(t.getId());
					 }
					 catch (Exception e) 
					 {
						 e.printStackTrace();
					 }
					 
				}
				else if(projDet.getPortLanguageId() == 2)
				{
					
					//ASP Dot Net API
					
					//decrypt data
					 Tbl_DataPort_KPI_PORTED_DATA portedData = new Tbl_DataPort_KPI_PORTED_DATA();
					 portedData.setData_Group_Id(Integer.parseInt(decryptASPData(t.getGroupId().toString(), encryptionKey)));
					 
					 portedData.setInstance_Id(Integer.parseInt(decryptASPData(t.getInstanceCode().toString(), encryptionKey))); 
					 portedData.setIsComplete(1);
					 if(t.getKpiValue() != null)
					 {
						 
						 portedData.setKpi_Data(Double.parseDouble(decryptASPData(t.getKpiValue().toString(), encryptionKey)));
					 }
					 else
					 {
						 portedData.setKpi_Data(null);
					 }
					
					 String result;
					 if(t.getKpiId() != null)
					 {
						 result = decryptASPData(t.getKpiId().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setKpiId(Integer.parseInt(result));
					 }
					 
					 
					 result = decryptASPData(t.getLevel1Code().toString(), encryptionKey).replaceAll("\\s", "");
					 portedData.setLevel1Code(Integer.parseInt(result));
					 if(t.getLevel2Code() != null)
					 {
						
						 result = decryptASPData(t.getLevel2Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel2Code(Integer.parseInt(result));
					 }
					 if(t.getLevel3Code() != null)
					 {
						 result = decryptASPData(t.getLevel3Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel3Code(Integer.parseInt(result));
					 }
					 if(t.getLevel4Code() != null)
					 {
						 result = decryptASPData(t.getLevel4Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel4Code(Integer.parseInt(result));
					 }
					 if(t.getLevel5Code() != null)
					 {
						 result = decryptASPData(t.getLevel5Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel5Code(Integer.parseInt(result));
					 }
					 
					 if(t.getLevel6Code() != null)
					 {
						 result = decryptASPData(t.getLevel6Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel6Code(Integer.parseInt(result));
					 }
					 if(t.getLevel7Code() != null)
					 {
						 result = decryptASPData(t.getLevel7Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel7Code(Integer.parseInt(result));
					 }
					 if(t.getLevel8Code() != null)
					 {
						 result = decryptASPData(t.getLevel8Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel8Code(Integer.parseInt(result));
					 }
					 if(t.getLevel9Code() != null)
					 {
						 result = decryptASPData(t.getLevel9Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel9Code(Integer.parseInt(result));
					 }
					 if(t.getLevel10Code() != null)
					 {
						 result = decryptASPData(t.getLevel10Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel10Code(Integer.parseInt(result));
					 }
					 
					 portedData.setMigrated(0);
					 result = decryptASPData(t.getMinistryCode().toString(), encryptionKey).replaceAll("\\s", "");
					 portedData.setMinistry_Code(Integer.parseInt(result));
					 result = decryptASPData(t.getDepartmentCode().toString(), encryptionKey).replaceAll("\\s", "");
					 portedData.setDept_code(Integer.parseInt(result));
					 result = decryptASPData(t.getFrequencyId().toString(), encryptionKey).replaceAll("\\s", "");
					 portedData.setMode_frequency_id(Integer.parseInt(result));
					 result = decryptASPData(t.getProjectCode().toString(), encryptionKey).replaceAll("\\s", "");
					 portedData.setProjectCode(Long.parseLong(result));
					 result = decryptASPData(t.getSectorCode().toString(), encryptionKey).replaceAll("\\s", "");
					 portedData.setSec_code(Integer.parseInt(result));
					 
					
					  String dataDate = decryptASPData(t.getDataDate().toString(), encryptionKey);
					  String trimmedString = dataDate.substring(0, Math.min(dataDate.length(), 10));
					  String d = trimmedString  + " 00:00:00";
					  Timestamp ts = Timestamp.valueOf(d);
					  
					  portedData.setDatadt(ts);
				
					  LocalDateTime currentDateTime = LocalDateTime.now();
					  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					  String formattedDateTime = currentDateTime.format(formatter);
					  Timestamp timestamp = Timestamp.valueOf(formattedDateTime);
					  portedData.setEntrydt(timestamp);
					 
					  portedData.setAtmpt(0);
					  
					  try
					  {
						  
						  Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(portedData.getProjectCode().intValue());
						  portedData.setUsername(proj.getUsername());
						  System.out.println(portedData);
						  dataRepo.save(portedData);
						  tableDataRepo.deleteById(t.getId());
					  }
					  catch (Exception e) 
					  {
						e.printStackTrace();
					}
				}
				else
				{
					//PHP
					//decrypt data
					 Tbl_DataPort_KPI_PORTED_DATA portedData = new Tbl_DataPort_KPI_PORTED_DATA();
					 portedData.setData_Group_Id(Integer.parseInt(decryptPHPData(t.getGroupId().toString(), encryptionKey)));
					 
					 portedData.setInstance_Id(Integer.parseInt(decryptPHPData(t.getInstanceCode().toString(), encryptionKey))); 
					 portedData.setIsComplete(1);
					 if(t.getKpiValue() != null)
					 {
						 
						 portedData.setKpi_Data(Double.parseDouble(decryptPHPData(t.getKpiValue().toString(), encryptionKey)));
					 }
					 else
					 {
						 portedData.setKpi_Data(null);
					 }
					
					 String result;
					 if(t.getKpiId() != null)
					 {
						 result = decryptPHPData(t.getKpiId().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setKpiId(Integer.parseInt(result));
					 }
					 
					 
					 result = decryptPHPData(t.getLevel1Code().toString(), encryptionKey).replaceAll("\\s", "");
					 portedData.setLevel1Code(Integer.parseInt(result));
					 if(t.getLevel2Code() != null)
					 {
						
						 result = decryptPHPData(t.getLevel2Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel2Code(Integer.parseInt(result));
					 }
					 if(t.getLevel3Code() != null)
					 {
						 result = decryptPHPData(t.getLevel3Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel3Code(Integer.parseInt(result));
					 }
					 if(t.getLevel4Code() != null)
					 {
						 result = decryptPHPData(t.getLevel4Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel4Code(Integer.parseInt(result));
					 }
					 if(t.getLevel5Code() != null)
					 {
						 result = decryptPHPData(t.getLevel5Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel5Code(Integer.parseInt(result));
					 }
					 
					 if(t.getLevel6Code() != null)
					 {
						 result = decryptPHPData(t.getLevel6Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel6Code(Integer.parseInt(result));
					 }
					 if(t.getLevel7Code() != null)
					 {
						 result = decryptPHPData(t.getLevel7Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel7Code(Integer.parseInt(result));
					 }
					 if(t.getLevel8Code() != null)
					 {
						 result = decryptPHPData(t.getLevel8Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel8Code(Integer.parseInt(result));
					 }
					 if(t.getLevel9Code() != null)
					 {
						 result = decryptPHPData(t.getLevel9Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel9Code(Integer.parseInt(result));
					 }
					 if(t.getLevel10Code() != null)
					 {
						 result = decryptPHPData(t.getLevel10Code().toString(), encryptionKey).replaceAll("\\s", "");
						 portedData.setLevel10Code(Integer.parseInt(result));
					 }
					 
					 portedData.setMigrated(0);
					 result = decryptPHPData(t.getMinistryCode().toString(), encryptionKey).replaceAll("\\s", "");
					 portedData.setMinistry_Code(Integer.parseInt(result));
					 result = decryptPHPData(t.getDepartmentCode().toString(), encryptionKey).replaceAll("\\s", "");
					 portedData.setDept_code(Integer.parseInt(result));
					 result = decryptPHPData(t.getFrequencyId().toString(), encryptionKey).replaceAll("\\s", "");
					 portedData.setMode_frequency_id(Integer.parseInt(result));
					 result = decryptPHPData(t.getProjectCode().toString(), encryptionKey).replaceAll("\\s", "");
					 portedData.setProjectCode(Long.parseLong(result));
					 result = decryptPHPData(t.getSectorCode().toString(), encryptionKey).replaceAll("\\s", "");
					 portedData.setSec_code(Integer.parseInt(result));
					 
					
					  String dataDate = decryptPHPData(t.getDataDate().toString(), encryptionKey);
					 
					  String trimmedString = dataDate.substring(0, Math.min(dataDate.length(), 10));
					  String d = trimmedString  + " 00:00:00";
					  Timestamp ts = Timestamp.valueOf(d);
					  portedData.setDatadt(ts);
				
					  LocalDateTime currentDateTime = LocalDateTime.now();
					  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					  String formattedDateTime = currentDateTime.format(formatter);
					  Timestamp timestamp = Timestamp.valueOf(formattedDateTime);
					  portedData.setEntrydt(timestamp);
					 
					  portedData.setAtmpt(0);
					  
					  try
					  {
						  
						  Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(portedData.getProjectCode().intValue());
						  portedData.setUsername(proj.getUsername());
						  System.out.println(portedData);
						  dataRepo.save(portedData);
						  tableDataRepo.deleteById(t.getId());
					  }
					  catch (Exception e) 
					  {
						 
						e.printStackTrace();
					  }
				}
					
				//check api language and call decryption method;
				
				
			}
			
		}
		
		return size;
	}
	
	
	public static String decryptJava(String encryptedText, String key) throws Exception 
	{
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        // Decrypt Data
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedText = new String(decryptedBytes);

    	 return decryptedText;
    }
	static String decryptASPData(String encryptedText, String key) throws UnsupportedEncodingException 
    {
    	
    	byte[] decryptedBytes = null;
    	try
    	{
	    	byte[] combinedBytes = Base64.getDecoder().decode(encryptedText);
	
	        byte[] iv = new byte[16];
	        System.arraycopy(combinedBytes, 0, iv, 0, 16);
	
	        byte[] encryptedBytes = new byte[combinedBytes.length - 16];
	        System.arraycopy(combinedBytes, 16, encryptedBytes, 0, encryptedBytes.length);
	
	        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
	
	       decryptedBytes = cipher.doFinal(encryptedBytes);
	       
    	}
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    	 return new String(decryptedBytes, "UTF-8");
    }
	static String decryptPHPData(String encryptedText, String key) throws UnsupportedEncodingException 
    {
    	
		byte[] decryptedBytes = null;
    	try
    	{
	    	byte[] combinedBytes = Base64.getDecoder().decode(encryptedText);
	
	        byte[] iv = new byte[16];
	        System.arraycopy(combinedBytes, 0, iv, 0, 16);
	
	        byte[] encryptedBytes = new byte[combinedBytes.length - 16];
	        System.arraycopy(combinedBytes, 16, encryptedBytes, 0, encryptedBytes.length);
	
	        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
	
	       decryptedBytes = cipher.doFinal(encryptedBytes);
	       
    	}
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    	//System.out.println("decryptedBytes = "+new String(decryptedBytes, "UTF-8"));
    	 return new String(decryptedBytes, "UTF-8");
    }
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("decryptAllUserData")
	public String decryptAllUserData()
	{
		List<TableDataTemp> data = tableDataRepo.findAll();
		for(TableDataTemp d : data)
		{
			//decrypt data
			
			//delete after decrypt
			tableDataRepo.deleteById(d.getId());
		}
		return "";
	}
	@GetMapping("decrypt")
	public void decrypt(@RequestParam("projectCode")int projectCode) throws org.springframework.batch.core.repository.JobRestartException
	{
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
	}
	@Autowired
	private AesDecryptor dec;
	@GetMapping("decryptJavaAPIData")
	public String decryptJavaAPIData() throws Exception
	{
		String data = dec.decryptJavaAPIData("MdournW+3C8bvBCBROpR3g==", "097bf83dc59b4d8a85f4674b9ea1cb7e");
		//String data = dec.decryptDotNetAPIData("MdournW+3C8bvBCBROpR3g==", "097bf83dc59b4d8a85f4674b9ea1cb7e");
		return data;
	}
	@GetMapping("decryptDotNetAPIData")
	public String decryptDotNetAPIData() throws Exception
	{
		
		String data = dec.decryptDotNetAPIData("YliWyYovHGrZCIqar8Vs74AATfIVOprDo4poMoiYBVE=", "097bf83dc59b4d8a85f4674b9ea1cb7e");
		return data;
	}
	
	
	public static java.util.Date toDate(LocalDate date) 
	{
        Instant instant = date.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static java.util.Date toDate(LocalDateTime date) 
    {
        Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

}
