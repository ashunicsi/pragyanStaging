package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.ListOfUpdatedKPI;
import com.nicsi.ceda.model.ListOfUpdatedScheme;

public interface ListOfUpdatedKPIRepo extends JpaRepository<ListOfUpdatedKPI, Integer>
{
	@Query(value="SELECT COUNT(*) from ListOfUpdatedKPI where status = ?1")
	int countKpiNameRequest(int i);

	List<ListOfUpdatedKPI> findByStatus(int i);

	ListOfUpdatedKPI findByKpiIdAndStatus(int kpiId, int i);

	ListOfUpdatedKPI findByKpiIdAndStatusAndProjectCode(int kpiId, int i, int projectCode);
}
