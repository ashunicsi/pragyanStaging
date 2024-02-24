package com.nicsi.ceda.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.Tbl_DataPort_KPI_PORTED_DATA;
import com.nicsi.ceda.model.Tbl_DataPort_KPI_PORTED_DATADeleted;

public interface Tbl_DataPort_KPI_PORTED_DATADeletedRepo extends JpaRepository<Tbl_DataPort_KPI_PORTED_DATADeleted, Long>
{
	List<Tbl_DataPort_KPI_PORTED_DATA> findByProjectCode(Long projectCode);
	
	@Query("SELECT MAX (id)  from Tbl_DataPort_KPI_PORTED_DATADeleted")
	Long getMaxId();
	@Query("From Tbl_DataPort_KPI_PORTED_DATA where id = :id")
	Tbl_DataPort_KPI_PORTED_DATA byId(Long id);
	
	
	@Query("SELECT count(*) from  Tbl_DataPort_KPI_PORTED_DATADeleted")
	int countSchemeData();
	
	@Query("SELECT  MAX(entrydt) AS  entrydt from Tbl_DataPort_KPI_PORTED_DATADeleted")
	Timestamp getSchemeMaxDate();
	@Query("SELECT  MAX(entrydt) AS  entrydt from Tbl_DataPort_KPI_PORTED_DATADeleted where username= :username")
	Timestamp getMaxDateForUsername(String username);
	
	@Query("SELECT count(*) from  Tbl_DataPort_KPI_PORTED_DATADeleted  where cast(entrydt as date)= :entrydt")
	int countSchemeDataUpto(Timestamp entrydt);
	
	@Query("SELECT count(*) from  Tbl_DataPort_KPI_PORTED_DATADeleted where projectCode = ?1")
	int countSchemeData(int projectCode);
	
	
	@Query(value="SELECT COUNT(*) from Tbl_DataPort_KPI_PORTED_DATADeleted where username = ?1")
	int countSchemeDataUpto(String username);
	
	@Query(value="SELECT COUNT(*) from Tbl_DataPort_KPI_PORTED_DATADeleted where username = ?1")
	int countSchemeDataPendency(String username);
	
	@Query(value="SELECT COUNT(*) from Tbl_DataPort_KPI_PORTED_DATADeleted where username = ?1")
	int countSchemeReviewed(String username);
	
	@Query(value="SELECT COUNT(*) from Tbl_DataPort_KPI_PORTED_DATADeleted where username = ?1")
	int countSchemePendingForReview(String username);
	
	@Query(value="SELECT COUNT(*) from Tbl_DataPort_KPI_PORTED_DATADeleted where username = ?1")
	int countSchemeIntegrationErrors(String username);
	
	@Query("SELECT count(*) from  Tbl_DataPort_KPI_PORTED_DATADeleted where username = ?1")
	int countSchemeData(String username);
	
	//@Query(value = "select * from public.tbl_data_port_kpi_ported_data where cast(entrydt as date)= ?1 and username= ?2", nativeQuery = true)
	@Query(value = "select * from public.Tbl_DataPort_KPI_PORTED_DATADeleted where cast(entrydt as date)= ?1 and username= ?2 limit 500", nativeQuery = true)
	List<Tbl_DataPort_KPI_PORTED_DATA> lastPushedData(Timestamp entryDate, String username);
	
	@Transactional
	@Modifying
	@Query("DELETE from  Tbl_DataPort_KPI_PORTED_DATADeleted where projectCode = ?1")
	int deleteByProjectCode(Long projectCode);
	
	
}
