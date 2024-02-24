package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.MSchemeDepMinistrySecMap;

public interface MSchemeDepMinistrySecMapRepo extends JpaRepository<MSchemeDepMinistrySecMap, Long>
{
	List<MSchemeDepMinistrySecMap> findByNewDepCode(int depCode);
	List<MSchemeDepMinistrySecMap> findByMinistryId(int sectorid);
}
