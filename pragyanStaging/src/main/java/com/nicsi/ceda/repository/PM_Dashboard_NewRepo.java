package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.PM_Dashboard_New;

public interface PM_Dashboard_NewRepo extends JpaRepository<PM_Dashboard_New, Integer>
{
	@Query(value = "Select pd.project_code, pd.state_name,district_name,sum((indicator='Farmers Registered')::int) \"Villages Verified as ODF\",sum((indicator='Total Number of Villages')::int) \"Total Number of Villages\",sum((indicator='Village Declared as ODF')::int) \"Village Declared as ODF\",sum((indicator='District Declared as ODF')::int) \"District Declared as ODF\",sum((indicator='Toilets Constructed')::int) \"Toilets Constructed\"FROM pm_dashboard_new  as pd inner join KPI_Cumulative_Current k on k.project_code = pd.project_code and k.Label = pd.label where pd.project_code= 1003   GROUP BY pd.project_code, pd.state_name,Indicator,district_name;", nativeQuery = true)
	List<Object> getData();
}
