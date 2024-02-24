package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.MKPIValidationOperator;

public interface MKPIValidationOperatorRepo extends JpaRepository<MKPIValidationOperator, Integer>
{
	List<MKPIValidationOperator> findByStatus(int status);
	List<MKPIValidationOperator> findByOperatorType(int operatorType);
}
