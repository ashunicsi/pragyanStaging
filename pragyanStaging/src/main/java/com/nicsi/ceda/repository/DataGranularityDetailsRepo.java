package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.DataGranularityDetails;

public interface DataGranularityDetailsRepo extends JpaRepository<DataGranularityDetails, Integer>
{
	List<DataGranularityDetails> findAll();

	List<DataGranularityDetails> findByStatus(int status);
	DataGranularityDetails findById(int id);
	DataGranularityDetails findByDataGranularityId(int id);

	List<DataGranularityDetails> findByStatusAndIsFixed(int status, int isFixed);
}
