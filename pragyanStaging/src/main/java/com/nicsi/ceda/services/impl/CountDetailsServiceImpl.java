package com.nicsi.ceda.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.nicsi.ceda.dao.ICountDetailsDao;
import com.nicsi.ceda.services.ICountDetailsService;

@Service

public class CountDetailsServiceImpl implements ICountDetailsService
{
	@Autowired
	private ICountDetailsDao countDao;
	
	public int countRegisteredScheme() 
	{
		return countDao.countRegisteredScheme();
	}
	public int countActivatedScheme() 
	{
		return countDao.countActivatedScheme();
	}
	public int countSubmittedScheme() 
	{
		return countDao.countSubmittedScheme();
	}
	public int countDeactivatedScheme() 
	{
		return countDao.countDeactivatedScheme();
	}
	public int countRegisteredKPI() 
	{
		return countDao.countRegisteredKPI() ;
	}
	public int countActivatedKPI() 
	{
		return countDao.countActivatedKPI();
	}
	public int countDeactivatedKPI() 
	{
		return countDao.countDeactivatedKPI();
	}
	public int countSchemeData() 
	{
		return countDao.countSchemeData();
	}
	public int countSchemeDataUpto() 
	{
		return countDao.countSchemeDataUpto(); 
	}
	public int countSchemeDataPendency() 
	{
		return countDao.countSchemeDataPendency(); 
	}
	public int countSchemeReviewed() 
	{
		return countDao.countSchemeReviewed(); 
	}
	public int countSchemePendingForReview() 
	{
		return countDao.countSchemePendingForReview();
	}
	public int countSchemeIntegrationErrors() 
	{
		return countDao.countSchemeIntegrationErrors();
	}
	
	public int countRegisteredScheme(String username)
	{
		return countDao.countRegisteredScheme(username);
	}
	public int countActivatedScheme(String username)
	{
		return countDao.countActivatedScheme(username);
	}
	public int countDeactivatedScheme(String username)
	{
		return countDao.countDeactivatedScheme(username);
	}
	public int countSubmittedScheme(String username) 
	{
		return countDao.countSubmittedScheme(username);
	}
	public int countRegisteredKPI(String username)
	{
		return countDao.countRegisteredKPI(username);
	}
	public int countActivatedKPI(String username)
	{
		return countDao.countActivatedKPI(username);
	}
	public int countDeactivatedKPI(String username)
	{
		return countDao.countDeactivatedKPI(username);
	}
	
	public int countSchemeData(String username)
	{
		return countDao.countSchemeData(username);
	}
	public int countSchemeDataUpto(String username)
	{
		return countDao.countSchemeDataUpto(username);
	}
	public int countSchemeDataPendency(String username)
	{
		return countDao.countSchemeDataPendency(username);
	}
	public int countSchemeReviewed(String username)
	{
		return countDao.countSchemeReviewed(username);
	}
	public int countSchemePendingForReview(String username)
	{
		return countDao.countSchemePendingForReview(username);
	}
	public int countSchemeIntegrationErrors(String username)
	{
		return countDao.countSchemeIntegrationErrors(username);
	}
	public int countRejectedScheme()
	{
		return countDao.countRejectedScheme();
	}
	public int countRejectedScheme(String username)
	{
		return countDao.countRejectedScheme(username);
	}
}
