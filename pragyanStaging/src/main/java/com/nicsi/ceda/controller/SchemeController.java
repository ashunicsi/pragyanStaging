package com.nicsi.ceda.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.nicsi.ceda.model.DataIntegrationDetails;
import com.nicsi.ceda.model.KPIDetailsDTO;
import com.nicsi.ceda.model.ListOfKpiForReport;
import com.nicsi.ceda.model.ListOfSchemeForReport;
import com.nicsi.ceda.model.MUnit;
import com.nicsi.ceda.model.Registration;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.model.Tbl_Project_KPI_Detail_Intrim;
import com.nicsi.ceda.repository.CategoryRepo;
import com.nicsi.ceda.repository.DataGranularityDetailsRepo;
import com.nicsi.ceda.repository.ListOfKpiForReportRepo;
import com.nicsi.ceda.repository.ListOfSchemeForReportRepo;
import com.nicsi.ceda.repository.MLevelRepo;
import com.nicsi.ceda.repository.MUnitRepo;
import com.nicsi.ceda.repository.RegistrationRepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.repository.Tbl_Project_KPI_Detail_IntrimRepo;
import com.nicsi.ceda.util.GetIP;


@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class SchemeController 
{
	@Autowired
	private Tbl_Project_KPI_Detail_IntrimRepo projKpiRepo;
	@Autowired
	private Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private MUnitRepo unitRepo;
	@Autowired
	private GetIP ip;
	@Autowired
	private MLevelRepo levelRepo;
	@Autowired
	private CategoryRepo catRepo;
	@Autowired
    private Environment environment;
	@Autowired
	private DataGranularityDetailsRepo granRepo;
	@Autowired
	private ListOfSchemeForReportRepo schemeReportRepo;
	@Autowired
	private ListOfKpiForReportRepo kpiReportRepo;
	@Autowired
	private RegistrationRepo regRepo;
	private static final Logger logger = LoggerFactory.getLogger(SchemeController.class);

	
	
	
	@PostMapping(value ="addScheme", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
    @CrossOrigin
	public String addScheme(@RequestParam("sectorCode") int sectorCode, @RequestParam("file") MultipartFile schemeLogo, 
							@RequestParam("ministryCode") int ministryCode, @RequestParam("deptCode") int deptCode,
							@RequestParam("projectCordEmail") String projectCordEmail,	@RequestParam("projectCordMobNo") String projectCordMobNo ,	
							@RequestParam("projectCordNameE") String projectCordNameE,	@RequestParam("projectCordDesignation") String projectCordDesignation,
							@RequestParam("projectHeadEmail") String projectHeadEmail,	@RequestParam("projectHeadMobNo") String projectHeadMobNo,	
							@RequestParam("projectHeadNameE") String projectHeadNameE, @RequestParam("projectHeadDesignation") String projectHeadDesignation,
							@RequestParam("projectAdminEmail") String projectAdminEmail,@RequestParam("projectAdminPhone") String projectAdminPhone,	
							@RequestParam("projectAdminName") String projectAdminName, @RequestParam("projectAdminDesignation") String projectAdminDesignation,
							@RequestParam("projectTooltipE") String projectTooltipE,	@RequestParam("projectDescriptionE") String projectDescriptionE,	
							@RequestParam("schemeName") String schemeName, @RequestParam("projectDataGranularityID") int projectDataGranularityID,
							@RequestParam("projectLaunchDate")  java.util.Date projectLaunchDate, @RequestParam("projectCategoryId") int projectCategoryId,
							
							@RequestParam("calendarType") int calendarType, @RequestParam("portingFrequency") int portingFrequency,
							@RequestParam("portMode") int portMode, @RequestParam("sendEmail") String sendEmail,
							@RequestParam("startDate") java.util.Date startDate, @RequestParam("waitingDays") int waitingDays, @RequestParam("recpEmail") String recpEmail,
							@RequestParam("username") String username, @RequestParam("portLanguageId") int portLanguageId,
						
							HttpServletRequest request) throws ParseException, IOException
	{
		logger.info("sectorCode = " +sectorCode);
		logger.info("schemeLogo = " +schemeLogo);
		logger.info("ministryCode = " +ministryCode);
		logger.info("deptCode = " +deptCode);
		
		logger.info("projectCordEmail = " +projectCordEmail);
		logger.info("projectCordMobNo = " +projectCordMobNo);
		logger.info("projectCordNameE = " +projectCordNameE);
		logger.info("projectCordDesignation = " +projectCordDesignation);
		
		logger.info("projectHeadEmail = " +projectHeadEmail);
		logger.info("projectHeadMobNo = " +projectHeadMobNo);
		logger.info("projectHeadNameE = " +projectHeadNameE);
		logger.info("projectHeadDesignation = " +projectHeadDesignation);
		
		logger.info("projectAdminEmail = " +projectAdminEmail);
		logger.info("projectAdminPhone = " +projectAdminPhone);
		logger.info("projectAdminName = " +projectAdminName);
		logger.info("projectAdminDesignation = " +projectAdminDesignation);
		
		logger.info("schemeName = " +schemeName);
		logger.info("projectDescriptionE = " +projectDescriptionE);
		logger.info("projectDataGranularityID = " +projectDataGranularityID);
		logger.info("projectLaunchDate = " +projectLaunchDate);
		logger.info("projectCategoryId = " +projectCategoryId);
		logger.info("calendarType = " +calendarType);
		logger.info("portingFrequency = " +portingFrequency);
		logger.info("portMode = " +portMode);
		logger.info("sendEmail = " +sendEmail);
		logger.info("waitingDays = " +waitingDays);
		logger.info("username = " +username);
		logger.info("portMode = " +portMode);
		logger.info("portMode = " +portMode);
		logger.info("portLanguageId = " +portLanguageId);
		
			String status = null;
			int flag = 1;
			String schemeLogoName = null;
			if(schemeLogo.getSize() != 0)
			{
				//save file and set fileName;
				try
				{
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
				catch (Exception e) 
				{
					logger.error("Error in uploading scheme logo ");
					logger.info("----------------------------");
					status = "Something went wrong please try again";
					flag = 0;
				}
			}
			ListOfSchemeForReport report = new ListOfSchemeForReport();
			 
		
			LocalDateTime dateTime = LocalDateTime.now();
			Tbl_Project_Detail_Intrim proj = new Tbl_Project_Detail_Intrim();
			proj.setSchemeLogoName(schemeLogoName);
			proj.setEntry_Date(null);
			//proj.setProject_Launch_Date(projectLaunchDate);
			proj.setSec_code(sectorCode);
			proj.setMinistry_code(ministryCode);
			proj.setDept_code(deptCode);
			report.setDepartmentCode(deptCode);
			report.setMinistryCode(ministryCode);
			
			int count = projRepo.getMaxProjectCode();
			int projectCode = count;
			
			projectCode = projectCode + 1;
			proj.setProjectCode(projectCode);
			report.setSchemeCode(projectCode);
			report.setSchemeName(schemeName);
			//Report Data
			//get deptName, minsitryName
			 report.setDepartmentName(levelRepo.getDepartmentName(deptCode, 21));
			  report.setMinistryName(levelRepo.getMinistryName(ministryCode, 22));
			  report.setStatus(1);
			//schemType
			//schemeName
			String email = projectCordEmail.replace("@","[AT]");
			email = email.replace(".","[DOT]");
			report.setCordEmail(email);
			proj.setProject_cord_email(email);
			proj.setProject_Cord_MobNo(projectCordMobNo);
			proj.setProject_cord_name_e(projectCordNameE);
			proj.setProjectCordDesignation(projectCordDesignation);

			email = projectHeadEmail.replace("@","[AT]");
			email = email.replace(".","[DOT]");
			report.setHeadEmail(email);
			proj.setProject_head_email(email);
			proj.setProject_Head_MobNo(projectHeadMobNo);
			proj.setProject_head_name_e(projectHeadNameE);
			proj.setProjectHeadDesignation(projectHeadDesignation);
			
			email = projectAdminEmail.replace("@","[AT]");
			email = email.replace(".","[DOT]");
			report.setAdminEmail(email);
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
			
			java.sql.Date sqlDate = new java.sql.Date(startDate.getTime());
			proj.setStartDate(sqlDate);
			sqlDate = new java.sql.Date(projectLaunchDate.getTime());
			proj.setProject_Launch_Date(sqlDate);
			 
			
			java.util.Date newdate = new java.util.Date();
			Date timeStamp = new Date(newdate.getTime());
			proj.setEntry_Date(new Timestamp(timeStamp.getTime()));
			//date = inputFormat.parse(startDate);
			

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
			
		
			proj.setLevel10_Code(0);
			proj.setLevel1_Code(0);
			proj.setLevel2_Code(0);
			proj.setLevel3_Code(0);
			proj.setLevel4_Code(0);
			proj.setLevel5_Code(0);
			proj.setLevel6_Code(0);
			proj.setLevel7_Code(0);
			proj.setLevel8_Code(0);
			proj.setLevel9_Code(0);
			proj.setInstance_Code(0);
			proj.setInstance_TypeCode_Sub(0);
			proj.setInstance_TypeCode_main(0);
			proj.setInstance_Lvl_Code(0);
			
			proj.setProject_Category_Id(0);
			proj.setDB_Server_Code(0);
			proj.setModifiy_Permission(0);
			proj.setDelete_Permission(0);
			
			proj.setLock_Status(0);
			//Changed when project will be approve
			proj.setProject_Approval_Status(0);
			proj.setStatus(0);
			proj.setEntry_by_User_Code(0);
			proj.setEntry_by_User_Type_Code(0);
			proj.setModify_by_User_Code(0);
			proj.setModify_By_User_Type_Code(0);
			proj.setProject_Master_Category_Id(0);
			proj.setMajor_Minor_Type(0);
			proj.setUser_Sys_IP(ip.getIPAddress(request));
			proj.setUsername(username);
			proj.setMajor_Proj_Code(new Long(projectCode));
			proj.setIsRejected(0);
			proj.setPortLanguageId(portLanguageId);
			try
			{
				logger.info("Added Scheme Data for project code "+proj.getProjectCode());
				logger.info("Scheme Reg Data"+proj);
				logger.info("----------------------------");
				projRepo.save(proj);
				status = "success";
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			
			schemeReportRepo.save(report);
			//update registration table 
			Registration reg = regRepo.findByEmail(username);
			reg.setProjectCode(projectCode);
			regRepo.save(reg);
		
		return status;
	}
	@PostMapping("dataIntegrationDetails")
	@ResponseBody
    @CrossOrigin
	public String dataIntegrationDetails(@RequestBody DataIntegrationDetails data)
	{
		String status = "";
		if(data.getCalendarType() == 0 || data.getPFrequency() == 0 || data.getPortMode() == 0 || data.getSendEmail() == null || data.getStartDate() == null || data.getWaitingDays() == 0)
		{
			status = "error";
		}
		else
		{
			status = "success";
		}
		//System.out.println(data);
		return status;
	}
	//consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
	@PostMapping(value="addKPIDetails", consumes = "application/json", produces = "application/json" )
	@ResponseBody
    @CrossOrigin
	public String kpiDetails(@RequestBody List<KPIDetailsDTO> obj, HttpServletRequest request)
	{
		logger.info("KPII Added Data = "+obj);
		
		String status = "";
		for(KPIDetailsDTO d : obj)
		{
			if(d.getSchemeCode() == 0 || d.getUnitId() == 0 || d.getCategoryId() == 0 || d.getDataValueId() == 0 || d.getInitialization() == "" || d.getKpiName() == "" || 
					d.getTooltip() == "" || d.getAllowNull() == "" )
			{
				status = "Please fill all details...";
				
			}
			
		}
		if(!status.equals("Please fill all details..."))
		{
			 
			for(KPIDetailsDTO data : obj)
			{
				ListOfKpiForReport kpi = new ListOfKpiForReport();
				if(data.getKpiName() == null)
				{
					status = "error";
				}
				else
				{
					Tbl_Project_Detail_Intrim proj= projRepo.findByProjectCode(data.getSchemeCode());
					
					String kpi_data_display_type_name = null;
					if(data.getDataValueId() == 1)
					{
						kpi_data_display_type_name = "cumulative";
					}
					else
					{
						kpi_data_display_type_name = "Current";
					}
					
					//String schemeName = projRepo.getSchemeName(data.getSchemeCode());
					
					Tbl_Project_KPI_Detail_Intrim tbl = new Tbl_Project_KPI_Detail_Intrim();
					
					java.util.Date newdate = new java.util.Date();
					Date timeStamp = new Date(newdate.getTime());
					
					tbl.setEntry_date(new Timestamp(timeStamp.getTime()));
					tbl.setKpi_data_display_type_name(kpi_data_display_type_name);
					tbl.setKpi_data_display_type_id(data.getDataValueId());
					tbl.setKpi_category_id(data.getCategoryId());
					
					tbl.setKpi_data_display_type_id(data.getDataValueId());
					tbl.setProjectCode(data.getSchemeCode());
					tbl.setKpiTooltipE(data.getTooltip());
					tbl.setKpiTooltipR(data.getTooltip());
					//String validationMin = data.getKpiValidationMin();
					//String validationMax = data.getKpiValidationMax();
					//String separator ="-";
					//int sepPos = validation.lastIndexOf(separator);
					//Double kpiDataMinLimit = Double.parseDouble(validationMin);
					//sepPos = validationTemp.indexOf("-");
					//Double kpiDataMaxLimit = Double.parseDouble(validationMax);
					//tbl.setKpi_data_min_limit(kpiDataMinLimit);
					//tbl.setKpi_data_max_limit(kpiDataMaxLimit);
					tbl.setSec_code(data.getSectorCode());
					tbl.setMinistry_code(proj.getMinistry_code());
					tbl.setDept_code(proj.getDept_code());
					tbl.setIs_datareset(0);
					tbl.setStatus(1);
					tbl.setKpi_name_e(data.getKpiName());
					tbl.setKpi_name_r(data.getKpiName());
					
					int kpiId = projKpiRepo.countKPI(data.getSchemeCode());
					kpiId = kpiId + 1;
					tbl.setKpiId(kpiId);
					tbl.setKpi_id_actual(kpiId);
					
					//for kpi id, count total registered kpi for projectNumber(SchemeCode) and increement by 1
					//get Ministry Name
					String ministryName = levelRepo.getMinistryName(data.getMinistryCode(), 22);
					String deptName = levelRepo.getDepartmentName(data.getDeptCode(), 23);
				
					//get Department Name
					//get Scheme Name 
					//Get Unit Name from m_unit table
					MUnit u = unitRepo.findByUnitId(data.getUnitId());
					tbl.setKpi_Unit_id(data.getUnitId());
					tbl.setKpi_unit_name_e(u.getUnit_name_e());
					tbl.setKpi_unit_name_r(u.getUnit_name_e());
					//getInitialization
					//get Sector code from reg table
					if(data.getInitialization().equals("yes"))
					{
						tbl.setIs_datareset(1);
					}
					else
					{
						tbl.setIs_datareset(0);
					}
					
					tbl.setStatus(1);
					tbl.setInstance_Lvl_code(1);
					tbl.setLevel10_code(0);
					tbl.setLevel1_code(0);
					tbl.setLevel2_code(0);
					tbl.setLevel3_code(0);
					tbl.setLevel4_code(0);
					tbl.setLevel5_code(0);
					tbl.setLevel6_code(0);
					tbl.setLevel7_code(0);
					tbl.setLevel8_code(0);
					tbl.setLevel9_code(0);
					tbl.setInstance_code(0);
					tbl.setInstance_TypeCode_Sub(0);
					tbl.setInstance_TypeCode_main(0);
					tbl.setInstance_code(0);
					//tbl.setProject_Data_Lvl_Code(0);
					tbl.setKpi_percentage_id_actual(0);
					
					tbl.setKpi_data_type_id(data.getDataValueId());
					tbl.setKpi_data_type_Name(kpi_data_display_type_name);
					
					tbl.setKpi_data_type_id(data.getDataValueId());
					tbl.setKpi_data_display_type_name(kpi_data_display_type_name);
					
					tbl.setData_reset_frequency_id(0);
					tbl.setIs_data_aggregate(0);
					tbl.setIs_updated(0);
					tbl.setModifiy_permission(0);
					tbl.setDelete_permission(0);
					tbl.setStatus(1);
					tbl.setLock_status(0);
					tbl.setProject_approval_status(0);
					tbl.setEntry_by_user_code(0);
					tbl.setEntry_by_user_type_code(0);
					tbl.setModify_by_user_code(0);
					tbl.setModify_by_user_type_code(0);
					tbl.setUser_sys_ip(ip.getIPAddress(request));
					tbl.setKpi_category_name_e(catRepo.findCategoryName(Long.valueOf(data.getCategoryId())));
					tbl.setKpi_category_name_r(catRepo.findCategoryName(Long.valueOf(data.getCategoryId())));
					tbl.setKpi_percentage_id(0);
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					//tbl.setEntry_date(cal);
					tbl.setIs_datareset(0);
					tbl.setRuleTypeId(data.getRuleTypeId());
					tbl.setOperatorId(data.getOperatorId());
					tbl.setAllowNull(data.getAllowNull());
					tbl.setUsername(data.getUsername());
					if(data.getInitialization().equals("yes"))
					{
						tbl.setIs_datareset(1);
					}
					if(data.getInitialization().equals("no"))
					{
						tbl.setIs_datareset(0);
					}
					//Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(data.getSchemeCode());
					tbl.setUsername(proj.getUsername());
					
					 kpi.setDeptCode(proj.getDept_code());
					 kpi.setDeptName(levelRepo.getDepartmentName(proj.getDept_code(), 21));
					 kpi.setMinistryName(levelRepo.getMinistryName(proj.getMinistry_code(), 22));
					  kpi.setKpiName(data.getKpiName());
					  kpi.setMinistryCode(proj.getMinistry_code());
					  kpi.setSchemeId(data.getSchemeCode());
					  kpi.setSchemeName(proj.getProject_name_e());
					  kpi.setToolTip(data.getTooltip());
					  kpi.setStatus(1);
					  kpi.setKpiId(tbl.getKpiId());
					  kpiReportRepo.save(kpi);
					//System.out.println(kpi);
					  logger.info("KPI Details for projetct code "+proj.getProjectCode());
					  logger.info("Scheme Reg Data"+tbl);
					  logger.info("----------------------------");
					  
					projKpiRepo.save(tbl);
					
					
					//System.out.println(tbl.getKpiId());
					status = "success";
				}
			}
		}
		
		return status;
	}
	@GetMapping("schemeForKPI")
	@ResponseBody
    @CrossOrigin
	public String schemeForKPI(@RequestParam("username") String username)
	{
		List<Tbl_Project_Detail_Intrim> project = projRepo.findByUsernameAndProjectApprovalStatus(username, 1);
		JSONArray levelList = new JSONArray();
		for(Tbl_Project_Detail_Intrim proj : project)
		{
			 JSONObject jsonobj = new JSONObject();
			
			 jsonobj.put("schemeName", proj.getProject_name_e());
			 jsonobj.put("schemeCode", proj.getProjectCode());
			 levelList.put(jsonobj);
		}
		return levelList.toString();
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
