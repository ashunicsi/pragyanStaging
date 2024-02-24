package com.nicsi.ceda.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class SupersetController 
{
	@Autowired
    private Environment environment;
	
	@GetMapping("getListOfRole")
	public String getListOfRole() throws SQLException
	{
		//String jdbcUrl = "jdbc:postgresql:http://10.24.248.8:5432/superset";
		String jdbcUrl = environment.getProperty("superset-jdbcUrl");
        String username = environment.getProperty("superset-username");
        String password = environment.getProperty("superset-password");
        JSONArray levelList = new JSONArray();
        try 
        {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement stmt=connection.createStatement();  
            String sql1 =  environment.getProperty("superset-query-role");
            ResultSet rs=stmt.executeQuery(sql1);
			int i = 0;
			
			while(rs.next())  
			{
				
				JSONObject jsonobj = new JSONObject();
				 jsonobj.put("roleId", rs.getInt(1));
				 jsonobj.put("name", rs.getString(2));
				 levelList.put(jsonobj);
			}
            connection.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
		
		return levelList.toString();
	}
}
