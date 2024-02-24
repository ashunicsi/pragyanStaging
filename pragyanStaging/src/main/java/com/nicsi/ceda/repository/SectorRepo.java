package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.Sector;

public interface SectorRepo extends JpaRepository<Sector, Integer>
{
	List<Sector> findByStatus(int status);
	Sector findBySectorId(int sectorId);
	
	
}
