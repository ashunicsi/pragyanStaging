package com.nicsi.ceda.dao.impl;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.nicsi.ceda.dao.ILoginDao;
import com.nicsi.ceda.model.Login;
import com.nicsi.ceda.model.UserActivities;
import com.nicsi.ceda.repository.LoginRepo;
import com.nicsi.ceda.repository.UserActivitiesRepo;

@Repository
public class LoginDaoImpl implements ILoginDao
{
	@Autowired
	private LoginRepo loginRepo;
	@Autowired
	private UserActivitiesRepo userRepo;
	
	String status = null;
	
	public String login(Login log, HttpSession session, HttpServletResponse response, ModelMap map, HttpServletRequest request) 
	{
		//System.out.println(log);
		UserActivities user = new UserActivities();
		try
		{
			String ip = request.getHeader("X-Forwarded-For");  
		    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		    {  
		        ip = request.getHeader("Proxy-Client-IP");  
		    }  
		    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		    {  
		        ip = request.getHeader("WL-Proxy-Client-IP");  
		    }  
		    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		    {  
		        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		    }  
		    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		    {  
		        ip = request.getHeader("HTTP_X_FORWARDED");  
		    }  
		    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		    {  
		        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
		    }  
		    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		    {  
		        ip = request.getHeader("HTTP_CLIENT_IP");  
		    }  
		    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		    {  
		        ip = request.getHeader("HTTP_FORWARDED_FOR");  
		    }  
		    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		    {  
		        ip = request.getHeader("HTTP_FORWARDED");  
		    }  
		    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		    {  
		        ip = request.getHeader("HTTP_VIA");  
		    }  
		    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		    {  
		        ip = request.getHeader("REMOTE_ADDR");  
		    }  
		    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		    {  
		        ip = request.getRemoteAddr();  
		    }  
			
			String ipAddress = Inet4Address.getLocalHost().getHostAddress();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	        LocalDateTime lt = LocalDateTime.now(); 
	        String time = lt.format(formatter);
	        InetAddress inetaddress = InetAddress.getLocalHost();
	        String name = inetaddress.getHostName();  
	        inetaddress =InetAddress.getLocalHost(); 
	        
	        //NetworkInterface network = NetworkInterface.getByInetAddress(inetaddress); 
	       // byte[] macArray = network.getHardwareAddress();  
	       // StringBuilder str = new StringBuilder();
	       // for (int i = 0; i < macArray.length; i++) 
	       // {
	       //         str.append(String.format("%02X%s", macArray[i], (i < macArray.length - 1) ? "-" : ""));
	       // }
	       // String macAddress=str.toString();
	        user.setIpAddress(ip);
			user.setLoginTime(time);
			//user.setMacAddress(macAddress);
			user.setSystemName(name);
			user.setOperationPerformend("Login");
			user.setUsername(log.getEmailId());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		Login login = loginRepo.findByEmailId(log.getEmailId());
		if(login != null)
		{
			if(login.getIsLocked().equals("Yes"))
			{
				user.setAction("Fail");
				userRepo.save(user);
				map.addAttribute("error", "Your account is locked, Please contact your system administrator");
				status = "Your account is locked, Please contact your system administrator";
			}
			else if(login.getIsApproved() == 0)
			{
				status = "Your account approval is pending...";
				 JSONObject jsonobj = new JSONObject();
				 jsonobj.put("status", status);
				 jsonobj.put("userType", login.getUserType());
				 jsonobj.put("userPermission",login.getUserPermission());
				 jsonobj.put("userRole",login.getUserRoleId());
				 jsonobj.put("name",login.getName());
				 jsonobj.put("displayName",login.getDisplayName());
				 status = jsonobj.toString();
			}
			else if(login.getIsApproved() == 2)
			{
				status = "Your account approval is rejected...";
				 JSONObject jsonobj = new JSONObject();
				 jsonobj.put("status", status);
				 jsonobj.put("userType", login.getUserType());
				 jsonobj.put("userPermission",login.getUserPermission());
				 jsonobj.put("userRole",login.getUserRoleId());
				 jsonobj.put("name",login.getName());
				 jsonobj.put("displayName",login.getDisplayName());
				 status = jsonobj.toString();
			}
			else
			{
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String encodedPassword = passwordEncoder.encode(log.getPassword());
			
				boolean isPasswordMatch = passwordEncoder.matches(log.getPassword(), login.getPassword());
				if(isPasswordMatch == true)
				{
					session.setAttribute("username", log.getEmailId());
					user.setAction("Success");
					userRepo.save(user);
					login.setIsLocked("No");
					login.setNoOfAttempts(0);
					loginRepo.save(login);
					session.setAttribute("username", log.getEmailId());
					
					 JSONObject jsonobj = new JSONObject();
					 jsonobj.put("status", "success");
					 jsonobj.put("userType", login.getUserType());
					 jsonobj.put("userPermission",login.getUserPermission());
					 jsonobj.put("userRole",login.getUserRoleId());
					 jsonobj.put("name",login.getName());
					 jsonobj.put("displayName",login.getDisplayName());
					status = jsonobj.toString();
					
				}
				else
				{
					int noOfAttempts = login.getNoOfAttempts();
					if(noOfAttempts == 3)
					{
						login.setIsLocked("Yes");
					}
					map.addAttribute("error", "Invalid Username or Password");
					status = "Invalid Email or Password";
					noOfAttempts = noOfAttempts + 1;
					login.setNoOfAttempts(noOfAttempts);
					loginRepo.save(login);
					user.setAction("Fail");
					
					JSONObject jsonobj = new JSONObject();
					 jsonobj.put("status", status);
					 jsonobj.put("userType", login.getUserType());
					 jsonobj.put("userPermission",login.getUserPermission());
					 jsonobj.put("userRole",login.getUserRoleId());
					 jsonobj.put("displayName",login.getName());
					status = jsonobj.toString();
					
				}
			}	
		
		}
		else
		{
			user.setAction("Fail");
			user.setAction("Login");
			
			status = "Invalid Email or Password";
			JSONObject jsonobj = new JSONObject();
			 jsonobj.put("status", status);
			 jsonobj.put("userType", "user");
			 jsonobj.put("userPermission",0);
			 //jsonobj.put("name",login.getName());
			status = jsonobj.toString();
		}
		//Long countRow = userRepo.count();
		//int id = countRow.intValue();
		//id = id +1;
		//user.setId(id);
		//userRepo.save(user);
		return status;
	}
	
}
