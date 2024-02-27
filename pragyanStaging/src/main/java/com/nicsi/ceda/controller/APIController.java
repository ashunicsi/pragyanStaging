package com.nicsi.ceda.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import com.nicsi.ceda.model.Category;
import com.nicsi.ceda.model.DataCalendarType;
import com.nicsi.ceda.model.DataFrequency;
import com.nicsi.ceda.model.DataGranularityDetails;
import com.nicsi.ceda.model.DataPortingMode;
import com.nicsi.ceda.model.DataPortingTime;
import com.nicsi.ceda.model.DepartmentDTO;
import com.nicsi.ceda.model.KPIDataDisplayType;
import com.nicsi.ceda.model.ListOfMinistry;
import com.nicsi.ceda.model.ListOfOrganisation;
import com.nicsi.ceda.model.MKPIValidationOperator;
import com.nicsi.ceda.model.MKPIValidationRuleType;
import com.nicsi.ceda.model.MLevel;
import com.nicsi.ceda.model.MSchemeDepMinistrySecMap;
import com.nicsi.ceda.model.MUnit;
import com.nicsi.ceda.model.M_Department;
import com.nicsi.ceda.model.PortingLanguage;
import com.nicsi.ceda.model.Registration;
import com.nicsi.ceda.model.Sector;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.model.TestTest;
import com.nicsi.ceda.repository.CategoryRepo;
import com.nicsi.ceda.repository.DataCalendarTypeRepo;
import com.nicsi.ceda.repository.DataFrequencyRepo;
import com.nicsi.ceda.repository.DataGranularityDetailsRepo;
import com.nicsi.ceda.repository.DataPortingModeRepo;
import com.nicsi.ceda.repository.DataPortingTimeRepo;
import com.nicsi.ceda.repository.DepartmentRepo;
import com.nicsi.ceda.repository.KPIDataDisplayTypeRepo;
import com.nicsi.ceda.repository.ListOfOrganisationRepo;
import com.nicsi.ceda.repository.MKPIValidationOperatorRepo;
import com.nicsi.ceda.repository.MKPIValidationRuleTypeRepo;
import com.nicsi.ceda.repository.MLevelRepo;
import com.nicsi.ceda.repository.MSchemeDepMinistrySecMapRepo;
import com.nicsi.ceda.repository.MUnitRepo;
import com.nicsi.ceda.repository.MinistryRepo;
import com.nicsi.ceda.repository.PortingLanguageRepo;
import com.nicsi.ceda.repository.RegistrationRepo;
import com.nicsi.ceda.repository.SectorRepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
//@Controller
public class APIController 
{
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
	private CategoryRepo categoryRepo;
	@Autowired
	private KPIDataDisplayTypeRepo kpiDataRepo;
	@Autowired
	private DataPortingModeRepo portingModeRepo;
	@Autowired
	private DataPortingTimeRepo portingTimeRepo;
	@Autowired
	private DataFrequencyRepo dataFreqRepo;
	@Autowired
	private RegistrationRepo regRepo;
	@Autowired
	private MUnitRepo unitRepo;
	@Autowired
	private MKPIValidationOperatorRepo validatorRepo;
	@Autowired
	private MKPIValidationRuleTypeRepo ruleTypeRepo;
	@Autowired
	private DataCalendarTypeRepo dataRepo;
	@Autowired
	private Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private PortingLanguageRepo portRepo;
	
	
	
	@GetMapping("getAllSector")
	@ResponseBody
    @CrossOrigin
	public String getAllSector(ModelMap map, @RequestParam String language)
	{
		List<Sector> sector = sectorRepo.findByStatus(1);
		//System.out.println(sector);
		sector = sector.stream().sorted(Comparator.comparing(Sector::getSector_Name_e)).collect(Collectors.toList());
		JSONArray levelList = new JSONArray();
		for(Sector s : sector)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("sectorCode", s.getId());
			 if(language.equals("en"))
			 {
				 jsonobj.put("sectorName",s.getSector_Name_e());
			 }
			 else
			 {
				 jsonobj.put("sectorName",s.getSector_Name_r());
			 }
			//System.out.println(jsonobj);
			 levelList.put(jsonobj);
		}
		
