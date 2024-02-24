package com.nicsi.ceda.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.codehaus.jettison.json.JSONException;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class SchedularController 
{
	@Autowired
	private Environment env;
	
	@GetMapping("callSchedularByProjectCode")
	@ResponseBody
    public String callSchedularByProjectCode(@RequestParam("projectCode") int projectCode) throws JSONException
	{
		String url = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        try 
        {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
           
            String temp1 = " CALL public.pm_scheduler()";
            
            ResultSet resultSet = statement.executeQuery(temp1); 
            
        }
        catch (Exception e) 
        {
			e.printStackTrace();
		}
        return "";
    }
	@GetMapping("callSchedular")
	@ResponseBody
    public String callSchedular() throws JSONException
	{
		String url = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        JSONArray levelList = new JSONArray(); 
        try 
        {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
           
            String temp1 = " CALL public.pm_scheduler()";
            ResultSet resultSet = statement.executeQuery(temp1); 
            
         
            while (resultSet.next()) 
            {
            	temp1 = resultSet.getString(1);
            }
        }
        catch (Exception e) 
        {
			e.printStackTrace();
		}
        return "";
    }
}
