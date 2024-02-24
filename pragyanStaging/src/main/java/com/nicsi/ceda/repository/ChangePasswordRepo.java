package com.nicsi.ceda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nicsi.ceda.model.ChangePassword;

@Repository
public interface ChangePasswordRepo extends JpaRepository<ChangePassword, Integer>
{

	ChangePassword findByEmailId(String emailId);
	
	//@Modifying 
    //@Query(value = "DELETE FROM ChangePassword WHERE emailId = ?1")
    //int deleteByEmailId(@Param("email") String email); 
	
	ChangePassword findByEmailIdAndCurrentPassword(String emailId, String password);
	long deleteByEmailId(String emailId);
}