		return levelList.toString();
	}
	@GetMapping("getMinistry")
	@ResponseBody
    @CrossOrigin
	public String getSector(ModelMap map, @RequestParam("sectorId") String sectorId)
	{
		List<M_Department> dept = deptRepo.findAllMinistryWhereSectorId(Integer.parseInt(sectorId));
		List<DepartmentDTO> deptDTO = new  ArrayList<DepartmentDTO>();
		for(M_Department d : dept)
		{
			DepartmentDTO dto = new DepartmentDTO();
			dto.setMinistryId(d.getMinistryId());
			dto.setMinistryName(d.getMinistry_Name_e());
			deptDTO.add(dto);
		}
		Collection<DepartmentDTO> nonDuplicateCollection = deptDTO.stream()
		        .collect(Collectors.toMap(DepartmentDTO::getMinistryId, Function.identity(), (a, b) -> a))
		        .values();
		JSONArray deptList = new JSONArray();
		for(DepartmentDTO d : nonDuplicateCollection)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("ministryCode", d.getMinistryId());
			 jsonobj.put("ministryName",d.getMinistryName());
			 deptList.put(jsonobj);
		}
		return deptList.toString();
	}
	
	@GetMapping("getAllMinistry")
	@ResponseBody
    @CrossOrigin
	public String getAllMinistry(ModelMap map, @RequestParam("language") String language)
	{
		/*List<ListOfMinistry> ministry = minRepo.findByStatusAndLanguage(1, language);
		
		ministry = ministry.stream().sorted(Comparator.comparing(ListOfMinistry::getMinistryName)).collect(Collectors.toList());
		JSONArray minList = new JSONArray();
		for(ListOfMinistry min : ministry)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("id", min.getMId());
			 jsonobj.put("ministryName",min.getMinistryName());
			 minList.put(jsonobj);
		}
		*/
		List<MLevel> level = levelRepo.findByDataGranularityId(22);
		level = level.stream().sorted(Comparator.comparing(MLevel::getLevel2NameE)).collect(Collectors.toList());
		JSONArray levelList = new JSONArray();
		for(MLevel l : level)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("ministryCode", l.getLevel2Code());
			 jsonobj.put("ministryName",l.getLevel2NameE());
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("getDepartment")
	@ResponseBody
    @CrossOrigin
	public String getDepartment(ModelMap map, @RequestParam("ministryId") String id)
	{
		
		List<M_Department> dept = deptRepo.findByMinistryId(Integer.parseInt(id));
		//System.out.println(dept);
		dept = dept.stream().sorted(Comparator.comparing(M_Department::getDepertment_Name_e)).collect(Collectors.toList());
		JSONArray deptList = new JSONArray();
		for(M_Department d : dept)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("departmentCode", d.getDepertment_ID());
			 jsonobj.put("departmentName",d.getDepertment_Name_e());
			 deptList.put(jsonobj);
		}
		
		return deptList.toString();
		
	}
	@GetMapping("getOrganisation")
	@ResponseBody
    @CrossOrigin
	public String getOrganisation(ModelMap map, @RequestParam("ministryId") String id)
	{
		List<M_Department> dept = deptRepo.findByMinistryId(Integer.parseInt(id));
		dept = dept.stream().sorted(Comparator.comparing(M_Department::getDepertment_Name_e)).collect(Collectors.toList());
		JSONArray deptList = new JSONArray();
		for(M_Department d : dept)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("id", d.getDepertment_ID());
			 jsonobj.put("departmentName",d.getDepertment_Name_e());
			 deptList.put(jsonobj);
		}
		return deptList.toString();
		
	}
	@GetMapping("getOrg")
	@ResponseBody
    @CrossOrigin
	public String getOrg(ModelMap map, @RequestParam("departmentCode") String id)
	{//System.out.println("Department ID = "+id);
		List<M_Department> dept = deptRepo.findByMinistryId(Integer.parseInt(id));
		dept = dept.stream().sorted(Comparator.comparing(M_Department::getDepertment_Name_e)).collect(Collectors.toList());
		JSONArray deptList = new JSONArray();
		for(M_Department d : dept)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("orgCode", d.getDepertment_ID());
			 jsonobj.put("organisationName",d.getDepertment_Name_e());
			 deptList.put(jsonobj);
		}
		return deptList.toString();
		
	}
	@GetMapping("getScheme")
	@ResponseBody
    @CrossOrigin
    public String getScheme(ModelMap map, @RequestParam("sectorId") String sectorId)
    {
		List<MSchemeDepMinistrySecMap> scheme = schemeRepo.findByMinistryId(Integer.parseInt(sectorId));
		scheme = scheme.stream().sorted(Comparator.comparing(MSchemeDepMinistrySecMap::getSchemeName)).collect(Collectors.toList());
		JSONArray deptList = new JSONArray();
		for(MSchemeDepMinistrySecMap d : scheme)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("deptCode", d.getNewDepCode());
			 jsonobj.put("departmentName",d.getNewDepName());
			 deptList.put(jsonobj);
		}
		return deptList.toString();
		
    }
	@GetMapping("Organisation")
	@ResponseBody
    @CrossOrigin
	public String organisation(ModelMap map, @RequestParam("language") String language)
	{
		List<ListOfOrganisation> org = orgRepo.findByStatusAndLanguage(1, language);
		org = org.stream().sorted(Comparator.comparing(ListOfOrganisation::getOrganisationName)).collect(Collectors.toList());
		JSONArray orgList = new JSONArray();
		for(ListOfOrganisation o : org)
		{
			
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("id", o.getOId());
				jsonobj.put("departmentName",o.getOrganisationName());
				orgList.put(jsonobj);
			
		}
		
		return orgList.toString();
	}
	
	@GetMapping("getState")
	@ResponseBody
    @CrossOrigin
    public String getState(ModelMap map)
    {
		//System.out.println("getState");
		List<MLevel> level = levelRepo.findByDataGranularityId(2);
		level = level.stream().sorted(Comparator.comparing(MLevel::getLevel2NameE)).collect(Collectors.toList());
		
		JSONArray mList = new JSONArray();
		for(MLevel m : level)
		{
			
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("stateId", m.getLevel2Code());
				jsonobj.put("stateName",m.getLevel2NameE());
				mList.put(jsonobj);
			
		}
		
		return mList.toString();
    }
	@GetMapping("getDistrict")
	@ResponseBody
    @CrossOrigin
    public String getDistrict(ModelMap map, @RequestParam("stateId") String stateId)
    {
		List<MLevel> level = levelRepo.getDistrict(Integer.parseInt(stateId), 3);
		//System.out.println(level);
		level = level.stream().sorted(Comparator.comparing(MLevel::getLevel3NameR)).collect(Collectors.toList());
		
		JSONArray mList = new JSONArray();
		for(MLevel m : level)
		{
			
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("districtId", m.getLevel3Code());
				jsonobj.put("districtName",m.getLevel3NameR());
				mList.put(jsonobj);
			
		}
		
		return mList.toString();
    }
	@GetMapping("getBlock")
	@ResponseBody
    @CrossOrigin
    public String getBlock(ModelMap map, @RequestParam("districtId") String districtId)
    {
		List<MLevel> level = levelRepo.getBlock(Integer.parseInt(districtId), 4);
		level = level.stream().sorted(Comparator.comparing(MLevel::getLevel4NameE)).collect(Collectors.toList());
		
		JSONArray mList = new JSONArray();
		for(MLevel m : level)
		{
			
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("blockId", m.getLevel4Code());
				jsonobj.put("blockName",m.getLevel4NameE());
				mList.put(jsonobj);
			
		}
		
		return mList.toString();
    }
	@GetMapping("getVillage")
	@ResponseBody
    @CrossOrigin
    public String getPanchayat(ModelMap map, @RequestParam("blockId") String blockId)
    {
		List<MLevel> level = levelRepo.getVillage(Integer.parseInt(blockId), 25);
		
		level = level.stream().sorted(Comparator.comparing(MLevel::getLevel5NameE)).collect(Collectors.toList());
		
		JSONArray mList = new JSONArray();
		for(MLevel m : level)
		{
			
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("villageId", m.getLevel5Code());
				jsonobj.put("villageName",m.getLevel5NameE());
				mList.put(jsonobj);
			
		}
		
		return mList.toString();
    }
	
	@GetMapping("getDistrictPanchayat")
	@ResponseBody
    @CrossOrigin
    public String getDistrictPanchayat(ModelMap map, @RequestParam("statePanchayatId") String statePanchayatId)
    {
		List<MLevel> level = levelRepo.getDistrictPanchayat(Integer.parseInt(statePanchayatId), 19);
		level = level.stream().sorted(Comparator.comparing(MLevel::getLevel5NameE)).collect(Collectors.toList());
		
		JSONArray mList = new JSONArray();
		for(MLevel m : level)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("districtPanchayatId", m.getLevel5Code());
			jsonobj.put("districtPanchayat",m.getLevel5NameE());
			mList.put(jsonobj);
			
		}
		
		return mList.toString();
    }
	
	@GetMapping("getGramPanchayat")
	@ResponseBody
    @CrossOrigin
    public String getVillage(ModelMap map, @RequestParam("blockPanchayatId") String blockPanchayatId)
    {
		List<MLevel> level = levelRepo.getGramPanchayat(Integer.parseInt(blockPanchayatId), 19);
		level = level.stream().sorted(Comparator.comparing(MLevel::getLevel5NameE)).collect(Collectors.toList());
		
		JSONArray mList = new JSONArray();
		for(MLevel m : level)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("panchayatId", m.getLevel5Code());
			jsonobj.put("gramPanchayat",m.getLevel5NameE());
			mList.put(jsonobj);
			
		}
		
		return mList.toString();
    }
	@GetMapping("getDataGranularityDetails")
	@ResponseBody
    @CrossOrigin
    public String getDataGranularityDetails(ModelMap map, @RequestParam String language)
    {
		List<DataGranularityDetails> data = granularityRepo.findByStatusAndIsFixed(1, 1);
		data = data.stream().sorted(Comparator.comparing(DataGranularityDetails::getData_Granularity_Name_e)).collect(Collectors.toList());
		
		JSONArray dList = new JSONArray();
		for(DataGranularityDetails d : data)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("dataGranularityId", d.getDataGranularityId());
			jsonobj.put("dataGranularityName",d.getData_Granularity_Name_e());
			dList.put(jsonobj);
			
		}
		//System.out.println(dList.toString());
		return dList.toString();
    }
	@GetMapping("getCategory")
	@ResponseBody
    @CrossOrigin
    public String getCategory(@RequestParam String language)
    {
		List<Category> cat = categoryRepo.findByStatus(1);
		//Other should be in last only
		//cat = cat.stream().sorted(Comparator.comparing(Category::getCategory_e)).collect(Collectors.toList());
		cat = cat.stream().sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId())).collect(Collectors.toList());
		JSONArray list = new JSONArray();
		for(Category c : cat)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("categoryId", c.getId());
			if(language.equals("en"))
			{
				jsonobj.put("categoryName",c.getCategory_e());
			}
			else
			{
				jsonobj.put("categoryName",c.getCategory_r());
			}
			list.put(jsonobj);
			
		}
		return list.toString();
    }
	@GetMapping("getDataCalendarType")
	@ResponseBody
    @CrossOrigin
    public String getDataCalendarType(@RequestParam String language)
    {
		List<DataCalendarType> data = dataRepo.findByStatus(1);
		//System.out.println(data);
		JSONArray list = new JSONArray();
		for(DataCalendarType s : data)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("calenderTypeId", s.getData_Calendar_Type_Id());
			if(language.equals("en"))
			{
				jsonobj.put("calenderTypeName",s.getDataCalendarTypeName());
			}
			else
			{
				jsonobj.put("calenderTypeName",s.getDataCalendarTypeNameR());
			}
			list.put(jsonobj);
			
		}
		return list.toString();
    }
	
	@GetMapping("getKPIDataDisplay")
	@ResponseBody
    @CrossOrigin
    public String getKPIDataDisplay(@RequestParam String language)
    {
		List<KPIDataDisplayType> kpi = kpiDataRepo.findByStatus(1);
		//System.out.println("KPI = "+kpi);
		//Other should be in last only
		//cat = cat.stream().sorted(Comparator.comparing(Category::getCategory_e)).collect(Collectors.toList());
		JSONArray list = new JSONArray();
		for(KPIDataDisplayType k : kpi)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("dataDisplayId", k.getKpi_data_display_type_id());
			if(language.equals("en"))
			{
				jsonobj.put("dataDisplayName",k.getKpi_data_display_type_name_e());
			}
			else
			{
				jsonobj.put("dataDisplayName",k.getKpi_data_display_type_name_r());
			}
			list.put(jsonobj);
			
		}
		return list.toString();
    }
	@GetMapping("getPortingMode")
	@ResponseBody
    @CrossOrigin
    public String getPortingMode(@RequestParam String language)
    {
		List<DataPortingMode> mode = portingModeRepo.findByStatus(1);
		//System.out.println("KPI = "+kpi);
		//Other should be in last only
		//cat = cat.stream().sorted(Comparator.comparing(Category::getCategory_e)).collect(Collectors.toList());
		JSONArray list = new JSONArray();
		for(DataPortingMode d : mode)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("portingModeId", d.getId());
			if(language.equals("en"))
			{
				jsonobj.put("portingModeName",d.getPortingModeEn());
			}
			else
			{
				jsonobj.put("portingModeName",d.getPortingModeLl());
			}
			list.put(jsonobj);
			
		}
		return list.toString();
    }
	@GetMapping("getPortingTime")
	@ResponseBody
    @CrossOrigin
    public String getPortingTime(@RequestParam String language)
    {
		List<DataPortingTime> data = portingTimeRepo.findByStatus(1);
		//System.out.println("KPI = "+kpi);
		//Other should be in last only
		//cat = cat.stream().sorted(Comparator.comparing(Category::getCategory_e)).collect(Collectors.toList());
		JSONArray list = new JSONArray();
		for(DataPortingTime d : data)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("portingTimeId", d.getId());
			if(language.equals("en"))
			{
				jsonobj.put("portingTimeName",d.getPortingTimeEn());
			}
			else
			{
				jsonobj.put("portingTimeName",d.getPortingTimeLl());
			}
			list.put(jsonobj);
			
		}
		return list.toString();
    }
	
	@GetMapping("getDataFrequency")
	@ResponseBody
    @CrossOrigin
	public String getDataFrequency(ModelMap map, @RequestParam("language") String language)
	{
		List<DataFrequency> data = dataFreqRepo.findByStatus(1);
		JSONArray list = new JSONArray();
		for(DataFrequency d : data)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("id", d.getId());
			jsonobj.put("frequencyName",d.getFrequencyNameE());
			list.put(jsonobj);
			
		}
		return list.toString();
	}
	/*
	 * @GetMapping("getDataFrequency")
	 * 
	 * @ResponseBody
	 * 
	 * @CrossOrigin public String getCategory(ModelMap
	 * map, @RequestParam("language") String language) { List<DataFrequency> data =
	 * dataFreqRepo.findByStatus(1); JSONArray list = new JSONArray();
	 * for(DataFrequency d : data) { JSONObject jsonobj = new JSONObject();
	 * jsonobj.put("id", d.getId());
	 * jsonobj.put("frequencyName",d.getFrequencyNameE()); list.put(jsonobj);
	 * 
	 * } return list.toString(); }
	 */
	
	
	@GetMapping("getUserDetails")
	@ResponseBody
    @CrossOrigin
	public String getUserDetails(ModelMap map, @RequestParam("username") String username)
	{
		//System.out.println("username = "+username);
		Registration reg = regRepo.findByEmail(username);
		//System.out.println("reg= "+reg);
		//System.out.println(reg);
		//System.out.println("Use Details = "+reg);
		int sectorCode = reg.getSectorCode();
		//System.out.println("secot code"+sectorCode);
		int ministryCode  = reg.getMinistryCode();
		int deptCode = reg.getDepartmentCode();
		//System.out.println(deptCode);
		JSONObject jsonobj = new JSONObject();
		
		Sector sector = sectorRepo.findBySectorId(sectorCode);	
		//System.out.println("Sector = "+sector);
		String ministryName = levelRepo.getMinistryName(ministryCode, 22);
		//System.out.println("ministryName = "+ministryName);
		String deptName = levelRepo.getDepartmentName(deptCode, 21);
		System.out.println("deptName = " +deptName);
		
		
		//System.out.println("deptName= "+deptName);
		
		
		jsonobj.put("sectorName", sector.getSector_Name_e());
		jsonobj.put("ministryName", ministryName);
		jsonobj.put("deptName", deptName);
		jsonobj.put("sectorCode", sectorCode);
		jsonobj.put("ministryCode", ministryCode);
		jsonobj.put("deptCode", deptCode);
		
		return jsonobj.toString();
	}
	
	@GetMapping("getSectorName")
	@ResponseBody
    @CrossOrigin
	public String getSectorName(ModelMap map, @RequestParam("sectorId") String sectorId)
	{
		Sector sector = sectorRepo.findBySectorId(Integer.parseInt(sectorId));
		//System.out.println(sector);
		//String ministryName = ministryName.get
		return sector.getSector_Name_e();
	}
	@GetMapping("getMinistryName")
	@ResponseBody
    @CrossOrigin
	public String getMinistryName(ModelMap map, @RequestParam("ministryId") String ministryId)
	{
		ListOfMinistry ministryName = minRepo.findByMinistryCode(ministryId);
		//System.out.println(ministryName);
		//String ministryName = ministryName.get
		return ministryName.getMinistryName();
	}
	@GetMapping("getDepartmentName")
	@ResponseBody
    @CrossOrigin
	public String getDepartmentName(ModelMap map, @RequestParam("deptId") String deptId)
	{
		//ListOfMinistry ministryName = minRepo.findByMinistryCode(deptId);
		//System.out.println(ministryName);
		//String ministryName = ministryName.get
		return "";
	}
	
	@GetMapping("getUnit")
	@ResponseBody
    @CrossOrigin
	public String getUnit()
	{
		//System.out.println("getUnit");
		List<MUnit> unit = unitRepo.findByStatus(1);
		unit = unit.stream().sorted(Comparator.comparing(MUnit::getUnit_name_e)).collect(Collectors.toList());
		JSONArray list = new JSONArray();
		for(MUnit m : unit)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("unitId", m.getUnitId());
			jsonobj.put("unitName",m.getUnit_name_e());
			list.put(jsonobj);
			
		}
		return list.toString();
		
	}
	@GetMapping("getRuleType")
	@ResponseBody
    @CrossOrigin
	public String getValidatorType()
	{
		List<MKPIValidationRuleType> ruleTypes = ruleTypeRepo.findByStatus(1);
		ruleTypes = ruleTypes.stream().sorted(Comparator.comparing(MKPIValidationRuleType::getRuleTypeName)).collect(Collectors.toList());
		
		JSONArray list = new JSONArray();
		for(MKPIValidationRuleType m : ruleTypes)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("ruleId", m.getRuleTypeId());
			jsonobj.put("ruleType",m.getRuleTypeName());
			list.put(jsonobj);
			
		}
		return list.toString();
	}
	@GetMapping("getValidator")
	@ResponseBody
    @CrossOrigin
	public String getOperator(@RequestParam("operatorType") int operatorType)
	{
		
		List<MKPIValidationOperator> operator = validatorRepo.findByOperatorType(operatorType);
		operator = operator.stream().sorted(Comparator.comparing(MKPIValidationOperator::getOperatorName)).collect(Collectors.toList());
		
		JSONArray list = new JSONArray();
		for(MKPIValidationOperator m : operator)
		{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("operatorId", m.getOperatorId());
			jsonobj.put("operatorName",m.getOperatorName());
			list.put(jsonobj);
			
		}
		return list.toString();
	}
	@GetMapping("getSchemeName")
	@ResponseBody
    @CrossOrigin
	public String getSchemeName(@RequestParam("schemeCode") int schemeCode)
	{
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(schemeCode);
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("schemeName",proj.getProject_name_e());
		return jsonobj.toString();
	}
	@GetMapping("getUsername")
	@ResponseBody
    @CrossOrigin
	public String getUsername(@RequestParam("projectCode") int projectCode)
	{
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCode(projectCode);
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("username",proj.getUsername());
		return jsonobj.toString();
	}
	@GetMapping("getPortingLanguage")
	@ResponseBody
    @CrossOrigin
	public String getPortingLanguage() throws JsonProcessingException
	{
		List<PortingLanguage> proj = portRepo.findByFlag(1);
		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(proj);

		return json;
	}
	@GetMapping("validateUsernameAndSchemeCode")
	public String validateUsernameAndSchemeCode(@RequestParam("schemeCode") int schemeCode, @RequestParam("username") String username)
	{
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCodeAndStatusAndUsername(schemeCode, 1, username);
		//System.out.println(" validateUsernameAndSchemeCode = "+proj);
		String status = "";
		if(proj == null)
		{
			status = "invalid";
		}
		else
		{
			status = "valid";
		}
		return status;
	}
	
	
	
	
}
