package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.DataPortingMode;

public interface DataPortingModeRepo extends JpaRepository<DataPortingMode, Long>
{
	List<DataPortingMode> findByStatus(int status);
	@Query(value="SELECT d.portingModeEn from DataPortingMode d where d.id=?1")
	String getDataPortingModeName(Long id);
}
