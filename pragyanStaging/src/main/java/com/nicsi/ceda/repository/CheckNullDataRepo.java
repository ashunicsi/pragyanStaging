package com.nicsi.ceda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.CheckNullData;

public interface CheckNullDataRepo extends JpaRepository<CheckNullData, Long>
{

}
