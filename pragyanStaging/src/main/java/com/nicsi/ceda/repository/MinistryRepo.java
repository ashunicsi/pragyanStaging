package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nicsi.ceda.model.ListOfMinistry;
@Repository
public interface MinistryRepo extends JpaRepository<ListOfMinistry, Integer>
{
	ListOfMinistry findBymId(int id);

	List<ListOfMinistry> findByStatusAndLanguage(int i, String language);

	//ListOfMinistry findByStatusAndmId(int i, int id);
	ListOfMinistry findByMinistryCode(String ministryCode);
	
}
