package com.nicsi.ceda.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nicsi.ceda.model.KPIDetailsDTO;
import com.nicsi.ceda.model.ListOfKpiForReport;
import com.nicsi.ceda.model.ListOfSchemeForReport;
import com.nicsi.ceda.model.RecvApiDataLogs;
import com.nicsi.ceda.model.SchemeDetailsDTO;
import com.nicsi.ceda.model.TblDataPortKpiPortedDataDTO;
import com.nicsi.ceda.model.Tbl_DataPort_KPI_PORTED_DATA;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.repository.DataGranularityDetailsRepo;
import com.nicsi.ceda.repository.Dim_TimeRepo;
import com.nicsi.ceda.repository.KpiPortedDataReplicaRepo;
import com.nicsi.ceda.repository.ListOfKpiForReportRepo;
import com.nicsi.ceda.repository.ListOfSchemeForReportRepo;
import com.nicsi.ceda.repository.MLevelRepo;
import com.nicsi.ceda.repository.MUnitRepo;
import com.nicsi.ceda.repository.RecvApiDataLogsRepo;
import com.nicsi.ceda.repository.RecvApiDataLogsToDisplayRepo;
import com.nicsi.ceda.repository.Tbl_DataPort_KPI_PORTED_DATARepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.repository.Tbl_Project_KPI_Detail_IntrimRepo;
import com.nicsi.ceda.services.ICountDetailsService;
import com.nicsi.ceda.services.IDataService;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class UserDataViewController 
{

	@Autowired
	private IDataService dataService;
	@Autowired
	private ICountDetailsService countService;
	@Autowired
	private Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private Tbl_Project_KPI_Detail_IntrimRepo kpiDetRepo;
	@Autowired
	private Tbl_DataPort_KPI_PORTED_DATARepo portedDataRepo;
	@Autowired
	private MUnitRepo unitRepo;
	@Autowired
	private ListOfSchemeForReportRepo schemeReportRepo;
	@Autowired
	private ListOfKpiForReportRepo kpiRepo;
	@Autowired
	private RecvApiDataLogsRepo dataLogRepo;
	@Autowired
	private DataGranularityDetailsRepo granularityRepo;
	@Autowired
	private Dim_TimeRepo timeRepo;
	@Autowired
	private MLevelRepo levelRepo;
	@Autowired
	private RecvApiDataLogsToDisplayRepo displayRepo;
	@Autowired
	private KpiPortedDataReplicaRepo relpicaRepo;
	
	@GetMapping("userHome")
	@ResponseBody
    @CrossOrigin
    public String homePage(@RequestParam("username") String username)
    {
		int registeredScheme  = countService.countRegisteredScheme(username);
		int activatedScheme = countService.countActivatedScheme(username);
		int deactivatedScheme = countService.countDeactivatedScheme(username);
		int registeredKPI = countService.countRegisteredKPI(username);
		int activatedKPI = countService.countActivatedKPI(username);
		int deactivatedKPI = countService.countDeactivatedKPI(username);
		
		int schemeData = countService.countSchemeData(username);
		int schemeDataUpto = countService.countSchemeDataUpto(username);
		int schemeDataPendency = countService.countSchemeDataPendency(username);
		int schemeReviewed = countService.countSchemeReviewed(username);
		int schemePendingForReview = countService.countSchemePendingForReview(username);
		int schemeIntegrationErrors = countService.countSchemeIntegrationErrors(username);
		int rejectedScheme = countService.countRejectedScheme(username);
		int pendingScheme = countService.countSubmittedScheme(username);
		
		
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("registeredScheme", registeredScheme);
		jsonobj.put("activatedScheme", activatedScheme);
		jsonobj.put("deactivatedScheme", deactivatedScheme);
		jsonobj.put("registeredKPI", registeredKPI);
		jsonobj.put("activatedKPI", activatedKPI);
		jsonobj.put("deactivatedKPI", deactivatedKPI);
		//jsonobj.put("schemeData", schemeData);
		jsonobj.put("schemeData", activatedScheme);
		//jsonobj.put("schemeDataUpto", schemeDataUpto);
		jsonobj.put("schemeDataUpto", schemeDataUpto);
	
		//jsonobj.put("schemeDataPendency", schemeDataPendency);
		jsonobj.put("schemeDataPendency", schemeDataPendency);
		jsonobj.put("schemeReviewed", schemeReviewed);
		jsonobj.put("schemePendingForReview", schemePendingForReview);
		//jsonobj.put("schemeIntegrationErrors", schemeIntegrationErrors);
		jsonobj.put("schemeIntegrationErrors", 0);
		 jsonobj.put("rejectedScheme", rejectedScheme);
		 jsonobj.put("pendingScheme", pendingScheme);
		return jsonobj.toString();
    }
	@PostMapping("userHome")
	@ResponseBody
    @CrossOrigin
    public String homePageInfo(@RequestParam("username") String username)
    {
		int registeredScheme  = countService.countRegisteredScheme(username);
		int activatedScheme = countService.countActivatedScheme(username);
		int deactivatedScheme = countService.countDeactivatedScheme(username);
		int registeredKPI = countService.countRegisteredKPI(username);
		int activatedKPI = countService.countActivatedKPI(username);
		int deactivatedKPI = countService.countDeactivatedKPI(username);
		
		int schemeData = countService.countSchemeData(username);
		int schemeDataUpto = countService.countSchemeDataUpto(username);
		int schemeDataPendency = countService.countSchemeDataPendency(username);
		int schemeReviewed = countService.countSchemeReviewed(username);
		int schemePendingForReview = countService.countSchemePendingForReview(username);
		int schemeIntegrationErrors = countService.countSchemeIntegrationErrors(username);
		int rejectedScheme = countService.countRejectedScheme(username);
		int pendingScheme = countService.countSubmittedScheme(username);
		
		
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("registeredScheme", registeredScheme);
		jsonobj.put("activatedScheme", activatedScheme);
		jsonobj.put("deactivatedScheme", deactivatedScheme);
		jsonobj.put("registeredKPI", registeredKPI);
		jsonobj.put("activatedKPI", activatedKPI);
		jsonobj.put("deactivatedKPI", deactivatedKPI);
		//jsonobj.put("schemeData", schemeData);
		jsonobj.put("schemeData", activatedScheme);
		//jsonobj.put("schemeDataUpto", schemeDataUpto);
		jsonobj.put("schemeDataUpto", schemeDataUpto);
	
		//jsonobj.put("schemeDataPendency", schemeDataPendency);
		jsonobj.put("schemeDataPendency", schemeDataPendency);
		jsonobj.put("schemeReviewed", schemeReviewed);
		jsonobj.put("schemePendingForReview", schemePendingForReview);
		//jsonobj.put("schemeIntegrationErrors", schemeIntegrationErrors);
		jsonobj.put("schemeIntegrationErrors", 0);
		 jsonobj.put("rejectedScheme", rejectedScheme);
		 jsonobj.put("pendingScheme", pendingScheme);
		return jsonobj.toString();
    }
	@GetMapping("viewUserActivatedScheme")
	@ResponseBody
    @CrossOrigin
	public String viewScheme(ModelMap map, HttpServletRequest request, HttpSession sessions, @RequestParam("username") String username)
	{
		//map.addAttribute("data",dataService.findActivatedScheme(map, request, sessions));
		List<SchemeDetailsDTO> data =dataService.findByUsernameAndStatus(username, 1, map, request, sessions);
		
		data.sort(Comparator.comparingInt(SchemeDetailsDTO::getSchemeCode));
		JSONArray levelList = new JSONArray();
		int sn = 1;
		for(SchemeDetailsDTO s : data)
		{
			  
			  
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", sn++);
			 jsonobj.put("id", s.getId());
			 jsonobj.put("sectorCode", s.getSectorCode());
			 jsonobj.put("sectorName", s.getSectorName());
			 jsonobj.put("ministryCode", s.getMinistryCode());
			 jsonobj.put("ministryName",s.getMinistryName());
			 jsonobj.put("departmentCode", s.getDepartmentCode());
			 jsonobj.put("departmentName",s.getDepartmentName());
			 jsonobj.put("schemeName", s.getSchemeName());
			 jsonobj.put("schemeCode",s.getSchemeCode());
			 jsonobj.put("cordEmail",s.getCordEmail());
			 jsonobj.put("headEmail",s.getHeadEmail());
			 jsonobj.put("adminEmail",s.getAdminEmail());
			 
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("viewUserDeactivatedScheme")
	@ResponseBody
    @CrossOrigin
	public String viewDectivatedScheme(ModelMap map, HttpServletRequest request, HttpSession sessions, @RequestParam("username") String username)
	{
		//map.addAttribute("data",dataService.findActivatedScheme(map, request, sessions));
		List<SchemeDetailsDTO> data =dataService.findByUsernameAndStatus(username, 0, map, request, sessions);
		data.sort(Comparator.comparingInt(SchemeDetailsDTO::getSchemeCode));
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
			 
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("getUserSubmittedScheme")
	@ResponseBody
    @CrossOrigin
	public String getSubmittedScheme(ModelMap map, HttpServletRequest request, HttpSession sessions, @RequestParam("username") String username)
	{
		//map.addAttribute("data",dataService.findActivatedScheme(map, request, sessions));
		List<SchemeDetailsDTO> data =dataService.findByUsernameAndProject_Approval_Status(username, 0, map, request, sessions);
		data.sort(Comparator.comparingInt(SchemeDetailsDTO::getSchemeCode));
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
			 //date = StringUtils.abbreviate(date, 18);
			 //System.out.println(date);
			 jsonobj.put("submittedDate", date);
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("getUserRejectedScheme")
	@ResponseBody
    @CrossOrigin
	public String getUserRejectedScheme(ModelMap map, HttpServletRequest request, HttpSession sessions, @RequestParam("username") String username)
	{
		//map.addAttribute("data",dataService.findActivatedScheme(map, request, sessions));
		List<SchemeDetailsDTO> data =dataService.findByUsernameAndIsRejected(username, 1);
		data.sort(Comparator.comparingInt(SchemeDetailsDTO::getSchemeCode));
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
			 SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
			 jsonobj.put("rejectDate", f.format(s.getProjectRejectionDate()));
			 
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	
	@GetMapping("countUserScheme")
	@ResponseBody
    @CrossOrigin
    public String countScheme(@RequestParam("username") String username)
    {
		int registeredScheme  = countService.countRegisteredScheme(username);
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("registeredScheme", registeredScheme);
		 return jsonobj.toString();
		
    }
	@GetMapping("countUserActivatedScheme")
	@ResponseBody
    @CrossOrigin
    public int countActivatedScheme(@RequestParam("username") String username)
    {
		int activatedScheme = countService.countActivatedScheme(username);
		return activatedScheme;
    }
	@GetMapping("countUserDeactivatedScheme")
	@ResponseBody
    @CrossOrigin
    public int countInactivatedScheme(@RequestParam("username") String username)
    {
		int deactivatedScheme = countService.countDeactivatedScheme(username);
		return deactivatedScheme;
    }
	@GetMapping("countUserRejectedScheme")
	@ResponseBody
    @CrossOrigin
    public int countUserRejectedScheme(@RequestParam("username") String username)
    {
		int rejectedScheme = countService.countRejectedScheme(username);
	
		return rejectedScheme;
    }
	@GetMapping("countUserSubmittedScheme")
	@ResponseBody
    @CrossOrigin
    public int countSubmittedScheme(@RequestParam("username") String username)
    {
		int submittedScheme = countService.countSubmittedScheme(username);
		
		return submittedScheme;
    }
	
	
	@GetMapping("userSchemeForKPI")
	@ResponseBody
    @CrossOrigin
	public String schemeForKPI(@RequestParam("username") String username)
	{
		List<Tbl_Project_Detail_Intrim> project = projRepo.findByUsername(username);
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
	@GetMapping("countUserActivatedKPI")
	@ResponseBody
    @CrossOrigin
    public int countActivatedKPI(@RequestParam("username") String username)
    {
		int activatedKPI = countService.countActivatedKPI(username);
		//System.out.println("activatedKPI = "+activatedKPI);
		return activatedKPI;
    }
	@GetMapping("countUserDeactivatedKPI")
	@ResponseBody
    @CrossOrigin
    public int countDeactivatedKPI(@RequestParam("username") String username)
    {
		int deactivatedKPI = countService.countDeactivatedKPI(username);
		return deactivatedKPI;
    }
	
	@GetMapping("viewUserActivatedKPI")
	@ResponseBody
    @CrossOrigin
	public String viewKPI(ModelMap map, HttpServletRequest request, HttpSession sessions, @RequestParam("username") String username)
	{
		
		List<KPIDetailsDTO> data =dataService.findKpiByUsernameAndStatus(username, 1, map, request, sessions);
		
		data.sort(Comparator.comparingInt(KPIDetailsDTO::getSchemeCode));
		
		  JSONArray levelList = new JSONArray(); int sn = 1;
		  
		  for(KPIDetailsDTO s : data) 
		  { 
			
			
			  
			  JSONObject jsonobj = new JSONObject();
			  jsonobj.put("sn", sn++); 
			  jsonobj.put("id", s.getId());
			  jsonobj.put("ministryCode", s.getMinistryCode());
			  jsonobj.put("ministryName",s.getMinistryName());
			  jsonobj.put("deptName", s.getDeptName());
			  jsonobj.put("deptCode", s.getDeptCode());
			  jsonobj.put("kpiName", s.getKpiName());
			  jsonobj.put("kpiId",s.getKpiId());
			  jsonobj.put("toolTip",s.getTooltip());
			  jsonobj.put("schemeName", s.getSchemeName());
			  jsonobj.put("schemeCode",s.getSchemeCode());
		  //System.out.println(jsonobj);
			  levelList.put(jsonobj); 
			  
		 } 
		 // System.out.println("ASHU");
		  return levelList.toString();
		 
		
	}
	@GetMapping("viewUserDeactivatedKPI")
	@ResponseBody
    @CrossOrigin
	public String viewDectivatedKPI(ModelMap map, HttpServletRequest request, HttpSession sessions, @RequestParam("username") String username)
	{
		//map.addAttribute("data",dataService.findActivatedScheme(map, request, sessions));
		List<KPIDetailsDTO> data =dataService.findKpiByUsernameAndStatus(username, 0, map, request, sessions);
		data.sort(Comparator.comparingInt(KPIDetailsDTO::getSchemeCode));

		  JSONArray levelList = new JSONArray(); 
		  int sn = 1;
		  for(KPIDetailsDTO s : data) 
		  { 
			  
			  
			  JSONObject jsonobj = new JSONObject();
			  jsonobj.put("sn", sn++); 
			  jsonobj.put("id", s.getId());
			  jsonobj.put("ministryCode", s.getMinistryCode());
			  jsonobj.put("ministryName",s.getMinistryName());
			  jsonobj.put("deptName", s.getDeptName());
			  jsonobj.put("deptCode", s.getDeptCode());
			  jsonobj.put("kpiName", s.getKpiName());
			  jsonobj.put("kpiId",s.getKpiId());
			  jsonobj.put("toolTip",s.getTooltip());
			  jsonobj.put("schemeName", s.getSchemeName());
			  jsonobj.put("schemeCode",s.getSchemeCode());
		  //System.out.println(jsonobj);
			  levelList.put(jsonobj); 
			  
		 } 
		return levelList.toString();
	}
	@GetMapping("lastPushedData")
	@ResponseBody
    @CrossOrigin
	public String lastPushedData(ModelMap map, HttpServletRequest request, HttpSession sessions, @RequestParam("username") String username)
	{
		//map.addAttribute("data",dataService.findActivatedScheme(map, request, sessions));
		Timestamp entryDate = portedDataRepo.getMaxDateForUsername(username);
		List<Tbl_DataPort_KPI_PORTED_DATA> data =portedDataRepo.lastPushedData(entryDate, username);
		data.sort(Comparator.comparingLong(Tbl_DataPort_KPI_PORTED_DATA::getProjectCode));

		  JSONArray levelList = new JSONArray(); 
		  int sn = 1;
		  for(Tbl_DataPort_KPI_PORTED_DATA t : data) 
		  { 
			  JSONObject jsonobj = new JSONObject();
			  jsonobj.put("sectorCode", t.getSec_code()); 
			  jsonobj.put("ministryCode", t.getMinistry_Code());
			  jsonobj.put("deptCode", t.getDept_code());
			  jsonobj.put("projectCode",t.getProjectCode());
			  jsonobj.put("level1Code", t.getLevel1Code());
			  jsonobj.put("level2Code", t.getLevel2Code());
			  jsonobj.put("level3Code", t.getLevel3Code());
			  jsonobj.put("level4Code", t.getLevel4Code());
			  jsonobj.put("level5Code", t.getLevel5Code());
			  jsonobj.put("level6Code", t.getLevel6Code());
			  jsonobj.put("level7Code", t.getLevel7Code());
			  jsonobj.put("level8Code", t.getLevel8Code());
			  jsonobj.put("level9Code", t.getLevel9Code());
			  jsonobj.put("level10Code", t.getLevel10Code());

			  jsonobj.put("kpiId", t.getKpiId());
			  jsonobj.put("KpiData", t.getKpi_Data());
			  SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			  //System.out.println(t.getKpi_Data());
			  jsonobj.put("dataDate", f.format(t.getDatadt()));
			  levelList.put(jsonobj); 
			  
		 } 
		return levelList.toString();
	}
}
