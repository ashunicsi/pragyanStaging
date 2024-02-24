package com.nicsi.ceda.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nicsi.ceda.model.PM_Dashboard_New;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.repository.PM_Dashboard_NewRepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;


public class DummyDataController 
{
	@Autowired
	private Tbl_Project_Detail_IntrimRepo projRepo;
	
	
	
	
	/*
	 * 
	 * 
	 * 
	 * 		
Select pd.project_code, pd.state_name,district_name, fiscal_year, date,

sum(value) FILTER (WHERE value >=1 or NOT NULL  AND indicator='Number of works executed') "Number of works executed",
sum(value) FILTER (WHERE value >=1 or NOT NULL  AND indicator='Number of works identified for execution') "Number of works identified for execution",
sum(value) FILTER (WHERE value >=1 or NOT NULL  AND  indicator='Number of households where need assessment survey completed') "Number of households where need assessment survey completed",
sum(value) FILTER (WHERE value >=1 or NOT NULL  AND  indicator='Number of Villages covered under the scheme') "Number of Villages covered under the scheme",
sum(value) FILTER (WHERE value >=1 or NOT NULL  AND indicator='Number of persons benefited') "Number of persons benefited",
sum(value) FILTER (WHERE value >=1 or NOT NULL  AND indicator='Number of prospective beneficiaries identified') "Number of prospective beneficiaries identified"


FROM pm_dashboard_new  as pd
inner join KPI_Cumulative_Current k on k.project_code = pd.project_code and k.Label = pd.label  
where pd.project_code= 1006   GROUP BY pd.project_code, pd.state_name,district_name, fiscal_year, date
----------------------------------------
New Code to handle NPE 
----------------------------------------
Select project_code, state_name,district_name, fiscal_year, date,
case when temp."Number of works executed" is NULL then '0' else temp."Number of works executed" END ,
case when temp."Number of works identified for execution" is NULL then '0' else temp."Number of works identified for execution" END ,
case when temp."Number of households where need assessment survey completed" is NULL then '0' else temp."Number of households where need assessment survey completed" END ,
case when temp."Number of Villages covered under the scheme" is NULL then '0' else temp."Number of Villages covered under the scheme" END ,
case when temp."Number of persons benefited" is NULL then '0' else temp."Number of persons benefited" END ,
case when temp."Number of prospective beneficiaries identified" is NULL then '0' else temp."Number of prospective beneficiaries identified" END 
from (
Select pd.project_code, pd.state_name,district_name, fiscal_year, date,

sum(value) FILTER (WHERE (value >=1 or NOT NULL)  AND indicator='Number of works executed') "Number of works executed",
sum(value) FILTER (WHERE (value >=1 or NOT NULL)  AND indicator='Number of works identified for execution') "Number of works identified for execution",
sum(value) FILTER (WHERE (value >=1 or NOT NULL)  AND  indicator='Number of households where need assessment survey completed') "Number of households where need assessment survey completed",
sum(value) FILTER (WHERE (value >=1 or NOT NULL)  AND  indicator='Number of Villages covered under the scheme') "Number of Villages covered under the scheme",
sum(value) FILTER (WHERE (value >=1 or NOT NULL)  AND indicator='Number of persons benefited') "Number of persons benefited",
sum(value) FILTER (WHERE (value >=1 or NOT NULL)  AND indicator='Number of prospective beneficiaries identified') "Number of prospective beneficiaries identified"


FROM pm_dashboard_new  as pd
inner join KPI_Cumulative_Current k on k.project_code = pd.project_code and k.Label = pd.label  
where pd.project_code= 1006   GROUP BY pd.project_code, pd.state_name,district_name, fiscal_year, date) temp
*/
	
	
	@GetMapping("readExcelData")
	public String excel() throws IOException
	{
		
		FileInputStream file=new FileInputStream(new File("E:\\fertilizer.xlsx"));  
		 XSSFWorkbook wb=new XSSFWorkbook(file);  
		 Sheet sheet = wb.getSheetAt(0);
         Iterator<Row> rowIterator = sheet.iterator();
           int i = 1;
           List<PM_Dashboard_New> data = new ArrayList<PM_Dashboard_New>();
           while (rowIterator.hasNext()) 
           {
           		Row row = rowIterator.next();
           		if(i != 1)
	            {
           			i++;
           			System.out.println(row.getCell(0).getNumericCellValue());
	            }
           		
           }
           
           wb.close();
		return "";
	}
}
