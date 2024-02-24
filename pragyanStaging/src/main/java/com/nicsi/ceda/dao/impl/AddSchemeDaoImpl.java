package com.nicsi.ceda.dao.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.nicsi.ceda.dao.IAddSchemeDao;
import com.nicsi.ceda.model.DataGranularityDetails;
import com.nicsi.ceda.model.Sector;
import com.nicsi.ceda.repository.DataGranularityDetailsRepo;
import com.nicsi.ceda.repository.SectorRepo;

@Repository
public class AddSchemeDaoImpl implements IAddSchemeDao
{

	@Autowired
	private DataGranularityDetailsRepo granularityRepo;
	@Autowired
	private SectorRepo sectorRepo;
	
	public String addScheme(ModelMap map) 
	{
		List<DataGranularityDetails> data = granularityRepo.findByStatus(1);
		data = data.stream().sorted(Comparator.comparing(DataGranularityDetails::getData_Granularity_Name_e)).collect(Collectors.toList());
		map.addAttribute("data", data);
		List<Sector> sector = sectorRepo.findByStatus(1);
		sector = sector.stream().sorted(Comparator.comparing(Sector::getSector_Name_e)).collect(Collectors.toList());
		map.addAttribute("sector", sector);
		return "addScheme";
	}
	
}
