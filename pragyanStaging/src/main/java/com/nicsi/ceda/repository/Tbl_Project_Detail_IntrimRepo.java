package com.nicsi.ceda.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;

public interface Tbl_Project_Detail_IntrimRepo extends JpaRepository<Tbl_Project_Detail_Intrim, Integer>
{
	Tbl_Project_Detail_Intrim findByProjectCode(int projCode);
	
	List<Tbl_Project_Detail_Intrim> findByStatus(int status);
	Tbl_Project_Detail_Intrim findById(int id);
	@Query(value="SELECT COUNT(*) from Tbl_Project_Detail_Intrim")
	int countRegisteredScheme();
	@Query(value="SELECT MAX(project_code) from Tbl_Project_Detail_Intrim", nativeQuery = true)
	int getMaxProjectCode();
	@Query(value="SELECT COUNT(*) from Tbl_Project_Detail_Intrim where status=1")
	int countActivatedScheme();
	
	@Query(value="SELECT COUNT(*) from Tbl_Project_Detail_Intrim where status=0")
	int countDeactivatedScheme();
	
	@Query(value="SELECT COUNT(*) from Tbl_Project_Detail_Intrim where Project_Approval_Status=0 and isRejected=0")
	int countSubmittedScheme();
	
	@Query(value="from Tbl_Project_Detail_Intrim where Ministry_Code = ?1 and Dept_Code = ?2")
	List<Tbl_Project_Detail_Intrim> findByMinistryCodeAndDeptCode(Integer ministryCode, Integer deptCode);
	@Query(value = "SELECT DISTINCT Project_name_e from Tbl_Project_Detail_Intrim where project_code = ?1 ")
	String getSchemeName(int ProjectCode);
	
	List<Tbl_Project_Detail_Intrim> findByUsername(String username);
	
	List<Tbl_Project_Detail_Intrim> findByUsernameAndStatus(String username, int status);
	
	@Query(value="SELECT COUNT(*) from Tbl_Project_Detail_Intrim where username = ?1")
	int countUserRegisteredScheme(String username);
	@Query(value="SELECT COUNT(*) from Tbl_Project_Detail_Intrim where status=1 and username = ?1 and Project_Approval_Status=1")
	int countUserActivatedScheme(String username);
	@Query(value="SELECT COUNT(*) from Tbl_Project_Detail_Intrim where status=0 and username = ?1 and Project_Approval_Status=1")
	int countUserDeactivatedScheme(String username);
	
	Tbl_Project_Detail_Intrim findByUsernameAndProjectCodeAndStatus(String username, int projectCode, int status);

	List<Tbl_Project_Detail_Intrim> findByUsernameAndIsRejected(String username, int status);

	Tbl_Project_Detail_Intrim findByUsernameAndProjectCode(String username, int projectCode);

	@Query(value="SELECT COUNT(*) from Tbl_Project_Detail_Intrim where Project_Approval_Status=0 and username = ?1 and isRejected=0")
	int countSubmittedScheme(String username);
	@Query(value="from Tbl_Project_Detail_Intrim where username = ?1 and Project_Approval_Status = ?2")
	List<Tbl_Project_Detail_Intrim> findByUsernameAndProjectApprovalStatus(String username, int status);

	@Query(value="SELECT COUNT(*) from Tbl_Project_Detail_Intrim where isRejected= 1")
	int countRejectedScheme();
	@Query(value="SELECT COUNT(*) from Tbl_Project_Detail_Intrim where isRejected= 1 and username = ?1")
	int countRejectedScheme(String username);
	@Query(value="from Tbl_Project_Detail_Intrim where Project_Approval_Status= ?1")
	List<Tbl_Project_Detail_Intrim> findByProject_Approval_Status(int status);

	List<Tbl_Project_Detail_Intrim> findByIsRejected(int status);
	@Query(value="from Tbl_Project_Detail_Intrim where Project_Approval_Status= ?1 and isRejected= ?2")
	List<Tbl_Project_Detail_Intrim> findByProject_Approval_StatusAndIsRejected(int status, int j);

	List<Tbl_Project_Detail_Intrim> findByStatusAndIsRejected(int status, int i);
	
	@Query(value="from Tbl_Project_Detail_Intrim where username= ?1 and Project_Approval_Status= ?2 and isRejected= ?3 and Project_Approval_Status= ?4")
	List<Tbl_Project_Detail_Intrim> findByUsernameAndStatusAndIsRejectedAndProject_Approval_Status(String usrename, int status, int i, int j);

	Tbl_Project_Detail_Intrim findByProjectCodeAndStatus(int projectCode, int i);

	Tbl_Project_Detail_Intrim findByProjectCodeAndStatusAndUsername(int schemeCode, int i, String username);

	Tbl_Project_Detail_Intrim findByProjectCodeAndUsername(int schemeCode, String username);
	
	
	

	
	
}
