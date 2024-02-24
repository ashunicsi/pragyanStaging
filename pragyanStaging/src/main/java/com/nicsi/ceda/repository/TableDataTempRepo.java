package com.nicsi.ceda.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.DataCleanRequest;
import com.nicsi.ceda.model.TableDataTemp;

@Transactional
public interface TableDataTempRepo extends JpaRepository<TableDataTemp, Integer>
{
    @Modifying
	@Query(value="delete from TableDataTemp where id= ?1")
	void deleteById(int id);

	List<TableDataTemp> findByProjectCode(int projectCode);
}
