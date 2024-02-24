package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.UserTempTableData;

public interface UserTempTableDataRepo extends JpaRepository<UserTempTableData, Integer>
{
	List<UserTempTableData> findAll();
	
	@Query("DELETE from  UserTempTableData")
	void deleteAll();
}
