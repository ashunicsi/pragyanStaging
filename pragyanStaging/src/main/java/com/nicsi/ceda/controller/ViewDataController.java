package com.nicsi.ceda.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nicsi.ceda.model.KPIDetailsDTO;
import com.nicsi.ceda.model.MKPIValidationRuleType;
import com.nicsi.ceda.model.RecvApiDataLogs;
import com.nicsi.ceda.model.RecvApiDataLogsToDisplay;
import com.nicsi.ceda.model.SchemeDetailsDTO;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.model.Tbl_Project_KPI_Detail_Intrim;
import com.nicsi.ceda.repository.DataGranularityDetailsRepo;
import com.nicsi.ceda.repository.Dim_TimeRepo;
import com.nicsi.ceda.repository.KpiPortedDataReplicaRepo;
import com.nicsi.ceda.repository.ListOfKpiForReportRepo;
import com.nicsi.ceda.repository.ListOfSchemeForReportRepo;
import com.nicsi.ceda.repository.MLevelRepo;
import com.nicsi.ceda.repository.MUnitRepo;
import com.nicsi.ceda.repository.PM_Dashboard_NewRepo;
import com.nicsi.ceda.repository.RecvApiDataLogsRepo;
import com.nicsi.ceda.repository.RecvApiDataLogsToDisplayRepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.repository.Tbl_Project_KPI_Detail_IntrimRepo;
import com.nicsi.ceda.services.ICountDetailsService;
import com.nicsi.ceda.services.IDataService;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class ViewDataController 
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
	
	@Autowired
	private PM_Dashboard_NewRepo dashRepo;
	@PersistenceContext
    private EntityManager entityManager;
	
	
	@GetMapping("home")
	@ResponseBody
    @CrossOrigin
    public String homePage()
    {
		
		int registeredScheme  = countService.countRegisteredScheme();
		int activatedScheme = countService.countActivatedScheme();
		int deactivatedScheme = countService.countDeactivatedScheme();
		int registeredKPI = countService.countRegisteredKPI();
		int activatedKPI = countService.countActivatedKPI();
		int deactivatedKPI = countService.countDeactivatedKPI();
		int schemeData = countService.countSchemeData();
		
		int schemeDataUpto = countService.countSchemeDataUpto();
		int schemeDataPendency = countService.countSchemeDataPendency();
		int schemeReviewed = countService.countSchemeReviewed();
		int schemePendingForReview = countService.countSchemePendingForReview();
		int schemeIntegrationErrors = countService.countSchemeIntegrationErrors();
		int rejectedScheme = countService.countRejectedScheme();
		int pendingScheme = countService.countSubmittedScheme();
		 JSONObject jsonobj = new JSONObject();
		 jsonobj.put("registeredScheme", registeredScheme);
		 jsonobj.put("activatedScheme", activatedScheme);
		 jsonobj.put("deactivatedScheme", deactivatedScheme);
		 jsonobj.put("registeredKPI", registeredKPI);
		 jsonobj.put("activatedKPI", activatedKPI);
		 jsonobj.put("deactivatedKPI", deactivatedKPI);
		 jsonobj.put("schemeData", schemeData);
		 //jsonobj.put("schemeDataUpto", schemeDataUpto);
		 jsonobj.put("schemeDataUpto", schemeDataUpto);
		 jsonobj.put("schemeDataPendency", schemeDataPendency);
		 jsonobj.put("schemeReviewed", schemeReviewed);
		 //jsonobj.put("schemePendingForReview", schemePendingForReview);
		 jsonobj.put("schemePendingForReview", schemePendingForReview);
		 //jsonobj.put("schemeIntegrationErrors", schemeIntegrationErrors);
		 jsonobj.put("schemeIntegrationErrors", 0);
		// System.out.println(jsonobj);
		 jsonobj.put("rejectedScheme", rejectedScheme);
		 jsonobj.put("pendingScheme", pendingScheme);
		return jsonobj.toString();
    }
	@PostMapping("home")
	@ResponseBody
    @CrossOrigin
    public String homePageInfo()
    {
		int registeredScheme  = countService.countRegisteredScheme();
		int activatedScheme = countService.countActivatedScheme();
		int deactivatedScheme = countService.countDeactivatedScheme();
		int registeredKPI = countService.countRegisteredKPI();
		int activatedKPI = countService.countActivatedKPI();
		int deactivatedKPI = countService.countDeactivatedKPI();
		int schemeData = countService.countSchemeData();
		int schemeDataUpto = countService.countSchemeDataUpto();
		int schemeDataPendency = countService.countSchemeDataPendency();
		
		int schemeReviewed = countService.countSchemeReviewed();
		int schemePendingForReview = countService.countSchemePendingForReview();
		int schemeIntegrationErrors = countService.countSchemeIntegrationErrors();
		
		 JSONObject jsonobj = new JSONObject();
		 jsonobj.put("registeredScheme", registeredScheme);
		 jsonobj.put("activatedScheme", activatedScheme);
		 jsonobj.put("deactivatedScheme", deactivatedScheme);
		 jsonobj.put("registeredKPI", registeredKPI);
		 jsonobj.put("activatedKPI", activatedKPI);
		 jsonobj.put("deactivatedKPI", deactivatedKPI);
		 jsonobj.put("schemeData", schemeData);
		 jsonobj.put("schemeDataUpto", schemeDataUpto);
		 jsonobj.put("schemeDataPendency", schemeDataPendency);
		 jsonobj.put("schemeReviewed", schemeReviewed);
		 jsonobj.put("schemePendingForReview", schemePendingForReview);
		 jsonobj.put("schemeIntegrationErrors", schemeIntegrationErrors);
		return jsonobj.toString();
    }
	@GetMapping("viewActivatedScheme")
	@ResponseBody 	
    @CrossOrigin
	public String viewScheme(ModelMap map, HttpServletRequest request, HttpSession sessions)
	{
		//System.out.println("viewActivatedScheme calling");
		//map.addAttribute("data",dataService.findActivatedScheme(map, request, sessions));
		List<SchemeDetailsDTO> data =dataService.findAllScheme(1, map, request, sessions);
		data = data.stream().sorted(Comparator.comparing(SchemeDetailsDTO::getSchemeCode)).collect(Collectors.toList());
		
		//sort based on project code
		data.sort(Comparator.comparing(SchemeDetailsDTO :: getSchemeCode));
		
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
	@GetMapping("viewDeactivatedScheme")
	@ResponseBody
    @CrossOrigin
	public String viewDectivatedScheme(ModelMap map, HttpServletRequest request, HttpSession sessions)
	{
		//map.addAttribute("data",dataService.findActivatedScheme(map, request, sessions));
		List<SchemeDetailsDTO> data =dataService.findAllScheme(0, map, request, sessions);
		data = data.stream().sorted(Comparator.comparing(SchemeDetailsDTO::getSchemeCode)).collect(Collectors.toList());
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
			 if(s.getIsRejected() == 0)
			 {
				 levelList.put(jsonobj);
			 }
		}
		return levelList.toString();
	}
	
	@GetMapping("viewSchemeById")
	@ResponseBody
    @CrossOrigin
	public String viewSchemeById(@RequestParam("id") String id, ModelMap map, HttpServletRequest request, HttpSession sessions)
	{
		//System.out.println("viewActivatedScheme calling");
		//map.addAttribute("data",dataService.findActivatedScheme(map, request, sessions));
		List<SchemeDetailsDTO> data =dataService.findAllScheme(1, map, request, sessions);
		JSONArray levelList = new JSONArray();
		int sn = 1;
		for(SchemeDetailsDTO s : data)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sn", sn++);
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
	@GetMapping("findSchemeById")
	@ResponseBody
    @CrossOrigin
    public String findById(@RequestParam("id") String id,  HttpServletRequest request, ModelMap map, HttpSession session)
    {
		SchemeDetailsDTO data =dataService.findById(Integer.parseInt(id), map, request, session);
		int sn = 1;
		 JSONObject jsonobj = new JSONObject();
		 jsonobj.put("sn", sn++);
		 jsonobj.put("id", data.getId());
		 jsonobj.put("ministryCode", data.getMinistryCode());
		 jsonobj.put("ministryName",data.getMinistryName());
		 jsonobj.put("departmentCode", data.getDepartmentCode());
		 jsonobj.put("departmentName",data.getDepartmentName());
		 jsonobj.put("schemeName", data.getSchemeName());
		 jsonobj.put("schemeCode",data.getSchemeCode());
		 jsonobj.put("cordEmail",data.getCordEmail());
		 jsonobj.put("headEmail",data.getHeadEmail());
		 jsonobj.put("adminEmail",data.getAdminEmail());
		 return jsonobj.toString();
    }
	@GetMapping("viewActiveSchemePagination")
	public String viewActiveSchemePagination(@RequestParam("p") int p, HttpServletRequest request, ModelMap map, HttpSession session)
	{
		
		map.addAttribute("data", dataService.findAllSchemePagination(p, request, map, session));
		
		return "listOfScheme";
	}
	@GetMapping("viewDeactiveSchemePagination")
	public String viewDeactiveSchemePagination(@RequestParam("p") int p, HttpServletRequest request, ModelMap map, HttpSession session)
	{
		
		map.addAttribute("data", dataService.findAllSchemePagination(p, request, map, session));
		
		return "listDeactiveScheme";
	}
	@GetMapping("countScheme")
	@ResponseBody
    @CrossOrigin
    public String countScheme()
    {
		
		int registeredScheme  = countService.countRegisteredScheme();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("registeredScheme", registeredScheme);
		 return jsonobj.toString();
		
    }
	@GetMapping("countActivatedScheme")
	@ResponseBody
    @CrossOrigin
    public int countActivatedScheme()
    {
		int activatedScheme = countService.countActivatedScheme();
		return activatedScheme;
    }
	@GetMapping("countDeactivatedScheme")
	@ResponseBody
    @CrossOrigin
    public int countInactivatedScheme()
    {
		int deactivatedScheme = countService.countDeactivatedScheme();
		return deactivatedScheme;
    }
	@GetMapping("countRejectedScheme")
	@ResponseBody
    @CrossOrigin
    public int countRejectedScheme()
    {
		int countRejectedScheme = countService.countRejectedScheme();
		
		return countRejectedScheme;
    }@GetMapping("countSubmittedScheme")
	@ResponseBody
    @CrossOrigin
    public int countSubmittedScheme()
    {
		int submittedScheme = countService.countSubmittedScheme();
		
		return submittedScheme;
    }
	
	
	@GetMapping("countActivatedKPI")
	@ResponseBody
    @CrossOrigin
    public int countActivatedKPI()
    {
		int activatedKPI = countService.countActivatedKPI();
		//System.out.println("activatedKPI = "+activatedKPI);
		return activatedKPI;
    }
	@GetMapping("countDeactivatedKPI")
	@ResponseBody
    @CrossOrigin
    public int countDeactivatedKPI()
    {
		int deactivatedKPI = countService.countDeactivatedKPI();
		return deactivatedKPI;
    }
	
	@GetMapping("viewActivatedKPI")
	@ResponseBody
    @CrossOrigin
	public String viewKPI(ModelMap map, HttpServletRequest request, HttpSession sessions)
	{
		List<KPIDetailsDTO> data =dataService.findAllKPI(1, map, request, sessions);
		data = data.stream().sorted(Comparator.comparing(KPIDetailsDTO::getKpiId)).collect(Collectors.toList());
		data = data.stream().sorted(Comparator.comparing(KPIDetailsDTO::getSchemeCode)).collect(Collectors.toList());
		
		//data.sort((KPIDetailsDTO rt1, KPIDetailsDTO rt2)->rt2.getSchemeCode()-rt1.getSchemeCode());
		
		  JSONArray levelList = new JSONArray(); int sn = 1;
		  
		  for(KPIDetailsDTO s : data) 
		  { 
			
			 
			  JSONObject jsonobj = new JSONObject();
			  jsonobj.put("sn", sn++); 
			  jsonobj.put("kpiId", s.getKpiId());
			  jsonobj.put("ministryCode", s.getMinistryCode());
			  jsonobj.put("ministryName",s.getMinistryName());
			  jsonobj.put("deptName", s.getDeptName());
			  jsonobj.put("deptCode", s.getDeptCode());
			  jsonobj.put("kpiName", s.getKpiName());
			  jsonobj.put("toolTip",s.getTooltip());
			  jsonobj.put("schemeName", s.getSchemeName());
			  jsonobj.put("schemeCode",s.getSchemeCode());
		  //System.out.println(jsonobj);
			  levelList.put(jsonobj); 
			  
		 } 
		 // System.out.println("ASHU");
		  return levelList.toString();
		 
		
	}
	@GetMapping("viewDeactivatedKPI")
	@ResponseBody
    @CrossOrigin
	public String viewDectivatedKPI(ModelMap map, HttpServletRequest request, HttpSession sessions)
	{
		//map.addAttribute("data",dataService.findActivatedScheme(map, request, sessions));
		List<KPIDetailsDTO> data =dataService.findAllKPI(0, map, request, sessions); 
		data = data.stream().sorted(Comparator.comparing(KPIDetailsDTO::getKpiId)).collect(Collectors.toList());
		data = data.stream().sorted(Comparator.comparing(KPIDetailsDTO::getSchemeCode)).collect(Collectors.toList());
		
		  JSONArray levelList = new JSONArray(); 
		  int sn = 1;
		  for(KPIDetailsDTO s : data) 
		  { 
			 
			  JSONObject jsonobj = new JSONObject();
			  jsonobj.put("sn", sn++); 
			  jsonobj.put("kpiId", s.getKpiId());
			  jsonobj.put("ministryCode", s.getMinistryCode());
			  jsonobj.put("ministryName",s.getMinistryName());
			  jsonobj.put("deptName", s.getDeptName());
			  jsonobj.put("deptCode", s.getDeptCode());
			  jsonobj.put("kpiName", s.getKpiName());
			  jsonobj.put("toolTip",s.getTooltip());
			  jsonobj.put("schemeName", s.getSchemeName());
			  jsonobj.put("schemeCode",s.getSchemeCode());
		  //System.out.println(jsonobj);
			  levelList.put(jsonobj); 
			  
		 } 
		return levelList.toString();
	}
	
	@GetMapping("getProjectDataLevel")
	@ResponseBody
    @CrossOrigin
	public String getProjectDataLevel(@RequestParam("id") int id) throws ParseException
	{
		RecvApiDataLogsToDisplay log = displayRepo.findById(id);
		
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(log.getProjectCode());
		//get Data Level
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("dataLevel", proj.getProject_Data_Lvl_Code());
		//get Ministry Code
		return jsonobj.toString();
		
		
	}
	@GetMapping("getKpiDetails")
	@ResponseBody
    @CrossOrigin
	public String getKpiDetails(@RequestParam("id") int id) throws ParseException
	{
		RecvApiDataLogsToDisplay log = displayRepo.findById(id);
		
		List<Tbl_Project_KPI_Detail_Intrim> kpiDet= kpiDetRepo.findByProjectCode(log.getProjectCode());
		JSONArray levelList = new JSONArray(); 
		for(Tbl_Project_KPI_Detail_Intrim s : kpiDet) 
		{ 
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("kpiName",s.getKpi_name_e()); 
			jsonobj.put("kpiDataDisplayType", s.getKpi_data_display_type_name());

			levelList.put(jsonobj); 
			
		}
		
		
		return levelList.toString();
	}
	@GetMapping("getFromAndToDateAndSchemeName")
	@ResponseBody
    @CrossOrigin
	public String getFromAndToDateAndSchemeName(@RequestParam("id") int id)
	{
		RecvApiDataLogsToDisplay log = displayRepo.findById(id);
		Tbl_Project_Detail_Intrim projDet =  projRepo.findByProjectCode(log.getProjectCode());
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("schemeName", projDet.getProject_name_e()); 
		jsonobj.put("fromDate", log.getPrevMonthFirstDate()); 
		jsonobj.put("toDate", log.getPrevMonthLastDate());
		
		
		return jsonobj.toString();
	}
	@GetMapping("viewData")
	@ResponseBody
    @CrossOrigin
	public String viewData(@RequestParam("id") int id) throws ParseException
	{
		//All data from kpi_ported_data_replica table
		RecvApiDataLogsToDisplay log = displayRepo.findById(id);
		Tbl_Project_Detail_Intrim projDet =  projRepo.findByProjectCode(log.getProjectCode());
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
	    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
	    
		int freqId = projDet.getPortingFrequency();
		
	    if(freqId == 1)
	    {
	    	//daily
	    	java.util.Date date = inputFormat.parse(log.getPrevMonthFirstDate());
            String formattedDate = outputFormat.format(date);
            Date sqlDate = Date.valueOf(formattedDate);
            relpicaRepo.findByDate(log.getProjectCode(), sqlDate);
	    }
	    if(freqId == 2)
	    {
	    	 //weekly-get week 1st date and last date
	    }
	    if(freqId == 3)
	    {
	    	 //monthly
	    }
	    if(freqId == 4)
	    {
	    	//quarterly
	    }
	    if(freqId == 5)
	    {
	    	 //half yearly(Apr-Sept)(Oct-March) oct/apr
	    }
	    if(freqId == 6)
	    {
	    	 //yearly
	    }
	    JSONObject jsonobj = new JSONObject();
		jsonobj.put("projectCode", log.getProjectCode()); 
		return jsonobj.toString();
		
	}
	@GetMapping("viewRawData")
	@ResponseBody
    @CrossOrigin
	public String viewLogData(@RequestParam("schemeCode") int schemeCode, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) throws ParseException
	{
		//All data from kpi_ported_data_replica table
		//RecvApiDataLogsToDisplay log = displayRepo.findById(id);
		//Tbl_Project_Detail_Intrim projDet =  projRepo.findByProjectCode(log.getProjectCode());
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    
	    // Parse the input date string
	    LocalDate date = LocalDate.parse(fromDate, inputFormatter);
	    
	    // Define the formatter for the output date format
	    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    
	    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
	    fromDate =  date.format(outputFormatter);
	    date = LocalDate.parse(toDate, inputFormatter);
	    toDate = date.format(outputFormatter);;
	   
	    JSONObject jsonobj = new JSONObject();
		jsonobj.put("schemeCode", schemeCode); 

		return jsonobj.toString();
		
	}
	@GetMapping("getDataLevel")
	@ResponseBody
    @CrossOrigin
	public String getDataLevel(@RequestParam("projectCode") int projectCode)
	{
		//get project details
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(projectCode);
		//get Data Level
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("dataLevel", proj.getProject_Data_Lvl_Code());
		//get Ministry Code
		return jsonobj.toString();
	}
	
	@GetMapping("getCurrentPushedData")
	@ResponseBody
    @CrossOrigin
	public String getCurrentPushedData(@RequestParam("projectCode") int projectCode)
	{
		Date maxdate = dataLogRepo.getMaxEntryDate(projectCode);
		List<RecvApiDataLogs> lastPushedDate = dataLogRepo.findByProjectCodeAndEntryDate(projectCode, maxdate);
		JSONArray levelList = new JSONArray(); 
		for(RecvApiDataLogs log : lastPushedDate) 
		{ 
			
			JSONObject jsonobj = new JSONObject();
			
			jsonobj.put("ministryCode", log.getMinistry_Code());
			jsonobj.put("ministryName", levelRepo.getMinistryName(log.getMinistry_Code(), 22));
			
			jsonobj.put("deptName", log);
			//get Department Code
			jsonobj.put("deptCode", log.getDept_Code());
			jsonobj.put("deptName", levelRepo.getDepartmentName(log.getDept_Code(), 23));
			jsonobj.put("projCode", projectCode);
			jsonobj.put("projName", projRepo.findByProjectCode(projectCode).getProject_name_e());
			jsonobj.put("kpiId", log.getKpiId());
			jsonobj.put("kpiName", kpiDetRepo.findByKpiId(log.getKpiId()));
			jsonobj.put("fromDate", log.getData_From_Date());
			jsonobj.put("toDate", log.getData_To_Date());
			
			jsonobj.put("level1Code", log.getLevel1_Code());
			jsonobj.put("level1Name", "India");
			
			jsonobj.put("level2Code", log.getLevel2_Code());
			jsonobj.put("level2Name", levelRepo.getDistinctState(log.getLevel2_Code(), 2));
			
			jsonobj.put("level3Code", log.getLevel3_Code());
			jsonobj.put("level3Name", levelRepo.getDistinctDistrict(log.getLevel3_Code(), 3));
			
			jsonobj.put("level4Code", log.getLevel4_Code());
			jsonobj.put("level4Name", levelRepo.getDistinctBlock(log.getLevel4_Code(), 4));
			
			jsonobj.put("level5Code", log.getLevel5_Code());
			jsonobj.put("level5Name", levelRepo.getDistinctVillage(log.getLevel5_Code(), 25));
			
		/*	jsonobj.put("level6Code", log.getLevel6_Code());
			jsonobj.put("level6Name", log.getLevel6_Code());
			
			jsonobj.put("level7Code", log.getLevel7_Code());
			jsonobj.put("level7Name", log.getLevel7_Code());
			
			jsonobj.put("level8Code", log.getLevel8_Code());
			jsonobj.put("level8Name", log.getLevel8_Code());
			
			jsonobj.put("level9Code", log.getLevel9_Code());
			jsonobj.put("level9Name", log.getLevel9_Code());
			
			jsonobj.put("level10Code", log.getLevel10_Code());
			jsonobj.put("level10Name", log.getLevel10_Code());
		*/
			levelList.put(jsonobj); 
		}
		return levelList.toString();
	}
	@GetMapping("dashRepo")
	public String dashRepo()
	{
		
		  
		return dashRepo.getData().toString();
	}
	@PostMapping("kpiDataInJson")
	@ResponseBody
    @CrossOrigin
	public String getKpiJsonData(@RequestBody Object obj)
	{
		
		 
		return "sucess";
	}
	
	
}
