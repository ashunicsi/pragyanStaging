package com.nicsi.ceda.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.Tbl_DataPort_KPI_PORTED_DATA;

public interface Tbl_DataPort_KPI_PORTED_DATARepo extends JpaRepository<Tbl_DataPort_KPI_PORTED_DATA, Long>
{
	List<Tbl_DataPort_KPI_PORTED_DATA> findByProjectCode(Long projectCode);
	Page<Tbl_DataPort_KPI_PORTED_DATA> findByProjectCode(Long projectCode, org.springframework.data.domain.PageRequest page);
	Page<Tbl_DataPort_KPI_PORTED_DATA> findByKpiId(int kpiId, org.springframework.data.domain.PageRequest page);
	List<Tbl_DataPort_KPI_PORTED_DATA> findAll();
	@Query(value="from Tbl_DataPort_KPI_PORTED_DATA where projectCode = ?1")
	List<Tbl_DataPort_KPI_PORTED_DATA> findByProjCode(Long projectCode);
	
	@Query("SELECT MAX (id)  from Tbl_DataPort_KPI_PORTED_DATA")
	Long getMaxId();
	@Query("From Tbl_DataPort_KPI_PORTED_DATA where id = :id")
	Tbl_DataPort_KPI_PORTED_DATA byId(Long id);
	
	
	@Query("SELECT count(*) from  Tbl_DataPort_KPI_PORTED_DATA")
	int countSchemeData();
	
	@Query("SELECT  MAX(entrydt) AS  entrydt from Tbl_DataPort_KPI_PORTED_DATA")
	Timestamp getSchemeMaxDate();
	@Query("SELECT  MAX(entrydt) AS  entrydt from Tbl_DataPort_KPI_PORTED_DATA where username= :username")
	Timestamp getMaxDateForUsername(String username);
	
	@Query("SELECT count(distinct project_code) from  Tbl_DataPort_KPI_PORTED_DATA")
	int countSchemeDataReceived();
	
	@Query("SELECT count(*) from  Tbl_DataPort_KPI_PORTED_DATA  where cast(entrydt as date)= :entrydt")
	int countSchemeDataUpto(Timestamp entrydt);
	
	@Query("SELECT count(*) from  Tbl_DataPort_KPI_PORTED_DATA where projectCode = ?1")
	int countSchemeData(int projectCode);
	
	
	//@Query(value="SELECT COUNT(*) from Tbl_DataPort_KPI_PORTED_DATA where username = ?1")
	@Query("SELECT count(distinct project_code) from  Tbl_DataPort_KPI_PORTED_DATA where username= :username")
	int countSchemeDataUpto(String username);
	
	@Query(value="SELECT COUNT(*) from Tbl_DataPort_KPI_PORTED_DATA where username = ?1")
	int countSchemeDataPendency(String username);
	
	@Query(value="SELECT COUNT(*) from Tbl_DataPort_KPI_PORTED_DATA where username = ?1")
	int countSchemeReviewed(String username);
	
	@Query(value="SELECT COUNT(*) from Tbl_DataPort_KPI_PORTED_DATA where username = ?1")
	int countSchemePendingForReview(String username);
	
	@Query(value="SELECT COUNT(*) from Tbl_DataPort_KPI_PORTED_DATA where username = ?1")
	int countSchemeIntegrationErrors(String username);
	
	@Query("SELECT count(*) from  Tbl_DataPort_KPI_PORTED_DATA where username = ?1")
	int countSchemeData(String username);
	
	//@Query(value = "select * from public.tbl_data_port_kpi_ported_data where cast(entrydt as date)= ?1 and username= ?2", nativeQuery = true)
	@Query(value = "select * from public.tbl_data_port_kpi_ported_data where cast(entrydt as date)= ?1 and username= ?2 limit 500", nativeQuery = true)
	List<Tbl_DataPort_KPI_PORTED_DATA> lastPushedData(Timestamp entryDate, String username);
	
	
	@Query("DELETE from  Tbl_DataPort_KPI_PORTED_DATA where projectCode = ?1")
	int deleteByProjectCode(Long projectCode);
	@Query("DELETE from  Tbl_DataPort_KPI_PORTED_DATA where id = ?1")
	int deleteByid(int id);
	@Query("DELETE from  Tbl_DataPort_KPI_PORTED_DATA where kpiId = ?1")
	int deleteByKpiId(int id);
	
	@Query(value = "SELECT DISTINCT datadt FROM public.tbl_data_port_kpi_ported_data where project_code = ?1", nativeQuery =  true)
	List<Timestamp> getDistinctDateWhereProjectCode(Long projectCode);
	@Query(value = "SELECT DISTINCT mode_frequency_id FROM public.tbl_data_port_kpi_ported_data where project_code = ?1", nativeQuery =  true)
	Integer getDistinctModeFrequencyIdWhereProjectCode(Long projectCode);
	@Query(value="select MAX(entrydt) from  public.tbl_data_port_kpi_ported_data where project_code= ?1 and entrydt < ?2", nativeQuery = true)
	Timestamp getMaxDateByDataDate(Integer projectCode, Date  entrydt);
	
	@Query(value="select distinct(entrydt) from  public.tbl_data_port_kpi_ported_data where project_code= ?1 and cast(datadt as date) = ?2", nativeQuery = true)
	Timestamp getDistinctReocrdByProjectCodeAndEntryDate(long longValue, java.util.Date datadt);
	
}
