package com.nicsi.ceda.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.Tbl_Project_KPI_Detail_Intrim;

public interface Tbl_Project_KPI_Detail_IntrimRepo extends JpaRepository<Tbl_Project_KPI_Detail_Intrim, Integer>
{
	Tbl_Project_KPI_Detail_Intrim findByKpiId(int kpiId);
	@Query(value="SELECT COUNT(*) from Tbl_Project_KPI_Detail_Intrim")
	int countRegisteredKPI();
	@Query(value="SELECT COUNT(*) from Tbl_Project_KPI_Detail_Intrim where status = 1")
	int countActivatedKPI();
	@Query(value="SELECT COUNT(*) from Tbl_Project_KPI_Detail_Intrim where status = 0")
	int countDeactivatedKPI();
	@Query(value="SELECT COUNT(*) from Tbl_Project_KPI_Detail_Intrim where project_code = ?1")
	int countKPI(int projectCode);
	
	//@Query(value="select t.kpi_name_e From Tbl_Project_KPI_Detail_Intrim t where status = 1", nativeQuery = true)
	List<Tbl_Project_KPI_Detail_Intrim>findByStatus(int status);
	//List<Tbl_Project_KPI_Detail_Intrim>findByStatus(int status);
	
	List<Tbl_Project_KPI_Detail_Intrim> findByProjectCode(int projectCode);
	List<Tbl_Project_KPI_Detail_Intrim> findByUsernameAndStatus(String username, int status);
	
	@Query(value="SELECT COUNT(*) from Tbl_Project_KPI_Detail_Intrim where username = ?1")
	int countUserRegisteredKPI(String username);
	@Query(value="SELECT COUNT(*) from Tbl_Project_KPI_Detail_Intrim where status = 1 and username = ?1")
	int countUserActivatedKPI(String username);
	@Query(value="SELECT COUNT(*) from Tbl_Project_KPI_Detail_Intrim where status = 0 and username = ?1")
	int countUserDeactivatedKPI(String username);
	
	Tbl_Project_KPI_Detail_Intrim findByKpiIdAndUsernameAndProjectCodeAndStatus(int kpiId, String username,
			int projectCode, int staus);
	
	Tbl_Project_KPI_Detail_Intrim findByKpiIdAndProjectCode(int kpiId, int projectNumber);
	Tbl_Project_KPI_Detail_Intrim findByKpiIdAndUsernameAndProjectCode(int kpiId, String username, int projectCode);
	@Query(value="SELECT COUNT(*) from Tbl_Project_KPI_Detail_Intrim where status=1 and projectCode = ?1")
	int countActiveKpi(int projectCode);
	
	Tbl_Project_KPI_Detail_Intrim findByProjectCodeAndKpiIdAndStatus(int projectCode, int kpiId, int status);
}
