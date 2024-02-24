package com.nicsi.ceda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.UserActivities;

public interface UserActivitiesRepo extends JpaRepository<UserActivities, String>
{
	
	
}
