package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.ListOfUpdatedScheme;

public interface ListOfUpdatedSchemeRepo extends JpaRepository<ListOfUpdatedScheme, Integer>
{

	@Query(value="SELECT COUNT(*) from ListOfUpdatedScheme where status = ?1")
	int countCleanRequest(int status);

	List<ListOfUpdatedScheme> findByStatus(int i);

	ListOfUpdatedScheme findByProjectCode(int projectCode);

	ListOfUpdatedScheme findByProjectCodeAndStatus(int projectCode, int status);

}
