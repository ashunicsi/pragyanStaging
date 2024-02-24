package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.MKPIValidationRuleType;

public interface MKPIValidationRuleTypeRepo extends JpaRepository<MKPIValidationRuleType, Integer>
{
	List<MKPIValidationRuleType> findByStatus(int status);
}
