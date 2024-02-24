package com.nicsi.ceda.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.RecvApiDataLogs;

public interface RecvApiDataLogsRepo extends JpaRepository<RecvApiDataLogs, Integer>
{
	List<RecvApiDataLogs> findByProjectCode(int projectCode);
	@Query(value="select MAX(entry_date) from public.recv_api_data_logs where project_code=?1", nativeQuery = true)
	Date getMaxEntryDate(int projectCode);
    //@Query(value = "SELECT row_to_json(t) AS jsonData FROM (SELECT * FROM tbl_data_port_kpi_ported_data where project_code=?1) t", nativeQuery = true)
  @Query(value = "SELECT row_to_json(recv_api_data_logs) FROM recv_api_data_logs where project_code=?", nativeQuery = true)
	List<String> findByProjectCodeInJSON(int projectCode);

	//@Query(value="SELECT row_to_json(recv_api_data_logs) from recv_api_data_logs where project_code=?1", nativeQuery = true)
	
	List<RecvApiDataLogs> findByProjectCodeAndEntryDate(int projectCode, Date entryDate);
	RecvApiDataLogs findById(int id);
}
