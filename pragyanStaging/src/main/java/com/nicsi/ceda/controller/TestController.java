package com.nicsi.ceda.controller;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicsi.ceda.model.CheckNullData;
import com.nicsi.ceda.model.DataGranularityDetails;
import com.nicsi.ceda.model.Dim_Time;
import com.nicsi.ceda.model.ListOfDepartment;
import com.nicsi.ceda.model.ListOfMinistry;
import com.nicsi.ceda.model.MLevelOld;
import com.nicsi.ceda.model.OldCodesOfStates;
import com.nicsi.ceda.model.Tbl_DataPort_KPI_PORTED_DATA;
import com.nicsi.ceda.repository.CheckNullDataRepo;
import com.nicsi.ceda.repository.CodesOfStatesRepo;
import com.nicsi.ceda.repository.DataFrequencyRepo;
import com.nicsi.ceda.repository.DataGranularityDetailsRepo;
import com.nicsi.ceda.repository.DepartmentRepo;
import com.nicsi.ceda.repository.Dim_TimeRepo;
import com.nicsi.ceda.repository.ListOfOrganisationRepo;
import com.nicsi.ceda.repository.MLevelRepo;
import com.nicsi.ceda.repository.MSchemeDepMinistrySecMapRepo;
import com.nicsi.ceda.repository.MinistryRepo;
import com.nicsi.ceda.repository.SectorRepo;
import com.nicsi.ceda.repository.Tbl_DataPort_KPI_PORTED_DATARepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.repository.Tbl_Project_KPI_Detail_IntrimRepo;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class TestController 
{
	@Autowired
	private CodesOfStatesRepo codeRepo;
	@Autowired
	private DataGranularityDetailsRepo dataRepo;
	@Autowired
	private MLevelRepo mLevelRepo;	
	@Autowired
	private Tbl_DataPort_KPI_PORTED_DATARepo repo;
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
	private Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private DataFrequencyRepo dataFrequencyRepo;
	@Autowired
	private Tbl_Project_KPI_Detail_IntrimRepo kpiDetailsRepo;
	@Autowired
	private Dim_TimeRepo dimRepo;
	
	
	
	@GetMapping("lastRecord")
	public String getLastRecord(ModelMap map)
	{
		Long id = repo.getMaxId();
		Tbl_DataPort_KPI_PORTED_DATA data = repo.byId(id);
		map.addAttribute("data", data);
		map.addAttribute("isData", 1);
		//Send data in new Table-Replica
		 /* KPI_PORTED_DATA_REPLICA replica = new KPI_PORTED_DATA_REPLICA();
		  int level1Code = data.getLevel1Code();
		  int level2Code = data.getLevel2Code();
		  int level3Code = data.getLevel3Code();
		  int level4Code = data.getLevel4Code();
		  int level5Code = data.getLevel5Code();
		  int level6Code = data.getLevel6Code();
		  int level7Code = data.getLevel7Code();
		  int level8Code = data.getLevel8Code();
		  int level9Code = data.getLevel9Code();
		  int level10Code = data.getLevel1Code();
		  Tbl_Project_Detail_Intrim projDetails = projRepo.findByProjectCode(Integer.valueOf(data.getProject_code().intValue()));
		  String countryName = "India";
		  String level2Name = levelRepo.getDistinctState(level2Code, projDetails.getProject_Data_Granularity_ID());//findDistinctByLevel2CodeAndDataGranularityId(1, 2);
		  String level3Name = levelRepo.getDistinctDistrict(level3Code, projDetails.getProject_Data_Granularity_ID());
		  String level4Name = levelRepo.getDistinctBlock(level4Code, projDetails.getProject_Data_Granularity_ID());
		  String level5Name = levelRepo.getDistinctGramPanchayat(level5Code, projDetails.getProject_Data_Granularity_ID());
		  String projName = projDetails.getProject_Name_E();
		  int modeFrequencyId = data.getMode_frequency_id();
		  Long freqId = (long) modeFrequencyId;
		  String frequencyName =  dataFrequencyRepo.getDataFrequencyName(freqId);
		  int kpiId = data.getKpi_id();
		  Tbl_Project_KPI_Detail_Intrim kpiDetails = kpiDetailsRepo.findByKpiId(data.getKpi_id());
		  //Date dataDate = ;
		  //int yr = ;
		  //int MMYYYY = ;
		 // int month = ;
		 // String monthName = ;

		  String indicator  = kpiDetails.getKPI_Name_E();
		  String indicatorNew =  kpiDetails.getKPI_Name_E();
		  //Double value = kpiDetails.get;
		  int label = data.getKpi_id();//KPIID
		  String unit = kpiDetails.getKPI_Unit_Name_E();
		 // String kpiCategoryNameE = kpiDetails.kpiCategoryNameE();
		  //int kpiTypeId = kpiDetails.getKPI_Data_Type_ID();
		  int kpiPercentageId = kpiDetails.getKPI_Percentage_ID();
		  
		  //String financialYear = ;
		  //int financialQrtr = ;
		  //int financialMonthSort = ;
		  
		  String description = projDetails.getProject_Description_E();
		 // String isCumulative = ;
		 // BigInteger  totalPopulation = ;
		  Timestamp darpanentrydt = data.getEntrydt();
		  Timestamp lastUpdated = data.getDatadt();
		  int ministryId = projDetails.getMinistry_Code();
		  String ministryName  = levelRepo.getMinistryName(ministryId, 22);
		  
		  Integer isDataReset = kpiDetails.getIs_Data_Reset();
		  int resetFrequencyId = kpiDetails.getData_Reset_Frequency_ID();
		  String resetFrequencyName = kpiDetails.getData_Reset_Frequency_Name();
		  int kpiCategoryId = kpiDetails.getKPI_Category_ID();
		  */
		return "lastRecord";
	}
	
	
	
	@GetMapping("get")
	public void getWebsiteData()
	{
		try
		{
			//org.jsoup.nodes.Document doc = Jsoup.connect("https://nicsi.com/rti/rti_details2530.html?page=14").get();
			//org.jsoup.nodes.Document doc = Jsoup.connect("C:/Users/Admin/Desktop/data.html").get();
			org.jsoup.nodes.Document doc = Jsoup.connect("https://lgdirectory.gov.in/globalviewstateforcitizen.do?OWASP_CSRFTOKEN=52GV-2468-GISJ-7PUS-DR3K-9WXJ-2RZV-PA9T#").get();
	        org.jsoup.select.Elements rows = doc.select("tr");
	        int i = 0;
	        int j = 1;
	        int k = 1;
	        OldCodesOfStates st = new OldCodesOfStates();
	        for(org.jsoup.nodes.Element row :rows)
	        {
	        	j++;
	            org.jsoup.select.Elements columns = row.select("td");
	            i = 1;
	            for (org.jsoup.nodes.Element column:columns)
	            {
	            	 if(i == 2)
	            	{
	            		st.setStateLGDCode(Integer.parseInt(column.text()));
	            	}
	            	else if(i == 3)
	            	{
	            		st.setStateNameInEnglish(column.text());
	            	}
	            	else if(i == 4)
	            	{
	            		st.setStateNameInLocalLanguage(column.text());
	            	}
	            	else if(i == 5)
	            	{
	            		st.setStateOrUT(column.text());
	            	}
	            	if(i == 11)
	            	{
	            		i = 1;
	            	}
	            	else
	            	{
	            		i++;
	            	}
	            }
	            if(st.getStateLGDCode() != 0)
	            {
	            	st.setId(st.getStateLGDCode());
	            	
	            	codeRepo.save(st);
	            	
	            }
	            
	        }
	       
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		
	}
	@GetMapping("readExcel")
	public String readExcelData()
	{
		
		try
		{
			FileInputStream input = new FileInputStream("C:\\Users\\Admin\\Desktop\\ListOfMin.xls");
			POIFSFileSystem fs = new POIFSFileSystem (input);
	        HSSFWorkbook wb = new HSSFWorkbook(fs);
	        HSSFSheet sheet = wb.getSheetAt(0);
	        ListOfMinistry min = new ListOfMinistry();
	        //Iterator rows = sheet.rowIterator(); 
	        /*for(int i = 0; i <= 61; i++)
	        {
	        	HSSFRow row = sheet.getRow(i);
	            double id = row.getCell(0).getNumericCellValue();
	            int mId = (int)id;
	            min.setMId(mId);
	            String mininstryName = row.getCell(1).getStringCellValue();
	            min.setMinistryName(mininstryName);
	            min.setLanguage("en");
	            min.setStatus(1);
	            
	            char minCode;
	            String ministryCode = "";
	            for (int j = 0; j < mininstryName.length(); j++) 
	            {
	                if(Character.isUpperCase(mininstryName.charAt(j)))
	                {    
	                	minCode = mininstryName.charAt(j);
	                	ministryCode = ministryCode + minCode;
	                }
	            }
	            min.setMinistryCode(ministryCode);
	            minRepo.save(min);
	        }
	        */
	        sheet = wb.getSheetAt(1);
	        //Iterator rows = sheet.rowIterator(); 
            int schemeId;
           
	        for(int k = 1; k <= 99; k++)
	        {
	        	HSSFRow row = sheet.getRow(k);
	        	double mId = row.getCell(0).getNumericCellValue();
	            int minId = (int)mId;
	           
	            ListOfMinistry ministry = minRepo.findBymId(minId);
	            //System.out.println(ministry);
	            ListOfDepartment dept = new ListOfDepartment();
	            
	        	double dId = row.getCell(1).getNumericCellValue();
	        	int id = (int) dId;
	        	dept.setDId(id);
	        	String departmentName = row.getCell(2).getStringCellValue();
	        	dept.setDepartmentName(departmentName);
	        	char minCode;
	            String departmentCode = "";
	            for (int j = 0; j < departmentName.length(); j++) 
	            {
	                
	                if(Character.isUpperCase(departmentName.charAt(j)))
	                {    
	                	minCode = departmentName.charAt(j);
	                	departmentCode = departmentCode + minCode;
	                }
	            }
	            dept.setDepartmentCode(departmentCode);
	            dept.setStatus(1);
	            dept.setLanguage("en");
	            dept.setDId(k);
	            ministry.getDepartment().add(dept);
	          
	            //minRepo.save(ministry);
	        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	@GetMapping("DataGranularityDetails")
	public List<DataGranularityDetails> getDataGranularityDetails()
	{
		
		return dataRepo.findAll();
	}
	@GetMapping("getMLevel")
	public void getMLevelData()
	{
		String uri = "http://localhost/get";
		RestTemplate temp = new RestTemplate();
		String jsondata = temp.getForObject(uri, String.class);
		//List<MLevel> level = (List<MLevel>) temp.getForObject(uri, MLevel.class);
		//System.out.println(level.size());
		//for(MLevel m : level)
		//{
			//System.out.println(m);
		//}
		//System.out.println(jsondata);
		ObjectMapper mapper = new ObjectMapper();
	    try 
		{
			//InputStream inputStrem = new FileInputStream(new File(pathName));
			TypeReference<List<MLevelOld>> typeReference = new TypeReference<List<MLevelOld>>(){};
			List<MLevelOld> min = mapper.readValue(jsondata, typeReference);
			
			for(MLevelOld m : min)
			{
				//mLevelRepo.save(m);
				//System.out.println(m.getId());
			} 
			System.out.println(min.size());
		}
	    catch (Exception e) 
	    {
			System.out.println(e);
		}
		
	}  
	@GetMapping("byId")
	public void getMLevelDataById(@RequestParam("id") String id)
	{
		
		
	}
	@GetMapping("getDocker")
	@ResponseBody
	@CrossOrigin
	public String getPanchayat()
	{
			
			
			JSONArray mList = new JSONArray();
			for(int i = 1; i < 20; i++)
			{
				
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("Id", i);
					jsonobj.put("Name","ASHU"+i);
					mList.put(jsonobj);
				
			}
			
			return mList.toString();
	 }
	@PostMapping("getDimTime")
	public void getDimTime(@RequestBody  List<Dim_Time> data)
	{
		int i = 1;
		for(Dim_Time dt : data)
		{
			dimRepo.save(dt);
			
		}
		//System.out.println(r);
	}
	@Autowired
	private CheckNullDataRepo nullRepo;
	@GetMapping("testNullData")
	public void testNullData()
	{
		CheckNullData c = new CheckNullData();
		//long longValue = 3;
		//c.setId(longValue);
		c.setFlag(null);
		nullRepo.save(c);
		
		
		
	}
	
}
