package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.RecvApiDataLogsToDisplay;

public interface RecvApiDataLogsToDisplayRepo extends JpaRepository<RecvApiDataLogsToDisplay, Integer>
{
	List<RecvApiDataLogsToDisplay> findByProjectCode(int projecrCode);
	RecvApiDataLogsToDisplay findById(int id);
}
