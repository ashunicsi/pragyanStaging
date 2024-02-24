package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.KPIDataDisplayType;

public interface KPIDataDisplayTypeRepo extends JpaRepository<KPIDataDisplayType, Integer>
{
	List<KPIDataDisplayType> findByStatus(int status);
}
