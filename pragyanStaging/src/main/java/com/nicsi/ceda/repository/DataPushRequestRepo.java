package com.nicsi.ceda.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nicsi.ceda.model.DataCleanRequest;
import com.nicsi.ceda.model.DataPushRequest;
@Repository
public interface DataPushRequestRepo extends JpaRepository<DataPushRequest, Integer>
{
	@Query("SELECT COUNT(*) from DataPushRequest where projectCode =?1 and pushedDate =?2")
	int countNoOfAttempts(int projectCode, Date pushedDate);
	
    @Modifying
    @Transactional
	@Query(value = "CALL public.nd_data_load_prayas_dashboard_custom_projectcode(:projectCode)", nativeQuery = true)
	void callProcedureForProjectCode(int projectCode);

    @Modifying
    @Transactional
	@Query(value = "CALL public.pm_scheduler()", nativeQuery = true)
	void callProcedure();

	List<DataPushRequest> findByUsername(String username);

	List<DataPushRequest> findByUsernameAndProjectCodeAndPushedDate(String username, int projectCode, Date date);
	
	List<DataPushRequest> findByStatusAndIsRejected(int status, int isRejected);
	List<DataPushRequest> findByStatus(int status);

	@Query("SELECT COUNT(*) from DataPushRequest where status = 0")
	int countDataPushRequest();
	@Query("SELECT COUNT(*) from DataPushRequest where status = 1")
	int countAcceptedPushRequest();
	@Query("SELECT COUNT(*) from DataPushRequest where status = 2")
	int countRejectedPushRequest();
	
	@Query("SELECT COUNT(*) from DataPushRequest where status = 0 and username =?1")
	int countDataPushRequest(String username);
	@Query("SELECT COUNT(*) from DataPushRequest where status = 1  and username =?1")
	int countAcceptedPushRequest(String username);
	@Query("SELECT COUNT(*) from DataPushRequest where status = 2 and username = ?1")
	int countRejectedPushRequest(String username);

	DataPushRequest findByProjectCodeAndStatus(int projectCode, int status);
	DataPushRequest findById(int id);

	List<DataPushRequest> findByStatusAndUsername(int i, String username);
	
	@Query("SELECT COUNT(*) from DataPushRequest where isPushCompleted = '1'")
	int countDataPushed();

	List<DataPushRequest> findByIsPushCompleted(String i);
	
}
