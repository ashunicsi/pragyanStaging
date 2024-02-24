package com.nicsi.ceda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.OldCodesOfStates;

public interface CodesOfStatesRepo extends JpaRepository<OldCodesOfStates, Integer>
{

}
