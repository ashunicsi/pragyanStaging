package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.MUnit;

public interface MUnitRepo extends JpaRepository<MUnit, Integer>
{
	List<MUnit> findByStatus(int status);
	
	MUnit findByUnitId(int id);
}
