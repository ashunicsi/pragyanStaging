package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.DataCalendarType;

public interface DataCalendarTypeRepo extends JpaRepository<DataCalendarType, Integer>
{
	List<DataCalendarType> findByStatus(int status);
	@Query(value="SELECT d.dataCalendarTypeName from DataCalendarType d where d.id=?1")
	String getCalenderTypeName(int id);
}
