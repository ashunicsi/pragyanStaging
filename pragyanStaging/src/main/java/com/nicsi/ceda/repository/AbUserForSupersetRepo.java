package com.nicsi.ceda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.AbUserForSuperset;

public interface AbUserForSupersetRepo extends JpaRepository<AbUserForSuperset, Integer>
{
	@Query(value="SELECT COUNT(*) from AbUserForSuperset")
	int countTotalRecord();
}
