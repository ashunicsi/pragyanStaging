package com.nicsi.ceda.dao;

import org.springframework.stereotype.Component;

@Component(value = "iCountDetailsDao")
public interface ICountDetailsDao 
{
	int countRegisteredScheme();
	int countActivatedScheme();
	int countDeactivatedScheme();
	int countSubmittedScheme();
	int countRegisteredKPI();
	int countActivatedKPI();
	int countDeactivatedKPI();
	int countSchemeData();
	int countSchemeDataUpto();
	int countSchemeDataPendency();
	int countSchemeReviewed();
	int countSchemePendingForReview();
	int countSchemeIntegrationErrors();
	
	int countRegisteredScheme(String username);
	int countActivatedScheme(String username);
	int countDeactivatedScheme(String username);
	int countRegisteredKPI(String username);
	int countActivatedKPI(String username);
	int countDeactivatedKPI(String username);
	
	int countSchemeData(String username);
	int countSchemeDataUpto(String username);
	int countSchemeDataPendency(String username);
	int countSchemeReviewed(String username);
	int countSchemePendingForReview(String username);
	int countSchemeIntegrationErrors(String username);
	int countSubmittedScheme(String username);
	int countRejectedScheme();
	int countRejectedScheme(String username);
}
