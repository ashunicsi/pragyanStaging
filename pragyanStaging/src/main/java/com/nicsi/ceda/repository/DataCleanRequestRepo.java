package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.DataCleanRequest;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;

public interface DataCleanRequestRepo extends JpaRepository<DataCleanRequest, Integer>
{

	DataCleanRequest findByUsernameAndProjectCodeAndStatus(String username, int projectCode, int status);

	List<DataCleanRequest> findByUsername(String username);

	@Query(value="delete from DataCleanRequest where username= ?1 and id= ?2")
	DataCleanRequest deleteDataCleanRequest(String username, int id);
	
	//user
	@Query(value="SELECT COUNT(*) from DataCleanRequest where  status = 0 and isRejected = 0 and username=?1")
	int countDataCleanRequest(String username);
	@Query(value="SELECT COUNT(*) from DataCleanRequest where isRejected= 1  and username=?1")
	int countRejectedCleanRequest(String username);	
	@Query(value="SELECT COUNT(*) from DataCleanRequest where status = 1 and isRejected = 0  and username=?1")
	int countAcceptedCleanRequest(String username);
	//Admin
	@Query(value="SELECT COUNT(*) from DataCleanRequest where  status = 0 and isRejected = 0")
	int countDataCleanRequest();
	@Query(value="SELECT COUNT(*) from DataCleanRequest where isRejected = 1")
	int countRejectedCleanRequest();	
	@Query(value="SELECT COUNT(*) from DataCleanRequest where status = 1 and isRejected = 0")
	int countAcceptedCleanRequest();

	List<DataCleanRequest> findByStatusAndIsRejected(int i, int j);

	DataCleanRequest findByProjectCodeAndIsRejectedAndStatus(int projectCode, int i, int j);

	List<DataCleanRequest> findByStatusAndIsRejectedAndUsername(int i, int j, String username);

	DataCleanRequest findByProjectCodeAndIsRejectedAndStatusAndUsername(int projectCode, int i, int j, String username);
	
}
