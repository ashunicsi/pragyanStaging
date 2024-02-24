package com.nicsi.ceda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.ListOfKpiForReport;

public interface ListOfKpiForReportRepo extends JpaRepository<ListOfKpiForReport, Integer>
{

	ListOfKpiForReport findByKpiIdAndSchemeId(int kpiId, int schemeCode);


	

}
