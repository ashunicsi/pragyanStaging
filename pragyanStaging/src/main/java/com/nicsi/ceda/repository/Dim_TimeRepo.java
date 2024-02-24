package com.nicsi.ceda.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.Dim_Time;

public interface Dim_TimeRepo extends JpaRepository<Dim_Time, Long>
{
	
	Dim_Time findByDdate(Date date);
}
