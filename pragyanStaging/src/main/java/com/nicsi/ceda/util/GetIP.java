package com.nicsi.ceda.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class GetIP
{
	public String getIPAddress(HttpServletRequest request)
	{
		String ip = "";
		try 
		{
			ip = request.getHeader("X-Forwarded-For");  
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
          
        } 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		return ip;
	}
}
