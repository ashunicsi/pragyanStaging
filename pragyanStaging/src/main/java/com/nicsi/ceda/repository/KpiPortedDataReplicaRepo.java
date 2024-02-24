package com.nicsi.ceda.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.KPI_PORTED_DATA_REPLICA;

public interface KpiPortedDataReplicaRepo extends JpaRepository<KPI_PORTED_DATA_REPLICA, Long>
{
	@Query(value="select * from public.kpi_ported_data_replica where project_code = ?1 and date(data_date) = ?2", nativeQuery = true)
	List<KPI_PORTED_DATA_REPLICA> findByDate(int projectCode, Date date);

	List<KPI_PORTED_DATA_REPLICA>  findByProjectCodeAndDataDateBetween(int projectCode, Date fDate, Date tDate);
}
