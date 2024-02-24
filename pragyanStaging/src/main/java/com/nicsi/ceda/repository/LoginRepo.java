package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.Login;

public interface LoginRepo extends JpaRepository<Login, Integer>
{
	Login findByEmailId(String emailId);
	Login findByPassword(String password);
	List<Login> findByStatusAndUserTypeAndIsSuperAdmin(int status, String userType, int isSuperAdmin);
	@Query(value="SELECT COUNT(*) from Login where flag = 1  and isSuperAdmin=1")
	int countActivatedUser();
	@Query(value="SELECT COUNT(*) from Login where flag = 0 and isSuperAdmin=1")
	int countDeactivatedUser();
	Login findByEmailIdAndUserType(String emailId, String string);
	List<Login> findByIsLocked(String string);
	
	@Query(value="SELECT COUNT(*) from Login where status = 1 and createdBy =? 1")
	int countActivatedUser(String username);
	@Query(value="SELECT COUNT(*) from Login where status = 0 and createdBy =? 1")
	int countDeactivatedUser(String username);
	List<Login> findByStatusAndCreatedBy(int i, String string);
	List<Login> findByStatus(int status);
	List<Login> findByStatusAndIsSuperAdmin(int i, int j);
	Login findByEmailIdAndIsApproved(String username, int approveStatus);
	List<Login> findByIsApproved(int i);
	List<Login> findByFlagAndIsSuperAdmin(int i, int j);
}
