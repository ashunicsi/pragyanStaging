package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.PortingLanguage;

public interface PortingLanguageRepo extends JpaRepository<PortingLanguage, Integer>
{
	List<PortingLanguage> findByFlag(int flag);
	PortingLanguage findByPortLanguageId(int id);
}
