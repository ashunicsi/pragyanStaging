package com.nicsi.ceda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.ListOfTokens;



public interface ListOfTokensRepo extends JpaRepository<ListOfTokens, Integer>
{
	ListOfTokens findByProjectCode(int projectCode);
}
