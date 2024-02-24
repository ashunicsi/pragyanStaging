package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.DataPortingTime;

public interface DataPortingTimeRepo extends JpaRepository<DataPortingTime, Long>
{
	List<DataPortingTime> findByStatus(int status);
}
