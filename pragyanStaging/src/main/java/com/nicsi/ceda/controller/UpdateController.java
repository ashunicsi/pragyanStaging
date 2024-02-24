package com.nicsi.ceda.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nicsi.ceda.model.DataGranularityDetails;
import com.nicsi.ceda.model.KPIDetailsDTO;
import com.nicsi.ceda.model.ListOfKpiForReport;
import com.nicsi.ceda.model.ListOfSchemeForReport;
import com.nicsi.ceda.model.ListOfUpdatedKPI;
import com.nicsi.ceda.model.ListOfUpdatedScheme;
import com.nicsi.ceda.model.MUnit;
import com.nicsi.ceda.model.PortingLanguage;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.model.Tbl_Project_KPI_Detail_Intrim;
import com.nicsi.ceda.repository.CategoryRepo;
import com.nicsi.ceda.repository.DataCalendarTypeRepo;
import com.nicsi.ceda.repository.DataFrequencyRepo;
import com.nicsi.ceda.repository.DataGranularityDetailsRepo;
import com.nicsi.ceda.repository.DataPortingModeRepo;
import com.nicsi.ceda.repository.ListOfKpiForReportRepo;
import com.nicsi.ceda.repository.ListOfSchemeForReportRepo;
import com.nicsi.ceda.repository.ListOfUpdatedKPIRepo;
import com.nicsi.ceda.repository.ListOfUpdatedSchemeRepo;
import com.nicsi.ceda.repository.MLevelRepo;
import com.nicsi.ceda.repository.MUnitRepo;
import com.nicsi.ceda.repository.PortingLanguageRepo;
import com.nicsi.ceda.repository.TblDataPortKPIPORTEDDATADeletedDataRepo;
import com.nicsi.ceda.repository.Tbl_DataPort_KPI_PORTED_DATARepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.repository.Tbl_Project_KPI_Detail_IntrimRepo;
import com.nicsi.ceda.util.GetIP;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class UpdateController 
{
	@Autowired(required = true)
	private Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
    private Environment environment;
	@Autowired
	private DataGranularityDetailsRepo granRepo;
	@Autowired
	private GetIP ip;
	@Autowired
	private Tbl_DataPort_KPI_PORTED_DATARepo portedDataRepo;
	@Autowired
	private TblDataPortKPIPORTEDDATADeletedDataRepo deleteRepo;
	@Autowired
	private Tbl_Project_KPI_Detail_IntrimRepo kpiRepo;
	@Autowired
	private MLevelRepo levelRepo;
	@Autowired
	private MUnitRepo unitRepo;
	@Autowired
	private CategoryRepo catRepo;
	@Autowired
	private DataGranularityDetailsRepo granularityRepo;
	@Autowired
	private ListOfSchemeForReportRepo schemeReportRepo;
	@Autowired
	private ListOfKpiForReportRepo kpiReportRepo;
	@Autowired
	private DataPortingModeRepo portingModeRepo;
	@Autowired
	private DataFrequencyRepo dataFreqRepo;
	@Autowired
	private DataCalendarTypeRepo dataRepo;
	
	@Autowired
	private ListOfUpdatedSchemeRepo updatedSchemeRepo;
	@Autowired
	private ListOfUpdatedKPIRepo updatedKpiRepo;
	
	@Autowired
	private PortingLanguageRepo langRepo;
	
	
	@GetMapping("isValidUserToUpdate")
	@ResponseBody
    @CrossOrigin
	public String isValidUserToUpdate(@RequestParam("schemeCode") Integer projectCode, @RequestParam("username") String username)
	{
		Tbl_Project_Detail_Intrim proj =  projRepo.findByUsernameAndProjectCodeAndStatus(username, projectCode, 1);
		String status = "";
		if(proj == null)
		{
			status = "fail";
		}
		else
		{
			status = "success";
		}
		
		return status;
	}
	@GetMapping("getSchemeDetailsForUpdate")
	@ResponseBody
    @CrossOrigin
	public String getSchemeDetailsForUpdate(@RequestParam("schemeCode") int projectCode, @RequestParam("username") String username)
	{
		Tbl_Project_Detail_Intrim proj = projRepo.findByUsernameAndProjectCode(username, projectCode);
		
		DataGranularityDetails data = granularityRepo.findByDataGranularityId(proj.getProject_Data_Granularity_ID());
		String modeName = portingModeRepo.getDataPortingModeName(new Long(proj.getPortMode()));
		String freqName = dataFreqRepo.getDataFrequencyName(new Long(proj.getPortingFrequency()));
		String calenderType =  dataRepo.getCalenderTypeName(proj.getCalendarType());
		
		JSONObject jsonobj = new JSONObject();
		
		jsonobj.put("projectName", proj.getProject_name_e());
		jsonobj.put("projCordName", proj.getProject_cord_name_e());
		jsonobj.put("projCordDesignation", proj.getProjectCordDesignation());
		jsonobj.put("projCordEmail", proj.getProject_cord_email().replace("[AT]","@").replace("[DOT]","."));
		jsonobj.put("projCordMobile", proj.getProject_Cord_MobNo());
		jsonobj.put("projHeadName", proj.getProject_head_name_e());
		jsonobj.put("projHeadDesignation", proj.getProjectHeadDesignation());
		jsonobj.put("projHeadEmail", proj.getProject_head_email().replace("[AT]","@").replace("[DOT]","."));
		jsonobj.put("projHeadMobile", proj.getProject_Head_MobNo());
		jsonobj.put("projAdminName", proj.getProject_Admin_Name());
		jsonobj.put("projAdminDesignation", proj.getProjectAdminDesignation());
		jsonobj.put("projAdminEmail", proj.getProject_Admin_Email().replace("[AT]","@").replace("[DOT]","."));
		jsonobj.put("projAdminMobile", proj.getProject_Admin_MobNo());
		jsonobj.put("projectTooltip", proj.getProject_Tooltip_E());
		jsonobj.put("projectDesc", proj.getProject_Description_E());
		jsonobj.put("projectDataLevel", proj.getProject_Data_Lvl_Code());
		//read only in UI
		jsonobj.put("projLaunchDate", proj.getProject_Launch_Date());
		
		jsonobj.put("dataGranularityId", proj.getProject_Data_Granularity_ID());
		jsonobj.put("dataGranularityName", data.getData_Granularity_Name_e());
		jsonobj.put("logoName", proj.getSchemeLogoName());
		jsonobj.put("calendarType", proj.getCalendarType());
		jsonobj.put("calendarTypeName", calenderType);
		jsonobj.put("portingFrequency", proj.getPortingFrequency());
		jsonobj.put("portingFrequencyName", freqName);
		jsonobj.put("portMode", proj.getPortMode());
		jsonobj.put("portModeName", modeName);
		//read only in UI
		jsonobj.put("startdate", proj.getStartDate());
		
		jsonobj.put("waitingDays", proj.getWaitingDays());
		jsonobj.put("sendEmail", proj.getSendEmail());
		jsonobj.put("recpEmail", proj.getRecpEmail().replace("[AT]","@").replace("[DOT]","."));
		jsonobj.put("projectCode", proj.getProjectCode());
		jsonobj.put("username", proj.getUsername());
		jsonobj.put("portLanguageId", proj.getPortLanguageId());
		PortingLanguage lang =  langRepo.findByPortLanguageId(proj.getPortLanguageId());
		jsonobj.put("languageName", lang.getLanguageName());
		return jsonobj.toString();
	}
	
	@PostMapping(value ="updateScheme", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
    @CrossOrigin
	public String updateScheme(@RequestParam("sectorCode") int sectorCode, @RequestParam("file") MultipartFile schemeLogo, 
							@RequestParam("ministryCode") int ministryCode, @RequestParam("deptCode") int deptCode,
							@RequestParam("projectCordEmail") String projectCordEmail,	@RequestParam("projectCordMobNo") String projectCordMobNo ,	
							@RequestParam("projectCordNameE") String projectCordNameE,	@RequestParam("projectCordDesignation") String projectCordDesignation,
							@RequestParam("projectHeadEmail") String projectHeadEmail,	@RequestParam("projectHeadMobNo") String projectHeadMobNo,	
							@RequestParam("projectHeadNameE") String projectHeadNameE, @RequestParam("projectHeadDesignation") String projectHeadDesignation,
							@RequestParam("projectAdminEmail") String projectAdminEmail,@RequestParam("projectAdminPhone") String projectAdminPhone,	
							@RequestParam("projectAdminName") String projectAdminName, @RequestParam("projectAdminDesignation") String projectAdminDesignation,
							@RequestParam("projectTooltipE") String projectTooltipE,	@RequestParam("projectDescriptionE") String projectDescriptionE,	
							@RequestParam("schemeName") String schemeName, @RequestParam("projectDataGranularityID") int projectDataGranularityID,
							@RequestParam("projectLaunchDate")  String projectLaunchDate, @RequestParam("projectCategoryId") int projectCategoryId,
							
							@RequestParam("calendarType") int calendarType, @RequestParam("portingFrequency") int portingFrequency,
							@RequestParam("portMode") int portMode, @RequestParam("sendEmail") String sendEmail,
							@RequestParam("startDate") String startDate, @RequestParam("waitingDays") int waitingDays, @RequestParam("recpEmail") String recpEmail,
							@RequestParam("username") String username, @RequestParam("schemeCode") int schemeCode, @RequestParam("portLanguageId") int portLanguageId,
						
							HttpServletRequest request) throws ParseException, IOException
	{
		String schemeLogoName = null;
		if(schemeLogo.getSize() != 0)
		{
			//save file and set fileName;
			byte [] content = schemeLogo.getBytes();
			schemeLogoName = ZonedDateTime.now().toInstant().toEpochMilli()+"_"+schemeLogo.getOriginalFilename();
			String pathName = environment.getProperty("schemeLogo")+schemeLogoName;
			File f = new File(pathName);
			FileCopyUtils.copy(content, f);
			BufferedImage image = ImageIO.read(f);
	        BufferedImage resized = resize(image, 200, 250);
			File output = new File(pathName);
	        ImageIO.write(resized, "jpg", output);
	        
		}
		else
		{
			
		}
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(schemeCode);
		String status = null;
		//startDate, projectLaunchDate
		ListOfSchemeForReport report = schemeReportRepo.findBySchemeCode(schemeCode);
		
		LocalDateTime dateTime = LocalDateTime.now();
		
		proj.setSchemeLogoName(schemeLogoName);
		proj.setEntry_Date(null);
		//proj.setProject_Launch_Date(projectLaunchDate);
		proj.setSec_code(sectorCode);
		proj.setMinistry_code(ministryCode);
		proj.setDept_code(deptCode);
		
		
		//schemType
		//schemeName
		String email = projectCordEmail.replace("@","[AT]");
		email = email.replace(".","[DOT]");
		
		proj.setProject_cord_email(email);
		proj.setProject_Cord_MobNo(projectCordMobNo);
		proj.setProject_cord_name_e(projectCordNameE);
		proj.setProjectCordDesignation(projectCordDesignation);

		email = projectHeadEmail.replace("@","[AT]");
		email = email.replace(".","[DOT]");
		
		proj.setProject_head_email(email);
		proj.setProject_Head_MobNo(projectHeadMobNo);
		proj.setProject_head_name_e(projectHeadNameE);
		proj.setProjectHeadDesignation(projectHeadDesignation);
		
		email = projectAdminEmail.replace("@","[AT]");
		email = email.replace(".","[DOT]");
		
		proj.setProject_Admin_Email(email);
		proj.setProject_Admin_MobNo(projectAdminPhone);
		proj.setProject_Admin_Name(projectAdminName);
		proj.setProjectAdminDesignation(projectAdminDesignation);
		
		proj.setProject_Tooltip_E(projectTooltipE);
		proj.setProject_Tooltip_R(projectTooltipE);
		proj.setProject_Description_E(projectDescriptionE);
		proj.setProject_Description_R(projectDescriptionE);
		proj.setProject_name_e(schemeName);
		proj.setProject_Name_R(schemeName);
		
		java.sql.Date startD = Date.valueOf(startDate);
		proj.setStartDate(startD);
		java.sql.Date launchD = Date.valueOf(projectLaunchDate);
		
		proj.setProject_Launch_Date(launchD);
		 
		
		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		proj.setEntry_Date(new Timestamp(timeStamp.getTime()));
		//date = inputFormat.parse(startDate);
		
		if(projectDataGranularityID != proj.getProject_Data_Granularity_ID())
		{
			ListOfUpdatedScheme data = new ListOfUpdatedScheme();
			data.setProjectCode(proj.getProjectCode());
			data.setIsDataLevelChanged(1);
			data.setUpdatedDate(new Timestamp(timeStamp.getTime()));
			data.setStatus(0);
			data.setReasion("Data Penetration Level has changed");
			updatedSchemeRepo.save(data);
			
		}
		proj.setProject_Data_Granularity_ID(projectDataGranularityID);
		DataGranularityDetails data =  granRepo.findById(projectDataGranularityID);
		proj.setProject_Data_Lvl_Code(data.getMax_Data_Level());
		proj.setCalendarType(calendarType);
		proj.setPortingFrequency(portingFrequency);
		proj.setPortMode(portMode);
		proj.setSendEmail(sendEmail);
		proj.setWaitingDays(waitingDays);
		email = recpEmail.replace("@","[AT]");
		email = email.replace(".","[DOT]");
		proj.setRecpEmail(email);
		
	
		//Changed when project will be approve
		
		proj.setUser_Sys_IP(ip.getIPAddress(request));
		proj.setUsername(username);
		proj.setMajor_Proj_Code(new Long(schemeCode));
		java.util.Date lastUpdateDate = new java.util.Date();
		timeStamp = new Date(lastUpdateDate.getTime());
		proj.setLastUpdateDate(new Timestamp(timeStamp.getTime()));
		proj.setPortLanguageId(portLanguageId);
		try
		{
			projRepo.save(proj);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		report.setAdminEmail(proj.getProject_Admin_Email());
		report.setCordEmail(proj.getProject_cord_email());
		report.setHeadEmail(proj.getProject_head_email());
		report.setDepartmentCode(proj.getDept_code());
		report.setDepartmentName(levelRepo.getDepartmentName(proj.getDept_code(), 21));
		report.setMinistryCode(proj.getMinistry_code());
		report.setMinistryName(levelRepo.getMinistryName(proj.getMinistry_code(), 22));
		report.setSchemeCode(proj.getProjectCode());
		report.setSchemeName(proj.getProject_name_e());
		report.setStatus(proj.getStatus());
		schemeReportRepo.save(report);
		
		status = "success";
		
	
	
	return status;
	}
	@PostMapping(value ="updateSchemeWithoutLogo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
    @CrossOrigin
	public String updateScheme(@RequestParam("sectorCode") int sectorCode,
							@RequestParam("ministryCode") int ministryCode, @RequestParam("deptCode") int deptCode,
							@RequestParam("projectCordEmail") String projectCordEmail,	@RequestParam("projectCordMobNo") String projectCordMobNo ,	
							@RequestParam("projectCordNameE") String projectCordNameE,	@RequestParam("projectCordDesignation") String projectCordDesignation,
							@RequestParam("projectHeadEmail") String projectHeadEmail,	@RequestParam("projectHeadMobNo") String projectHeadMobNo,	
							@RequestParam("projectHeadNameE") String projectHeadNameE,  @RequestParam("projectHeadDesignation") String projectHeadDesignation,
							@RequestParam("projectAdminEmail") String projectAdminEmail,@RequestParam("projectAdminPhone") String projectAdminPhone,	
							@RequestParam("projectAdminName") String projectAdminName,  @RequestParam("projectAdminDesignation") String projectAdminDesignation,
							@RequestParam("projectTooltipE") String projectTooltipE,	@RequestParam("projectDescriptionE") String projectDescriptionE,	
							@RequestParam("schemeName") String schemeName, @RequestParam("projectDataGranularityID") int projectDataGranularityID,
							@RequestParam("projectLaunchDate")  String projectLaunchDate, @RequestParam("projectCategoryId") int projectCategoryId,
							
							@RequestParam("calendarType") int calendarType, @RequestParam("portingFrequency") int portingFrequency,
							@RequestParam("portMode") int portMode, @RequestParam("sendEmail") String sendEmail,
							@RequestParam("startDate") String startDate, @RequestParam("waitingDays") int waitingDays, @RequestParam("recpEmail") String recpEmail,
							@RequestParam("username") String username, @RequestParam("schemeCode") int schemeCode, @RequestParam("portLanguageId") int portLanguageId,
						
							HttpServletRequest request) throws ParseException, IOException
	{
		System.out.println(projectLaunchDate);
		System.out.println(startDate);
		System.out.println("Scheme Code = "+schemeCode);
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(schemeCode);
		System.out.println("Proj Details = "+proj);
		String status = null;
		String schemeLogoName = null;
		//startDate, projectLaunchDate
	
		LocalDateTime dateTime = LocalDateTime.now();
		
		
		//proj.setProject_Launch_Date(projectLaunchDate);
		proj.setSec_code(sectorCode);
		proj.setMinistry_code(ministryCode);
		proj.setDept_code(deptCode);
		
		
		//schemType
		//schemeName
		String email = projectCordEmail.replace("@","[AT]");
		email = email.replace(".","[DOT]");
		
		proj.setProject_cord_email(email);
		proj.setProject_Cord_MobNo(projectCordMobNo);
		proj.setProject_cord_name_e(projectCordNameE);
		proj.setProjectCordDesignation(projectCordDesignation);

		email = projectHeadEmail.replace("@","[AT]");
		email = email.replace(".","[DOT]");
		
		proj.setProject_head_email(email);
		proj.setProject_Head_MobNo(projectHeadMobNo);
		proj.setProject_head_name_e(projectHeadNameE);
		proj.setProjectHeadDesignation(projectHeadDesignation);
		
		email = projectAdminEmail.replace("@","[AT]");
		email = email.replace(".","[DOT]");
		
		proj.setProject_Admin_Email(email);
		proj.setProject_Admin_MobNo(projectAdminPhone);
		proj.setProject_Admin_Name(projectAdminName);
		proj.setProjectAdminDesignation(projectAdminDesignation);
		
		proj.setProject_Tooltip_E(projectTooltipE);
		proj.setProject_Tooltip_R(projectTooltipE);
		proj.setProject_Description_E(projectDescriptionE);
		proj.setProject_Description_R(projectDescriptionE);
		proj.setProject_name_e(schemeName);
		proj.setProject_Name_R(schemeName);
		try
		{
			java.sql.Date startD = Date.valueOf(startDate);
			proj.setStartDate(startD);
		}
		catch (Exception e) 
		{
			SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (z)", Locale.ENGLISH);
	        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
	        try 
	        {
	        	java.util.Date date = inputFormat.parse(startDate);
	            String formattedDate = outputFormat.format(date);
	            java.sql.Date sqlDate = java.sql.Date.valueOf(formattedDate);
	            
	            proj.setStartDate(sqlDate);
	        } 
	        catch (Exception e1) 
	        {
	            e1.printStackTrace();
	        }
			e.printStackTrace();
		}
		try
		{
			java.sql.Date launchD = Date.valueOf(projectLaunchDate);
			proj.setProject_Launch_Date(launchD);
		}
		catch (Exception e) 
		{
			SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (z)", Locale.ENGLISH);
	        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
	        try 
	        {
	        	java.util.Date date = inputFormat.parse(projectLaunchDate);
	            String formattedDate = outputFormat.format(date);
	            java.sql.Date sqlDate = java.sql.Date.valueOf(formattedDate);
	            proj.setProject_Launch_Date(sqlDate);
	        } 
	        catch (Exception e1) 
	        {
	            e1.printStackTrace();
	        }
			e.printStackTrace();
		}
		java.util.Date newdate = new java.util.Date();
		Date timeStamp = new Date(newdate.getTime());
		

		if(projectDataGranularityID != proj.getProject_Data_Granularity_ID())
		{
			ListOfUpdatedScheme data = new ListOfUpdatedScheme();
			data.setProjectCode(proj.getProjectCode());
			data.setIsDataLevelChanged(1);
			data.setUpdatedDate(new Timestamp(timeStamp.getTime()));
			data.setStatus(0);
			data.setReasion("Data Penetration Level has changed");
			updatedSchemeRepo.save(data);
			
			
		}
		proj.setProject_Data_Granularity_ID(projectDataGranularityID);
		DataGranularityDetails data =  granRepo.findById(projectDataGranularityID);
		proj.setProject_Data_Lvl_Code(data.getMax_Data_Level());
		proj.setCalendarType(calendarType);
		proj.setPortingFrequency(portingFrequency);
		proj.setPortMode(portMode);
		proj.setSendEmail(sendEmail);
		proj.setWaitingDays(waitingDays);
		email = recpEmail.replace("@","[AT]");
		email = email.replace(".","[DOT]");
		proj.setRecpEmail(email);
		
	
		//Changed when project will be approve
		
		proj.setUser_Sys_IP(ip.getIPAddress(request));
		proj.setUsername(username);
		proj.setMajor_Proj_Code(new Long(schemeCode));
		java.util.Date lastUpdateDate = new java.util.Date();
		timeStamp = new Date(lastUpdateDate.getTime());
		proj.setLastUpdateDate(new Timestamp(timeStamp.getTime()));
		proj.setPortLanguageId(portLanguageId);
		try
		{
			
			projRepo.save(proj);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		status = "success";
		ListOfSchemeForReport report = schemeReportRepo.findBySchemeCode(schemeCode);
		report.setAdminEmail(proj.getProject_Admin_Email());
		report.setCordEmail(proj.getProject_cord_email());
		report.setHeadEmail(proj.getProject_head_email());
		report.setDepartmentCode(proj.getDept_code());
		report.setDepartmentName(levelRepo.getDepartmentName(proj.getDept_code(), 21));
		report.setMinistryCode(proj.getMinistry_code());
		report.setMinistryName(levelRepo.getMinistryName(proj.getMinistry_code(), 22));
		report.setSchemeCode(proj.getProjectCode());
		report.setSchemeName(proj.getProject_name_e());
		report.setStatus(proj.getStatus());
		schemeReportRepo.save(report);
	
	return status;
	}
	@GetMapping("isValidUserToUpdateKpi")
	@ResponseBody
    @CrossOrigin
	public String isValidUserToUpdateKpi(@RequestParam("kpiId") Integer kpiId, @RequestParam("username") String username, @RequestParam("schemeCode") int projectCode)
	{
		Tbl_Project_KPI_Detail_Intrim kpi =  kpiRepo.findByKpiIdAndUsernameAndProjectCode(kpiId, username, projectCode);
		String status = "";
		if(kpi == null)
		{
			status = "fail";
		}
		else
		{
			status = "success";
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("status", status);
		return jsonobj.toString();
	}
	@GetMapping("getKpiForUpdate")
	@ResponseBody
    @CrossOrigin
	public String getKpiForUpdate(@RequestParam("kpiId") int kpiId, @RequestParam("username") String username, @RequestParam("schemeCode") int projectCode)
	{
		Tbl_Project_KPI_Detail_Intrim kpi =  kpiRepo.findByKpiIdAndUsernameAndProjectCode(kpiId, username, projectCode);
		String initializationName = "";
		if(kpi.getIs_datareset()== 0) 
		{
			initializationName = "Yes";
		}
		else
		{
			initializationName = "No";
		}
		JSONObject jsonobj = new JSONObject();
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(kpi.getProjectCode());
		jsonobj.put("schemeName", proj.getProject_name_e());
		jsonobj.put("kpiName", kpi.getKpi_name_e());
		jsonobj.put("categoryId", kpi.getKpi_category_id());
		jsonobj.put("categoryName", kpi.getKpi_category_name_e());
		jsonobj.put("dataValueId", kpi.getKpi_data_type_id());
		jsonobj.put("dataDisplayName", kpi.getKpi_data_display_type_name());
		jsonobj.put("initialization", kpi.getIs_datareset());
		jsonobj.put("initializationName", initializationName);
		jsonobj.put("unitName", kpi.getKpi_unit_name_e());
		jsonobj.put("unitId", kpi.getKpi_Unit_id());
		jsonobj.put("tooltip", kpi.getKpiTooltipE());
		jsonobj.put("allowNull", kpi.getAllowNull());
		jsonobj.put("ruleTypeId", kpi.getRuleTypeId());
		jsonobj.put("operatorId", kpi.getOperatorId());
		return jsonobj.toString();
	}
	@PostMapping(value="updateKPIDetails", consumes = "application/json", produces = "application/json" )
	@ResponseBody
    @CrossOrigin
	public String updateKpiDetails(@RequestBody KPIDetailsDTO dto, HttpServletRequest request)
	{
		//System.out.println("dto = "+dto);
		String status = "";
		if(dto.getUnitId() == 0 || dto.getCategoryId() == 0 || dto.getDataValueId() == 0 || dto.getInitialization() == "" || dto.getKpiName() == "" || 
				dto.getTooltip() == "" || dto.getAllowNull() == "" )
		{
				status = "Please fill all details...";
				
		}
		
		if(!status.equals("Please fill all details..."))
		{
			java.util.Date newdate = new java.util.Date();
			Date timeStamp = new Date(newdate.getTime());
			
			
			
			String kpi_data_display_type_name = null;
			if(dto.getDataValueId() == 1)
			{
				kpi_data_display_type_name = "Cummulative";
			}
			else
			{
				kpi_data_display_type_name = "Current";
			}

			Tbl_Project_KPI_Detail_Intrim tbl = new Tbl_Project_KPI_Detail_Intrim();
			tbl = kpiRepo.findByKpiIdAndProjectCode(dto.getKpiId(), dto.getSchemeCode());
			
			if(tbl.getKpi_data_display_type_id() != dto.getDataValueId())
			{
				//clean pm_dashboard_new
				//update kpiPorted
				ListOfUpdatedKPI data = new ListOfUpdatedKPI();
				data.setProjectCode(dto.getSchemeCode());
				data.setKpiId(dto.getKpiId());
				java.util.Date lastUpdateDate = new java.util.Date();
				timeStamp = new Date(lastUpdateDate.getTime());
				data.setUpdatedDate(new Timestamp(timeStamp.getTime()));
				data.setStatus(0);
				data.setReasion("Data ValueType / Data Display Type has changed");

				updatedKpiRepo.save(data);
				

			}
			if(dto.getInitialization().equals("0"))
			{
				tbl.setIs_datareset(0);
			}
			else
			{
				tbl.setIs_datareset(1);
			}
			tbl.setKpi_data_display_type_id(dto.getDataValueId());

			
			tbl.setLastUpdate(new Timestamp(timeStamp.getTime()));

			tbl.setKpi_category_id(dto.getCategoryId());



			tbl.setKpi_data_display_type_id(dto.getDataValueId());
			tbl.setKpi_data_display_type_name(kpi_data_display_type_name);

			tbl.setKpi_data_type_Name(kpi_data_display_type_name);
			tbl.setKpi_data_type_id(dto.getDataValueId());

			tbl.setProjectCode(dto.getSchemeCode());
			tbl.setKpiTooltipE(dto.getTooltip());
			tbl.setKpiTooltipR(dto.getTooltip());

			tbl.setStatus(1);
			tbl.setKpi_name_e(dto.getKpiName());
			tbl.setKpi_name_r(dto.getKpiName());


			//Get Unit Name from m_unit table
			MUnit u = unitRepo.findByUnitId(dto.getUnitId());
			tbl.setKpi_Unit_id(dto.getUnitId());
			tbl.setKpi_unit_name_e(u.getUnit_name_e());
			tbl.setKpi_unit_name_r(u.getUnit_name_e());
			//getInitialization
			//get Sector code from reg table




			tbl.setIs_updated(1);
			tbl.setUser_sys_ip(ip.getIPAddress(request));
			tbl.setKpi_category_name_e(catRepo.findCategoryName(Long.valueOf(dto.getCategoryId())));
			tbl.setKpi_category_name_r(catRepo.findCategoryName(Long.valueOf(dto.getCategoryId())));

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			//tbl.setEntry_date(cal);

			tbl.setRuleTypeId(dto.getRuleTypeId());
			tbl.setOperatorId(dto.getOperatorId());
			tbl.setAllowNull(dto.getAllowNull());
			tbl.setUsername(dto.getUsername());
			
			Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(dto.getSchemeCode());
			tbl.setUsername(proj.getUsername());
			
			ListOfKpiForReport kpi = kpiReportRepo.findByKpiIdAndSchemeId(dto.getKpiId(), dto.getSchemeCode());
			//reg kpi is not available in report table then NPE
			kpi.setKpiName(tbl.getKpi_name_e());
			kpi.setToolTip(tbl.getKpiTooltipE());
			
			kpiReportRepo.save(kpi);
			kpiRepo.save(tbl);

			status = "success";
		}
		return status;
	}
	
	@GetMapping("inactiveScheme")
	@ResponseBody
	public String inactiveScheme(@RequestParam("schemeCode") int projectCode, @RequestParam("username") String username)
	{
		String status = "";
		Tbl_Project_Detail_Intrim proj = projRepo.findByUsernameAndProjectCodeAndStatus(username, projectCode, 1);
		if(proj == null)
		{
			status = "Invalid request...";
		}
		else
		{
			proj.setStatus(0);
			proj.setProject_Approval_Status(0);
			java.util.Date newdate = new java.util.Date();
			Date timeStamp = new Date(newdate.getTime());
			proj.setLastUpdateDate(new Timestamp(timeStamp.getTime()));
			projRepo.save(proj);
			status = "Scheme Inactivated Successfully...";
		}
		return status;
	}
	@GetMapping("activeScheme")
	@ResponseBody
	public String activeScheme(@RequestParam("schemeCode") int projectCode, @RequestParam("username") String username)
	{
		String status = "";
		Tbl_Project_Detail_Intrim proj = projRepo.findByUsernameAndProjectCodeAndStatus(username, projectCode, 0);
		if(proj == null)
		{
			status = "Invalid request...";
		}
		else
		{
			proj.setStatus(1);
			proj.setProject_Approval_Status(1);
			java.util.Date newdate = new java.util.Date();
			Date timeStamp = new Date(newdate.getTime());
			proj.setLastUpdateDate(new Timestamp(timeStamp.getTime()));
			projRepo.save(proj);
			status = "Scheme Activated Successfully...";
		}
		return status;
	}
	@GetMapping("inactiveKPI")
	public String inactiveKPI(@RequestParam("schemeCode") int projectCode, @RequestParam("username") String username, @RequestParam("kpiId") int kpiId)
	{
		
		String status = "";
		Tbl_Project_KPI_Detail_Intrim kpi = kpiRepo.findByKpiIdAndUsernameAndProjectCodeAndStatus(kpiId, username, projectCode, 1);
		if(kpi == null)
		{
			status = "Invalid request...";
		}
		else
		{
			kpi.setStatus(0);
			java.util.Date newdate = new java.util.Date();
			Date timeStamp = new Date(newdate.getTime());
			kpi.setLastUpdate(new Timestamp(timeStamp.getTime()));
			kpiRepo.save(kpi);
			// clean data from tbl_project_kpi_detail_intrim ???
			status = "KPI Inactivated Successfully...";
		}
		return status;
	}
	@GetMapping("activeKPI")
	public String activeKPI(@RequestParam("schemeCode") int projectCode, @RequestParam("username") String username, @RequestParam("kpiId") int kpiId)
	{
		String status = "";
		Tbl_Project_KPI_Detail_Intrim kpi = kpiRepo.findByKpiIdAndUsernameAndProjectCodeAndStatus(kpiId, username, projectCode, 0);
		if(kpi == null)
		{
			status = "Invalid request...";
		}
		else
		{
			kpi.setStatus(1);
			java.util.Date newdate = new java.util.Date();
			Date timeStamp = new Date(newdate.getTime());
			kpi.setLastUpdate(new Timestamp(timeStamp.getTime()));
			kpiRepo.save(kpi);
			// clean data from tbl_project_kpi_detail_intrim ???
			status = "KPI Activated Successfully...";
		}
		return status;
	}
	@GetMapping("getSchemeDetailsForAdminView")
	@ResponseBody
    @CrossOrigin
	public String getSchemeDetailsForAdminView(@RequestParam("schemeCode") int projectCode)
	{
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(projectCode);
		DataGranularityDetails data = granularityRepo.findByDataGranularityId(proj.getProject_Data_Granularity_ID());
		String modeName = portingModeRepo.getDataPortingModeName(new Long(proj.getPortMode()));
		String freqName = dataFreqRepo.getDataFrequencyName(new Long(proj.getPortingFrequency()));
		String calenderType =  dataRepo.getCalenderTypeName(proj.getCalendarType());
		
		JSONObject jsonobj = new JSONObject();
		
		jsonobj.put("projectName", proj.getProject_name_e());
		jsonobj.put("projCordName", proj.getProject_cord_name_e());
		jsonobj.put("projCordDesignation", proj.getProjectCordDesignation());
		jsonobj.put("projCordEmail", proj.getProject_cord_email().replace("[AT]","@").replace("[DOT]","."));
		jsonobj.put("projCordMobile", proj.getProject_Cord_MobNo());
		jsonobj.put("projHeadName", proj.getProject_head_name_e());
		jsonobj.put("projHeadDesignation", proj.getProjectHeadDesignation());
		jsonobj.put("projHeadEmail", proj.getProject_head_email().replace("[AT]","@").replace("[DOT]","."));
		jsonobj.put("projHeadMobile", proj.getProject_Head_MobNo());
		jsonobj.put("projAdminName", proj.getProject_Admin_Name());
		jsonobj.put("projAdminDesignation", proj.getProjectAdminDesignation());
		jsonobj.put("projAdminEmail", proj.getProject_Admin_Email().replace("[AT]","@").replace("[DOT]","."));
		jsonobj.put("projAdminMobile", proj.getProject_Admin_MobNo());
		jsonobj.put("projectTooltip", proj.getProject_Tooltip_E());
		jsonobj.put("projectDesc", proj.getProject_Description_E());
		jsonobj.put("projectDataLevel", proj.getProject_Data_Lvl_Code());
		//read only in UI
		jsonobj.put("projLaunchDate", proj.getProject_Launch_Date());
		
		jsonobj.put("dataGranularityId", proj.getProject_Data_Granularity_ID());
		jsonobj.put("dataGranularityName", data.getData_Granularity_Name_e());
		jsonobj.put("logoName", proj.getSchemeLogoName());
		jsonobj.put("calendarType", proj.getCalendarType());
		jsonobj.put("calendarTypeName", calenderType);
		jsonobj.put("portingFrequency", proj.getPortingFrequency());
		jsonobj.put("portingFrequencyName", freqName);
		jsonobj.put("portMode", proj.getPortMode());
		jsonobj.put("portModeName", modeName);
		//read only in UI
		jsonobj.put("startdate", proj.getStartDate());
		
		jsonobj.put("waitingDays", proj.getWaitingDays());
		jsonobj.put("sendEmail", proj.getSendEmail());
		jsonobj.put("recpEmail", proj.getRecpEmail().replace("[AT]","@").replace("[DOT]","."));
		jsonobj.put("projectCode", proj.getProjectCode());
		jsonobj.put("username", proj.getUsername());
		//get Port langugae name
		PortingLanguage lang =  langRepo.findByPortLanguageId(proj.getPortLanguageId());
		jsonobj.put("languageName", lang.getLanguageName());
		return jsonobj.toString();
	}
	@GetMapping("getKpiDetailsForAdminView")
	@ResponseBody
    @CrossOrigin
	public String getKpiDetailsForAdminView(@RequestParam("kpiId") int kpiId, @RequestParam("schemeCode") int schemeCode)
	{
		Tbl_Project_KPI_Detail_Intrim kpi =  kpiRepo.findByKpiIdAndProjectCode(kpiId,schemeCode);
		
		String initializationName = "";
		if(kpi.getIs_datareset()== 0) 
		{
			initializationName = "Yes";
		}
		else
		{
			initializationName = "No";
		}
		JSONObject jsonobj = new JSONObject();
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(kpi.getProjectCode());
		jsonobj.put("schemeName", proj.getProject_name_e());
		jsonobj.put("kpiName", kpi.getKpi_name_e());
		jsonobj.put("categoryId", kpi.getKpi_category_id());
		jsonobj.put("categoryName", kpi.getKpi_category_name_e());
		jsonobj.put("dataValueId", kpi.getKpi_data_type_id());
		jsonobj.put("dataDisplayName", kpi.getKpi_data_display_type_name());
		jsonobj.put("initialization", kpi.getIs_datareset());
		jsonobj.put("initializationName", initializationName);
		jsonobj.put("unitName", kpi.getKpi_unit_name_e());
		jsonobj.put("unitId", kpi.getKpi_Unit_id());
		jsonobj.put("tooltip", kpi.getKpiTooltipE());
		jsonobj.put("allowNull", kpi.getAllowNull());
		jsonobj.put("ruleTypeId", kpi.getRuleTypeId());
		jsonobj.put("operatorId", kpi.getOperatorId());
		return jsonobj.toString();
	}
	
	
	@GetMapping("validateKpiIdAndProjectCode")
	@ResponseBody
    @CrossOrigin
	public String validateKpiIdAndProjectCode(@RequestParam("kpiId") int kpiId, @RequestParam("schemeCode") int projectCode)
	{
		Tbl_Project_KPI_Detail_Intrim kpi =  kpiRepo.findByKpiIdAndProjectCode(kpiId, projectCode);
		String status = "";
		
		if(kpi == null)
		{
			status = "invalid";
		}
		else
		{
			status = "valid";
		}
		return status;
	}
	
	@GetMapping("getSchemeLogo")
	@ResponseBody
    @CrossOrigin
    public byte[] getImage(@RequestParam("schemeCode") int schemeCode) throws IOException 
	{
		Tbl_Project_Detail_Intrim proj =  projRepo.findByProjectCode(schemeCode);
		String path = environment.getProperty("schemeLogo");
		File file = new File(path+proj.getSchemeLogoName());
     
        BufferedImage bImage = ImageIO.read(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos );
        byte [] data = bos.toByteArray();
        
        Path imagePath = file.toPath();
        return Files.readAllBytes(imagePath);
      
    }
	private static BufferedImage resize(BufferedImage img, int height, int width) 
	{
        Image tmp = img.getScaledInstance(width+8, height+5, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
