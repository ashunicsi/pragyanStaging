package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.RuleType;

public interface RuleTypeRepo extends JpaRepository<RuleType, Long>
{
	List<RuleType> findByStatus(int status);
}
