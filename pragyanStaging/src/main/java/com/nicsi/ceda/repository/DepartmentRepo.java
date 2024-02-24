package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.M_Department;

public interface DepartmentRepo extends JpaRepository<M_Department, Integer>
{
	List<M_Department> findBySectorId(int sectorId);
	@Query(value="select *  from M_Department d where d.sector_Id=?1 GROUP BY   d.ministry_Id, d.id, d.ministry_Name_e", nativeQuery = true)
	List<M_Department> findAllMinistryWhereSectorId(int sectorId);
	
	List<M_Department> findByMinistryId(int ministryId);
}
