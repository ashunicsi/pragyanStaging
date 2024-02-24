package com.nicsi.ceda.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nicsi.ceda.model.RecvApiDataLogsToDisplay;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.model.Tbl_Project_KPI_Detail_Intrim;
import com.nicsi.ceda.repository.MLevelRepo;
import com.nicsi.ceda.repository.RecvApiDataLogsToDisplayRepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.repository.Tbl_Project_KPI_Detail_IntrimRepo;
import com.nicsi.ceda.services.IDataService;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class DataLogController 
{
	@Autowired
	private RecvApiDataLogsToDisplayRepo displayRepo;
	@Autowired
	private Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private IDataService dataService;
	@Autowired
	private Environment env;
	@Autowired
	private Tbl_Project_KPI_Detail_IntrimRepo kpiRepo;
	@Autowired
	private MLevelRepo mLevelRepo;
	
	
	
	
	@ResponseBody
    @CrossOrigin
    @GetMapping("validateSchemeCode")
	public String validateSchemeCode(@RequestParam("schemeCode") String projectCode) throws JSONException
	{
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCodeAndStatus(Integer.parseInt(projectCode), 1);
		JSONObject jsonobj = new JSONObject();
		if(proj != null)
		{
			jsonobj.put("status", "valid");
		}
		else
		{
			jsonobj.put("status", "invalid");
		}
		return jsonobj.toString();
	}
	@GetMapping("validateProjectCodeAndUsername")
	@ResponseBody
	@CrossOrigin
	public String validateProjectCodeAndUSername(@RequestParam("schemeCode") int schemeCode, @RequestParam("username") String username) throws JSONException
	{
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCodeAndStatusAndUsername(schemeCode, 1, username);
	    
	    JSONObject jsonobj = new JSONObject();
	    if(proj == null)
	    {
	    	jsonobj.put("status", "invalid");
	    }
	    else
	    {
	    	jsonobj.put("status", "valid");
	    }
	    return jsonobj.toString(); 
	}
	
	@GetMapping("getLogData")
	@ResponseBody
	@CrossOrigin
	public String getLogData(@RequestParam("schemeCode") int schemeCode)
	{
		List<RecvApiDataLogsToDisplay> data = displayRepo.findByProjectCode(schemeCode);
		data = data.stream().sorted(Comparator.comparingInt(RecvApiDataLogsToDisplay::getId)).collect(Collectors.toList());
		data.sort(Comparator.comparingInt(RecvApiDataLogsToDisplay::getId).reversed());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(data);
		//data = dataLogRepo.findByProjectCodeInJSON(schemeCode);
		//System.out.println(data);

		//data = data.stream().sorted(Comparator.comparingInt(RecvApiDataLogs::getId)).collect(Collectors.toList());
		//data.sort(Comparator.comparingInt(RecvApiDataLogs::getId).reversed());

		JSONArray levelList = new JSONArray(); 
		int sn = 1;
		/*for(RecvApiDataLogs s : data) 
		 { 
			SchemeDataLogDTO dto = new SchemeDataLogDTO();
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("sn", sn++); 
			jsonobj.put("id", s.getId());
			java.sql.Date d = s.getData_From_Date();
			SimpleDateFormat newFormater = new SimpleDateFormat("dd-MM-yyyy");

			jsonobj.put("prevMonthFirstDate", newFormater.format(d));
			LocalDate ld = LocalDate.parse("2023-06-30");
	        Month monthName = ld.getMonth();
			d = s.getData_To_Date();
			jsonobj.put("prevMonthLastDate", newFormater.format(d));

	       	jsonobj.put("monthName", monthName.toString().substring(0,3));
	       	Dim_Time t = timeRepo.findByDdate(s.getData_From_Date());
			jsonobj.put("finYear", t.getFinYearFull());
			jsonobj.put("portingFrequencyName", s.getData_Port_Description_Name());
			int id = projRepo.findByProjectCode(s.getProjectCode()).getProject_Data_Granularity_ID();
			DataGranularityDetails det = granularityRepo.findByDataGranularityId(id);
			//get  granularityName based on freqId
			jsonobj.put("granularityName", det.getData_Granularity_Name_e());
			jsonobj.put("scheduledOn", newFormater.format(s.getData_Port_Schedule_Date()));
			jsonobj.put("portedOn",  newFormater.format(s.getEntryDate()));
			String status = "";
			LocalDate localDate1 = s.getData_Port_Schedule_Date().toLocalDate();
		    LocalDate localDate2 = s.getEntryDate().toLocalDate();
	        long daysDifference = ChronoUnit.DAYS.between(localDate1, localDate2);
	        //get waiting days from tbl_project_detail_intrim
	        Tbl_Project_Detail_Intrim proj= projRepo.findByProjectCode(s.getProjectCode());
	        //DIEP_delay failure 

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
			jsonobj.put("status", status);
			levelList.put(jsonobj); 
			System.out.println("Data = "+jsonobj);
		 } 
		 */
		// System.out.println("ASHU");

		return json;

	}
	
	@GetMapping("getUserLogData")
	@ResponseBody
	@CrossOrigin
	public String getUserLogData(@RequestParam("schemeCode") String schemeCode, @RequestParam("username") String username)
	{
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCodeAndStatusAndUsername(Integer.parseInt(schemeCode), 1, username);
	    
	    String json = "";
	    if(proj == null)
	    {
	    	
	    }
	    else
	    {
			List<RecvApiDataLogsToDisplay> data = displayRepo.findByProjectCode(Integer.parseInt(schemeCode));
			data = data.stream().sorted(Comparator.comparingInt(RecvApiDataLogsToDisplay::getId)).collect(Collectors.toList());
			data.sort(Comparator.comparingInt(RecvApiDataLogsToDisplay::getId).reversed());
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			json = gson.toJson(data);
		}
	  
	    return json;
	}
	@GetMapping("validateRequest")
	@ResponseBody
	@CrossOrigin
	public String validateRequest(@RequestParam("id") String id) throws JSONException
	{
		RecvApiDataLogsToDisplay data = displayRepo.findById(Integer.parseInt(id));
		
		JSONObject jsonobj = new JSONObject();
		if(data == null)
		{
			jsonobj.put("status", "invalid");
		}
		
		else
		{
		    jsonobj.put("status", "valid");
		}
	
		
	    
	    return jsonobj.toString(); 
		
	}
	@GetMapping("validateRequestAndUsername")
	@ResponseBody
	@CrossOrigin
	public String validateRequestAndUsername(@RequestParam("id") String id, @RequestParam("username") String username) throws JSONException
	{
		RecvApiDataLogsToDisplay data = displayRepo.findById(Integer.parseInt(id));
		JSONObject jsonobj = new JSONObject();
		if(data == null)
		{
			jsonobj.put("status", "invalid");
		}
		else
		{
			Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCodeAndStatusAndUsername(data.getProjectCode(), 1, username);
			if(proj == null)
			{
				jsonobj.put("status", "invalid");
			}
			else
		    {
		    	jsonobj.put("status", "valid");
		    }
		}
		
	    
	    return jsonobj.toString(); 
		
	}
	//For Data Log for raw data
	@GetMapping("getKpiDetailsForHeader")
	@ResponseBody
    @CrossOrigin
	public String getKpiDetails(@RequestParam("id") int id)
	{
		RecvApiDataLogsToDisplay log = displayRepo.findById(id);
		
		List<Tbl_Project_KPI_Detail_Intrim> kpiDet= kpiRepo.findByProjectCode(log.getProjectCode());
		JSONArray levelList = new JSONArray(); 
		for(Tbl_Project_KPI_Detail_Intrim s : kpiDet) 
		{ 
			JSONObject jsonobj = new JSONObject();
			try 
			{
				jsonobj.put("kpiName",s.getKpi_name_e());
				jsonobj.put("kpiDataDisplayType", s.getKpi_data_display_type_name());
			} 
			catch (JSONException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			levelList.put(jsonobj); 
			
		}
		String jsonData = levelList.toString().replace("\\", "");
        jsonData = jsonData.replace("[\"", "[");
        jsonData = jsonData.replace("\"]", "]");
        jsonData = jsonData.replace("}\"", "}");
        jsonData = jsonData.replace("\"{", "{");
		
		return jsonData;
	}
	@GetMapping("getKpiDetailsData")
	@ResponseBody
    @CrossOrigin
	public String getConn(@RequestParam("projectCode") int projectCode) throws JSONException
	{
		String url = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        JSONArray levelList = new JSONArray(); 
        try 
        {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
           
           // String temp1 = " select string_agg(indicator,',') from  (Select concat(concat('sum((indicator=''',kpi_name,''')::int)'),' \"', kpi_name,'\"') as indicator, project_code  from KPI_Cumulative_Current where project_code="+projectCode+") as tmp group by project_code;";
            String temp1 = " select string_agg(indicator,',') from  (Select concat(concat('sum(value) FILTER (WHERE indicator=''',kpi_name,''')'),' \"', kpi_name,'\"') as indicator, project_code  from KPI_Cumulative_Current where project_code="+projectCode+") as tmp group by project_code;";
            
            ResultSet resultSet = statement.executeQuery(temp1); 
            while (resultSet.next()) 
            {
            	temp1 = resultSet.getString(1);
            	//System.out.println("Temp 1 ="+temp1);
            }
            String temp2 = "";
            String temp4 = "";
            //get data level based on project code
            Tbl_Project_Detail_Intrim proj= projRepo.findByProjectCode(projectCode);
            if(proj.getProject_Data_Lvl_Code() == 1)
            {
            	temp2 = "";
            }
            else if(proj.getProject_Data_Lvl_Code() == 2)
            {
            	temp2 = temp2 + "pd.state_name, state_code;";
            	temp4 = temp4 + "pd.state_name, state_code, ";
            }
            else if(proj.getProject_Data_Lvl_Code() == 3)
            {
            	temp2 = temp2 + "pd.state_name, state_code, district_name, dist_code; ";
            	temp4 = temp4 + "pd.state_name, state_code, district_name, dist_code,  ";
            }
            else if(proj.getProject_Data_Lvl_Code() == 4)
            {
            	temp2 = temp2 + "pd.state_name, state_code,  district_name, dist_code, village_name, village_code; ";
            	temp4 = temp4 + "pd.state_name, state_code, district_name, dist_code,  village_name, village_code,  ";
            }
            
            String temp3 = "Select pd.project_code,"+temp4;
           // System.out.println(temp3);
            temp3 = temp3 + temp1 + " FROM pm_dashboard_new  as pd inner join KPI_Cumulative_Current k on k.project_code = pd.project_code and k.Label = pd.label where pd.project_code = "+projectCode+" GROUP BY pd.project_code,"+temp2;
           // System.out.println("Temp3 = "+temp3);
            //temp3 = temp3 + dataLevel;
            resultSet = statement.executeQuery(temp3);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // Optionally, you can also print the names of the columns
            int count  = 0;
            for (int i = 1; i <= columnCount; i++) 
            {
                count ++;
            }
          
            while (resultSet.next()) 
            {
            	JSONObject jsonobj = new JSONObject();
            	jsonobj.put("projectCode", resultSet.getString(1));
            	if(proj.getProject_Data_Lvl_Code() == 1)
            	{
            		jsonobj.put("nationName", "India");
                	jsonobj.put("stateName", "");
                	jsonobj.put("stateCode", "");
                	jsonobj.put("districtName", "");
                	jsonobj.put("districtCode", "");
                	jsonobj.put("villageName", "");
                	jsonobj.put("villageCode", "");
            	}
            	else if(proj.getProject_Data_Lvl_Code() == 2)
            	{
            		jsonobj.put("nationName", "India");
                	jsonobj.put("stateName", resultSet.getString(2));
                	jsonobj.put("stateCode", resultSet.getString(3));
                	
                	jsonobj.put("districtName", "");
                	jsonobj.put("districtCode", "");
                	jsonobj.put("villageName", "");
                	jsonobj.put("villageCode", "");
            	}
            	else if(proj.getProject_Data_Lvl_Code() == 3)
            	{
            		jsonobj.put("nationName", "India");
                	jsonobj.put("stateName", resultSet.getString(2));
                	jsonobj.put("stateCode", resultSet.getString(3));
                	jsonobj.put("districtName", resultSet.getString(4));
                	jsonobj.put("districtCode", resultSet.getString(5));
                	jsonobj.put("villageName", "");
                	jsonobj.put("villageCode", "");
            	}
            	else if(proj.getProject_Data_Lvl_Code() == 4)
            	{
            		jsonobj.put("nationName", "India");
                	jsonobj.put("stateName", resultSet.getString(2));
                	jsonobj.put("stateCode", resultSet.getString(3));
                	jsonobj.put("districtName", resultSet.getString(4));
                	jsonobj.put("districtCode", resultSet.getString(5));
                	jsonobj.put("villageName", resultSet.getString(6));
                	jsonobj.put("villageCode", resultSet.getString(7));
            	}
            	//System.out.println(jsonobj);
            	String kpi = "kpi";
            	int j = 1;
            	int k = proj.getProject_Data_Lvl_Code() + 2;
            	int totalKpi = kpiRepo.countActiveKpi(proj.getProjectCode());
            	//System.out.println("Total Kpi Count = "+totalKpi);
            	
            	jsonobj.put("kpiSize", totalKpi);
            	for(int i = 0; i < totalKpi; i++)
            	{
            		jsonobj.put(kpi+j, resultSet.getString(k+i));
            		//System.out.println(resultSet.getString(k+i));
            		j++;
            	}
            		
            	
            	levelList.put(jsonobj); 
            }
            //System.out.println(levelList.toString());
            // Remember to close the resources properly
            resultSet.close();
            statement.close();
            connection.close();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        String jsonData = levelList.toString().replace("\\", "");
        jsonData = jsonData.replace("[\"", "[");
        jsonData = jsonData.replace("\"]", "]");
        jsonData = jsonData.replace("}\"", "}");
        jsonData = jsonData.replace("\"{", "{");

		return jsonData;
	}
	
	//get Data Log Data from kpi_ported
		//SELECT * FROM public.tbl_data_port_kpi_ported_data where datadt::timestamp::date >= '2023-11-28' and datadt::timestamp::date <= '2023-11-29';

	@GetMapping("getKpiDetailsDataLog")
	@ResponseBody
    @CrossOrigin
    public String getKpiDetailsDataLog(@RequestParam("projectCode") int projectCode, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) throws SQLException, JSONException, ParseException
    {
		
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fDate = inputFormat.parse(fromDate); 
		Date tDate = inputFormat.parse(toDate);
		fromDate = outputFormat.format(fDate);
		toDate =  outputFormat.format(tDate);
		String url = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM public.tbl_data_port_kpi_ported_data where datadt::timestamp::date >= '"+fromDate+"' and datadt::timestamp::date <= '"+toDate+"' and project_code="+projectCode+" ";
       //System.out.println(query);
        ResultSet resultSet = statement.executeQuery(query); 
        JSONArray levelList = new JSONArray(); 
        while (resultSet.next()) 
        {
        	JSONObject jsonobj = new JSONObject();
        	//get project name
        	Tbl_Project_Detail_Intrim proj =  projRepo.findByProjectCode(projectCode);
        	String projectName = proj.getProject_name_e();
        	jsonobj.put("projectCode", projectCode);
        	//System.out.println(proj.getProject_Data_Granularity_ID());
        	//System.out.println(proj.getProject_Data_Lvl_Code() == 5 && proj.getProject_Data_Granularity_ID() == 19);
        	if(proj.getProject_Data_Lvl_Code() == 1)
        	{
        		
        		jsonobj.put("nationName", "India");
        		jsonobj.put("nationCode", "91");
            	jsonobj.put("stateName", "");
            	jsonobj.put("stateCode", "");
            	jsonobj.put("districtName", "");
            	jsonobj.put("districtCode", "");
            	jsonobj.put("blockName", "");
            	jsonobj.put("blockCode", "");
            	jsonobj.put("villageName", "");
            	jsonobj.put("villageCode", "");
            	jsonobj.put("panchayatName", "");
            	jsonobj.put("panchayatCode", "");
        	}
        	else if(proj.getProject_Data_Lvl_Code() == 2)
        	{
        		//Nation and State Name
        		String stateName = mLevelRepo.getDistinctState(resultSet.getInt("level2_code"), 2);
        		jsonobj.put("nationName", "India");
        		jsonobj.put("nationCode", "91");
            	jsonobj.put("stateName", stateName);
            	jsonobj.put("stateCode", resultSet.getInt("level2_code"));
            	jsonobj.put("districtName", "");
            	jsonobj.put("districtCode", "");
            	jsonobj.put("blockName", "");
            	jsonobj.put("blockCode", "");
            	jsonobj.put("villageName", "");
            	jsonobj.put("villageCode", "");
            	jsonobj.put("panchayatName", "");
            	jsonobj.put("panchayatCode", "");
        		
        	}
        	else if(proj.getProject_Data_Lvl_Code() == 3)
        	{
        		//Nation, State & District Name
        		String stateName = mLevelRepo.getDistinctState(resultSet.getInt("level2_code"), 2);
        		String districtName = mLevelRepo.getDistinctDistrict(resultSet.getInt("level3_code"), 3);
        		jsonobj.put("nationName", "India");
        		jsonobj.put("nationCode", "91");
            	jsonobj.put("stateName", stateName);
            	jsonobj.put("stateCode", resultSet.getInt("level2_code"));
            	jsonobj.put("districtName", districtName);
            	jsonobj.put("districtCode", resultSet.getInt("level3_code"));
            	jsonobj.put("blockName", "");
            	jsonobj.put("blockCode", "");
            	jsonobj.put("villageName", "");
            	jsonobj.put("villageCode", "");
            	jsonobj.put("panchayatName", "");
            	jsonobj.put("panchayatCode", "");
        	}
        	else if(proj.getProject_Data_Lvl_Code() == 4)
        	{
        		//Nation, State, District & Block Name
        		String stateName = mLevelRepo.getDistinctState(resultSet.getInt("level2_code"), 2);
        		String districtName = mLevelRepo.getDistinctDistrict(resultSet.getInt("level3_code"), 3);
        		String blockName = mLevelRepo.getDistinctBlock(resultSet.getInt("level4_code"), 4);
        		jsonobj.put("nationName", "India");
        		jsonobj.put("nationCode", "91");
            	jsonobj.put("stateName", stateName);
            	jsonobj.put("stateCode", resultSet.getInt("level2_code"));
            	jsonobj.put("districtName", districtName);
            	jsonobj.put("districtCode", resultSet.getInt("level3_code"));
            	jsonobj.put("blockName", blockName);
            	jsonobj.put("blockCode", "");
            	jsonobj.put("villageName", "");
            	jsonobj.put("villageCode", "");
            	jsonobj.put("panchayatName", "");
            	jsonobj.put("panchayatCode", "");
        	}
        	else if(proj.getProject_Data_Lvl_Code() == 5 && proj.getProject_Data_Granularity_ID() != 19)
        	{
        		//Nation, State, District, Block, Village Name
        		String stateName = mLevelRepo.getDistinctState(resultSet.getInt("level2_code"), 2);
        		String districtName = mLevelRepo.getDistinctDistrict(resultSet.getInt("level3_code"), 3);
        		String blockName = mLevelRepo.getDistinctBlock(resultSet.getInt("level4_code"), 4);
        		String villageName = mLevelRepo.getDistinctVillage(resultSet.getInt("level5_code"), 25);
        		jsonobj.put("nationName", "India");
        		jsonobj.put("nationCode", "91");
            	jsonobj.put("stateName", stateName);
            	jsonobj.put("stateCode", resultSet.getInt("level2_code"));
            	jsonobj.put("districtName", districtName);
            	jsonobj.put("districtCode", resultSet.getInt("level3_code"));
            	jsonobj.put("blockName", blockName);
            	jsonobj.put("blockCode", resultSet.getInt("level4_code"));
            	jsonobj.put("villageName", villageName);
            	jsonobj.put("villageCode", resultSet.getInt("level5_code"));
            	jsonobj.put("panchayatName", "");
            	jsonobj.put("panchayatCode", "");
        	}
        	else if(proj.getProject_Data_Lvl_Code() == 5 && proj.getProject_Data_Granularity_ID() == 19)//also set data granualrity level
        	{
        		//Nation, State, District, Block, Panchayat Name 
        		
        		String stateName = mLevelRepo.getDistinctState(resultSet.getInt("level2_code"), 2);
        		String districtName = mLevelRepo.getDistinctDistrict(resultSet.getInt("level3_code"), 3);
        		String blockName = mLevelRepo.getDistinctBlock(resultSet.getInt("level4_code"), 4);
        		String panchayatName = mLevelRepo.getDistinctPanchayat(resultSet.getInt("level5_code"), 19);
        		
        		jsonobj.put("nationName", "India");
        		jsonobj.put("nationCode", "91");
            	jsonobj.put("stateName", stateName);
            	jsonobj.put("stateCode", resultSet.getInt("level2_code"));
            	jsonobj.put("districtName", districtName);
            	jsonobj.put("districtCode", resultSet.getInt("level3_code"));
            	jsonobj.put("blockName", blockName);
            	jsonobj.put("blockCode", resultSet.getInt("level4_code"));
            	
            	jsonobj.put("panchayatName", panchayatName);
            	jsonobj.put("panchayatCode", resultSet.getInt("level5_code"));
            	jsonobj.put("villageName", "");
            	jsonobj.put("villageCode", "");
        	}
        	//get state name
        	
        	//get district name
        	//get block name
        	//get village name
        	String kpi = "kpi";
        	int j = 1;
        	int k = proj.getProject_Data_Lvl_Code() + 2;
        	int totalKpi = kpiRepo.countActiveKpi(proj.getProjectCode());
        	//System.out.println("Total Kpi Count = "+totalKpi);
        	int kpiId = resultSet.getInt("kpi_id");
        	
        	jsonobj.put("kpiSize", totalKpi);
        	for(int i = 0; i < totalKpi; i++)
        	{
        		//Tbl_Project_KPI_Detail_Intrim kpiData = kpiRepo.findByProjectCodeAndKpiIdAndStatus(projectCode, i+1, 1);
        		//String kpiName = kpiData.getKpi_name_e();
        		jsonobj.put(kpi+j, resultSet.getDouble("kpi_data"));
        		j++;
        	}
        	levelList.put(jsonobj);
        }
        String jsonData = levelList.toString().replace("\\", "");
        jsonData = jsonData.replace("[\"", "[");
        jsonData = jsonData.replace("\"]", "]");
        jsonData = jsonData.replace("}\"", "}");
        jsonData = jsonData.replace("\"{", "{");
        
		return jsonData;
    }
	@ResponseBody
    @CrossOrigin
	@GetMapping("getDataGranularity")
	public int getDataGranularity(@RequestParam("projectCode") int projectCode)
	{
		Tbl_Project_Detail_Intrim proj = projRepo.findByProjectCodeAndStatus(projectCode, 1);
		
		return proj.getProject_Data_Granularity_ID();
	}
}
