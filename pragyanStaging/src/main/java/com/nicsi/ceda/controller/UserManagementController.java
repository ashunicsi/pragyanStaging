package com.nicsi.ceda.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nicsi.ceda.model.Login;
import com.nicsi.ceda.repository.LoginRepo;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class UserManagementController 
{
	@Autowired
	private LoginRepo loginRepo;
	@Autowired
    private Environment environment;
	
	@GetMapping("countActivatedUser")
	public String countActivatedUser()
	{
		int count = loginRepo.countActivatedUser();
		return String.valueOf(count);
	}
	@GetMapping("countDeactivatedUser")
	public String countDeactivatedUser()
	{
		int count = loginRepo.countDeactivatedUser();
		return String.valueOf(count);
	}
	@GetMapping("getActivatedUser")
	public String getActivatedUser()
	{
		//List<Login> log = loginRepo.findByStatusAndUserTypeAndIsSuperAdmin(1, "admin", 0);
		List<Login> log = loginRepo.findByFlagAndIsSuperAdmin(1, 1);
		JSONArray levelList = new JSONArray();
		for(Login l : log)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("username", l.getEmailId());
			 jsonobj.put("userType", l.getUserType());
			 if(l.getUserRoleId() == 1)
			 {
				 //Admin user  read permission 
				 jsonobj.put("userPermission", "Admin");
			 }
			 if(l.getUserRoleId() == 2)
			 {
				 //Admin user  read/write permission 
				 jsonobj.put("userPermission", "Public");
			 }
			 if(l.getUserRoleId() == 3)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "Alpha");
			 }
			 if(l.getUserRoleId() == 4)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "Gamma");
			 }
			 if(l.getUserRoleId() == 5)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "sql_lab");
			 }
			 if(l.getUserRoleId() == 6)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "Viewer");
			 }
			 jsonobj.put("createdDate", l.getCreatedDate());
			 jsonobj.put("isLocked", l.getIsLocked());
			 jsonobj.put("createdBy", l.getCreatedBy());
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("getDeactivatedUser")
	public String getDeactivatedUser()
	{
		//List<Login> log = loginRepo.findByStatusAndUserTypeAndIsSuperAdmin(0, "admin", 0);
		List<Login> log = loginRepo.findByStatusAndIsSuperAdmin(0, 0);
		JSONArray levelList = new JSONArray();
		for(Login l : log)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("username", l.getEmailId());
			 jsonobj.put("userType", l.getUserType());
			 if(l.getUserRoleId() == 1)
			 {
				 //Admin user  read permission 
				 jsonobj.put("userPermission", "Admin");
			 }
			 if(l.getUserRoleId() == 2)
			 {
				 //Admin user  read/write permission 
				 jsonobj.put("userPermission", "Public");
			 }
			 if(l.getUserRoleId() == 3)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "Alpha");
			 }
			 if(l.getUserRoleId() == 4)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "Gamma");
			 }
			 if(l.getUserRoleId() == 5)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "sql_lab");
			 }
			 if(l.getUserRoleId() == 6)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "Viewer");
			 }
			 jsonobj.put("createdDate", l.getCreatedDate());
			 jsonobj.put("isLocked", l.getIsLocked());
			 jsonobj.put("createdBy", l.getCreatedBy());
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("countActivatedUserByUsername")
	public String countActivatedUserByUsername(@RequestParam String username)
	{
		int count = loginRepo.countActivatedUser(username);
		return String.valueOf(count);
	}
	@GetMapping("countDeactivatedUserByUsername")
	public String countDeactivatedUserByUsername(@RequestParam String username)
	{
		int count = loginRepo.countDeactivatedUser(username);
		return String.valueOf(count);
	}
	@GetMapping("getActivatedUserByUsername")
	public String getActivatedUserByUsername(@RequestParam String username)
	{
		List<Login> log = loginRepo.findByStatusAndCreatedBy(1, username);
		JSONArray levelList = new JSONArray();
		for(Login l : log)
		{
			
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("username", l.getEmailId());
			 jsonobj.put("userType", l.getUserType());
			 if(l.getUserPermission() == 1)
			 {
				 //Admin user  read permission 
				 jsonobj.put("userPermission", "Admin");
			 }
			 if(l.getUserPermission() == 2)
			 {
				 //Admin user  read/write permission 
				 jsonobj.put("userPermission", "Viewer");
			 }
			 if(l.getUserPermission() == 3)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "Creator");
			 }
			 if(l.getUserPermission() == 4)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "Database");
			 }
			 if(l.getUserPermission() == 5)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "Public");
			 }
			 
			 jsonobj.put("createdDate", l.getCreatedDate());
			 jsonobj.put("isLocked", l.getIsLocked());
			 jsonobj.put("createdBy", l.getCreatedBy());
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	@GetMapping("getDeactivatedUserByUsername")
	public String getDeactivatedUserByUsername(@RequestParam String username)
	{
		List<Login> log = loginRepo.findByStatusAndCreatedBy(0, username);
		JSONArray levelList = new JSONArray();
		for(Login l : log)
		{
			 JSONObject jsonobj = new JSONObject();
			 jsonobj.put("username", l.getEmailId());
			 jsonobj.put("userType", l.getUserType());
			 if(l.getUserPermission() == 1)
			 {
				 //Admin user  read permission 
				 jsonobj.put("userPermission", "Admin");
			 }
			 if(l.getUserPermission() == 2)
			 {
				 //Admin user  read/write permission 
				 jsonobj.put("userPermission", "Viewer");
			 }
			 if(l.getUserPermission() == 3)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "Creator");
			 }
			 if(l.getUserPermission() == 4)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "Database");
			 }
			 if(l.getUserPermission() == 5)
			 {
				 //Admin user create user 
				 jsonobj.put("userPermission", "Public");
			 }
			 jsonobj.put("createdDate", l.getCreatedDate());
			 jsonobj.put("isLocked", l.getIsLocked());
			 jsonobj.put("createdBy", l.getCreatedBy());
			 levelList.put(jsonobj);
		}
		return levelList.toString();
	}
	
	@PostMapping(value ="addNewUser",consumes = "application/json", produces = "application/json" )
	@ResponseBody
    @CrossOrigin
    //@RequestParam("emailId") String emailId, @RequestParam("userPermission") String permission, @RequestParam("password") String password
	public String addNewUserByAdmin(@RequestBody Login log)
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(log.getPassword());
		log.setPassword(encodedPassword);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime lt = LocalDateTime.now(); 
		String createdDate = lt.format(formatter);
		log.setCreatedDate(createdDate);
		log.setFlag(1);
		log.setNoOfAttempts(0);
		log.setIsLocked("No");
		log.setLanguage("English");
		log.setUserType(log.getUserType());
		log.setUserPermission(0);
		log.setIsSuperAdmin(0);
		log.setStatus(1);
		log.setCreatedBy(log.getUsername());
		loginRepo.save(log);
		//System.out.println(log);
		/*
		String jdbcUrl = environment.getProperty("superset-jdbcUrl");
        String username = environment.getProperty("superset-username");
        String password = environment.getProperty("superset-password");
        Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime()); 

        try
        {
	        Class.forName("org.postgresql.Driver");
	
	        // Establish the database connection
	        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
	        Statement stmt=connection.createStatement();  
	        String sql = "SELECT COUNT(*) FROM public.ab_user";
	     // Execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Extract the count from the result set
            int rowCount = 0;
            if (resultSet.next()) 
            {
                rowCount = resultSet.getInt(1);
            }
			String query = "INSERT INTO public.ab_user (id, first_name, last_name, username, password, active, email, last_login, login_count, fail_login_count, created_on, changed_on, created_by_fk, changed_by_fk) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, rowCount+1);
            preparedStatement.setString(2, log.getName());
            preparedStatement.setString(3, "");
            preparedStatement.setString(4, log.getEmailId());
            preparedStatement.setString(5, "");
            preparedStatement.setBoolean(6, true);
            preparedStatement.setString(7, log.getEmailId());
            preparedStatement.setTimestamp(8, ts);
            preparedStatement.setInt(9, 0);
            preparedStatement.setInt(10, 0);
            preparedStatement.setTimestamp(11, ts);
            preparedStatement.setTimestamp(12, ts);
            preparedStatement.setInt(13, 1);
            preparedStatement.setInt(14, 1);
            preparedStatement.executeUpdate();
            //now map user and role
            sql = "SELECT COUNT(*) FROM public.ab_user_role";
   	     // Execute the query
               statement = connection.createStatement();
              // resultSet = statement.executeQuery(sql);

               // Extract the count from the result set
               int totalCount = 0;
               if (resultSet.next()) 
               {
            	   totalCount = resultSet.getInt(1);
               }
            query = "INSERT INTO ab_user_role (id, user_id, role_id) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, totalCount+1);
            preparedStatement.setInt(2, rowCount+1);
            preparedStatement.setInt(3, log.getUserPermission());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            connection.close();
          
        }
        catch (Exception e) 
        {
			e.printStackTrace();
		}
		  */
		return "success";
	}
	@PostMapping(value ="addNewUserByUser",consumes = "application/json", produces = "application/json" )
	@ResponseBody
    @CrossOrigin
    //@RequestParam("emailId") String emailId, @RequestParam("userPermission") String permission, @RequestParam("password") String password
	public String addNewUserByUser(@RequestBody Login log)
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(log.getPassword());
		log.setPassword(encodedPassword);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime lt = LocalDateTime.now(); 
		String createdDate = lt.format(formatter);
		log.setCreatedDate(createdDate);
		log.setFlag(1);
		log.setNoOfAttempts(0);
		log.setIsLocked("No");
		log.setLanguage("English");
		log.setUserType("user");
		log.setIsSuperAdmin(0);
		log.setStatus(1);
		log.setCreatedBy(log.getUsername());
		log.setUserRoleId(log.getUserPermission());
		log.setUserPermission(0);
		loginRepo.save(log);
		
		String jdbcUrl = environment.getProperty("superset-jdbcUrl");
        String username = environment.getProperty("superset-username");
        String password = environment.getProperty("superset-password");
        Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime()); 

        try
        {
	        Class.forName("org.postgresql.Driver");
	
	        // Establish the database connection
	        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
	        Statement stmt=connection.createStatement();  
	        String sql = "SELECT COUNT(*) FROM public.ab_user";
	     // Execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Extract the count from the result set
            int rowCount = 0;
            if (resultSet.next()) 
            {
                rowCount = resultSet.getInt(1);
            }
			String query = "INSERT INTO public.ab_user (id, first_name, last_name, username, password, active, email, last_login, login_count, fail_login_count, created_on, changed_on, created_by_fk, changed_by_fk) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            rowCount = rowCount +1;
            preparedStatement.setInt(1, rowCount+1);
            preparedStatement.setString(2, log.getName());
            preparedStatement.setString(3, "");
            preparedStatement.setString(4, log.getEmailId());
            preparedStatement.setString(5, "");
            preparedStatement.setBoolean(6, true);
            preparedStatement.setString(7, log.getEmailId());
            preparedStatement.setTimestamp(8, ts);
            preparedStatement.setInt(9, 0);
            preparedStatement.setInt(10, 0);
            preparedStatement.setTimestamp(11, ts);
            preparedStatement.setTimestamp(12, ts);
            preparedStatement.setInt(13, 1);
            preparedStatement.setInt(14, 1);
            //preparedStatement.executeUpdate();
            //now map user and role
            //get maX ID and add 1
            //sql = "SELECT COUNT(*) FROM public.ab_user_role";
            sql = "SELECT MAX(id) FROM public.ab_user_role";
            
   	     // Execute the query
               statement = connection.createStatement();
              resultSet = statement.executeQuery(sql);

               // Extract the count from the result set
               int totalCount = 0;
               if (resultSet.next()) 
               {
            	   totalCount = resultSet.getInt(1);
               }
            query = "INSERT INTO ab_user_role (id, user_id, role_id) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, totalCount+1);
            preparedStatement.setInt(2, rowCount);
            preparedStatement.setInt(3, log.getUserPermission());
           //preparedStatement.executeUpdate();
            preparedStatement.close();

            connection.close();
        }
        catch (Exception e) 
        {
			e.printStackTrace();
		}
		return "success";
	}
	@GetMapping("deactivateUser")
	public String deactivateUser(@RequestParam String emailId)
	{
		Login log = loginRepo.findByEmailId(emailId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime lt = LocalDateTime.now(); 
		String updatedDate = lt.format(formatter);
		log.setUpdatedDate(updatedDate);
		log.setStatus(0);
		log.setFlag(0);
		loginRepo.save(log);
		
		return "User Deactivated Successfully!";
	}
	@GetMapping("activateUser")
	public String activateUser(@RequestParam String emailId)
	{
		Login log = loginRepo.findByEmailId(emailId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime lt = LocalDateTime.now(); 
		String updatedDate = lt.format(formatter);
		log.setUpdatedDate(updatedDate);
		log.setStatus(1);
		log.setFlag(1);
		loginRepo.save(log);
		return "User Activated Successfully!";
	}

	@GetMapping("unlockUser")
	public String unlockUser(@RequestParam String emailId)
	{
		//Login log = loginRepo.findByEmailIdAndUserType(emailId, "admin");
		Login log = loginRepo.findByEmailId(emailId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime lt = LocalDateTime.now(); 
		String updatedDate = lt.format(formatter);
		log.setUpdatedDate(updatedDate);
		log.setIsLocked("No");
		loginRepo.save(log);
		return "User Unlocked Successfully!";
	}
	
	@GetMapping("getUserDetailsToUpdate")
	public String getUserDetailsToUpdate(@RequestParam String emailId)
	{
		Login log = loginRepo.findByEmailIdAndUserType(emailId, "admin");
		 JSONObject jsonobj = new JSONObject();
		 jsonobj.put("emailId", log.getEmailId());
		 jsonobj.put("userPermission",log.getUserPermission());
		return jsonobj.toString();
	}
	@GetMapping("getUserDetailsForUpdate")
	public String getUserDetailsForUpdate(@RequestParam String emailId)
	{
		Login log = loginRepo.findByEmailId(emailId);
		
		 JSONObject jsonobj = new JSONObject();
		 jsonobj.put("emailId", log.getEmailId());
		 jsonobj.put("userPermission",log.getUserPermission());
		
		 jsonobj.put("password",log.getPassword());
		 
		return jsonobj.toString();
	}
	@PostMapping(value ="passwordReset",consumes = "application/json", produces = "application/json" )
	@ResponseBody
    @CrossOrigin
	public String passwordReset(@RequestBody Login log)
	{
		//log = loginRepo.findByEmailIdAndUserType(log.getEmailId(), "admin");
		log = loginRepo.findByEmailId(log.getEmailId());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(log.getPassword());
		log.setPassword(encodedPassword);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime lt = LocalDateTime.now(); 
		String updatedDate = lt.format(formatter);
		log.setUpdatedDate(updatedDate);
		
		loginRepo.save(log);
		return "success";
	}
	@PostMapping(value ="isValidUser",consumes = "application/json", produces = "application/json" )
	@ResponseBody
    @CrossOrigin
	public String isValidUser(@RequestBody Login log)
	{
		//System.out.println(log);
		log = loginRepo.findByEmailId(log.getEmailId());
		
		String status = "";
		if(log == null)
		{
			status = "Invalid User"; 
		}
		else
		{
			status = "Valid User"; 
		}
		return status;
	}
	@PostMapping(value ="updateUser",consumes = "application/json", produces = "application/json" )
	@ResponseBody
    @CrossOrigin
	public String updateUserPermission(@RequestBody Login log)
	{
		//System.out.println(log);
		Login l = loginRepo.findByEmailId(log.getEmailId());
		//System.out.println(l);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime lt = LocalDateTime.now(); 
		String createdDate = lt.format(formatter);
		l.setUpdatedDate(createdDate);
		
		l.setUpdatedBy(log.getUsername());
		l.setUserRoleId(log.getUserPermission());
		//l.setUserPermission(log.getUserPermission());
		loginRepo.save(l);
		String jdbcUrl = environment.getProperty("superset-jdbcUrl");
        String username = environment.getProperty("superset-username");
        String password = environment.getProperty("superset-password");
        Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime()); 

        try
        {
	        Class.forName("org.postgresql.Driver");
	
	        // Establish the database connection
	        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
	        //Statement stmt=connection.createStatement();  
	        String sqlUpdate = "UPDATE public.ab_user_role SET role_id = ? WHERE id = ?";
  
	            // Set parameters for the update statement
	        	PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

	            preparedStatement.setInt(1, log.getUserPermission()); // Replace with the new value
	            preparedStatement.setInt(2, l.getId()); // Replace with the specific ID you want to update

	            // Execute the update
	           // int rowsAffected = preparedStatement.executeUpdate();

	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	            return "fail";
	        }
	        
	       		return "success";
	}
}
