package com.nicsi.ceda.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nicsi.ceda.dao.ICountDetailsDao;
import com.nicsi.ceda.repository.Tbl_DataPort_KPI_PORTED_DATARepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.repository.Tbl_Project_KPI_Detail_IntrimRepo;

import java.sql.Date;
import java.sql.Timestamp;


@Repository
public class CountDetailsDaoImpl implements ICountDetailsDao
{
	@Autowired
	private Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private Tbl_Project_KPI_Detail_IntrimRepo kpiRepo;
	@Autowired
	private Tbl_DataPort_KPI_PORTED_DATARepo kpiPortedRepo;
	
	public int countRegisteredScheme() 
	{
		return projRepo.countRegisteredScheme();
	}
	public int countActivatedScheme() 
	{
		return projRepo.countActivatedScheme();
	}
	public int countDeactivatedScheme() 
	{
		return projRepo.countDeactivatedScheme();
	}
	public int countSubmittedScheme()
	{
		return projRepo.countSubmittedScheme();
	}
	public int countRegisteredKPI() 
	{
		return kpiRepo.countRegisteredKPI();
	}
	public int countActivatedKPI() 
	{
		return kpiRepo.countActivatedKPI();
	}
	public int countDeactivatedKPI() 
	{
		return kpiRepo.countDeactivatedKPI();
	}
	public int countSchemeData() 
	{
		return projRepo.countActivatedScheme();
	}
	public int countSchemeDataUpto() 
	{
		//Timestamp date = kpiPortedRepo.getSchemeMaxDate();
		//for admin-count projectCode and return to caller
		//System.out.println("Total Data Recvd = "+kpiPortedRepo.countSchemeDataReceived());
		return kpiPortedRepo.countSchemeDataReceived();
	}
	public int countSchemeDataPendency() 
	{
		//Count total Activated Scheme
		//Get Max Date Count
		//CountScheme -  maxdatCount
		int totalActivatedScheme = countActivatedScheme();
		int maxDateCount = countSchemeDataUpto();
		
		return totalActivatedScheme - maxDateCount;
	}
	
	public int countRegisteredScheme(String username)
	{
		return projRepo.countUserRegisteredScheme(username);
	}
	public int countActivatedScheme(String username)
	{
		return projRepo.countUserActivatedScheme(username);
	}
	
	public int countDeactivatedScheme(String username)
	{
		return projRepo.countUserDeactivatedScheme(username);
	}
	public int countSubmittedScheme(String username)
	{
		return projRepo.countSubmittedScheme(username);
	}
	public int countRegisteredKPI(String username)
	{
		return kpiRepo.countUserRegisteredKPI(username);
	}
	public int countActivatedKPI(String username)
	{
		return kpiRepo.countUserActivatedKPI(username);
	}
	public int countDeactivatedKPI(String username)
	{
		return kpiRepo.countUserDeactivatedKPI(username);
	}
	
	
	public int countSchemeData(String username)
	{
		return kpiPortedRepo.countSchemeData(username);
	}
	public int countSchemeDataUpto(String username)
	{
		 return kpiPortedRepo.countSchemeDataUpto(username);
	}
	public int countSchemeDataPendency(String username)
	{
		int totalActivatedScheme = countActivatedScheme(username);
		
		int maxDateCount = countSchemeDataUpto(username);
		
		return totalActivatedScheme - maxDateCount;
	}
	public int countSchemeReviewed(String username)
	{
		return kpiPortedRepo.countSchemeReviewed(username);
	}
	public int countSchemePendingForReview(String username)
	{
		return kpiPortedRepo.countSchemePendingForReview(username);
	}
	public int countSchemeIntegrationErrors(String username)
	{
		return kpiPortedRepo.countSchemeIntegrationErrors(username);
	}
	
	
	public int countSchemeReviewed() 
	{
		return 0;
	}
	public int countSchemePendingForReview() 
	{
		return 0;
	}
	public int countSchemeIntegrationErrors() 
	{
		return 0;
	}
	
	
	public int countRejectedScheme()
	{
		return projRepo.countRejectedScheme();
	}
	public int countRejectedScheme(String username)
	{
		return projRepo.countRejectedScheme(username);
	}
	
	
}
