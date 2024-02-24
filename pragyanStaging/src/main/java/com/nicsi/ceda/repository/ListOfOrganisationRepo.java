package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.ListOfOrganisation;

public interface ListOfOrganisationRepo extends JpaRepository<ListOfOrganisation, Integer>
{
	List<ListOfOrganisation> findByStatusAndLanguage(int i, String language);
}
