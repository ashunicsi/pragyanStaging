package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.DataFrequency;

public interface DataFrequencyRepo extends JpaRepository<DataFrequency, Long>
{
	@Query(value="SELECT d.frequencyNameE from DataFrequency d where d.id=?1")
	String getDataFrequencyName(Long id);
	List<DataFrequency> findByStatus(int status);
	
}
