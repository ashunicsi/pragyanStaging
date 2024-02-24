package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.MLevel;

public interface MLevelRepo extends JpaRepository<MLevel, Long>
{
	List<MLevel> findByDataGranularityId(int granularity_ID);
	List<MLevel> findByLevel2Code(int levelCode);
	List<MLevel> findAll();
	MLevel findDistinctByLevel2CodeAndDataGranularityId(int levelCode, int data_granularity_id);
	MLevel findDistinctByLevel3NameEAndDataGranularityId(int levelCode, int data_granularity_id);
	MLevel findDistinctByLevel4NameEAndDataGranularityId(int levelCode, int data_granularity_id);
	MLevel findDistinctByLevel5NameEAndDataGranularityId(int levelCode, int data_granularity_id);
	MLevel findDistinctByLevel6NameEAndDataGranularityId(int levelCode, int data_granularity_id);
	MLevel findDistinctByLevel7NameEAndDataGranularityId(int levelCode, int data_granularity_id);
	MLevel findDistinctByLevel8NameEAndDataGranularityId(int levelCode, int data_granularity_id);
	MLevel findDistinctByLevel9NameEAndDataGranularityId(int levelCode, int data_granularity_id);
	MLevel findDistinctByLevel10NameEAndDataGranularityId(int levelCode, int data_granularity_id);

	@Query(value="SELECT DISTINCT  m.level2_name_e from M_Level m where m.level2_code=?1 and m.Data_Granularity_ID=?2 and m.isLatest=1", nativeQuery = true)
	String getDistinctState(int levelCode, int granulartyId);
	
	@Query(value="SELECT DISTINCT  m.level3_name_e from M_Level m where m.level3_code=?1 and m.Data_Granularity_ID=?2 and m.isLatest=1", nativeQuery = true)
	String getDistinctDistrict(int levelCode, int granulartyId);
	
	@Query(value="SELECT DISTINCT  m.level4_name_e from M_Level m where m.level4_code=?1 and m.Data_Granularity_ID=?2 and m.isLatest=1", nativeQuery = true)
	String getDistinctBlock(int levelCode, int granulartyId);
	
	@Query(value="SELECT DISTINCT  m.level5_name_e from M_Level m where m.level5_code=?1 and m.Data_Granularity_ID=?2 and m.isLatest=1", nativeQuery = true)
	String getDistinctVillage(int levelCode, int granulartyId);
	@Query(value="SELECT DISTINCT  m.level5_name_e from M_Level m where m.level5_code=?1 and m.Data_Granularity_ID=?2 and m.isLatest=1", nativeQuery = true)
	String getDistinctPanchayat(int levelCode, int granulartyId);
	
	//@Query(value="select *  from M_Level m where m.level2_code=?1 and m.Data_Granularity_ID=?2 and m.isLatest=1", nativeQuery = true)
	@Query(value="select *  from M_Level m where m.level2_code=?1 and m.Data_Granularity_ID=?2", nativeQuery = true)
	List<MLevel> getDistrict(int stateCode, int granulartyId);
	
	@Query(value="select *  from M_Level m where m.level3_code=?1 and m.Data_Granularity_ID=?2 and m.isLatest=1", nativeQuery = true)
	List<MLevel> getBlock(int districtCode, int granulartyId);
	
	@Query(value="select *  from M_Level m where m.level4_code=?1 and m.Data_Granularity_ID=?2 and m.isLatest=1", nativeQuery = true)
	List<MLevel> getVillage(int blockCode, int granulartyId);
	
	@Query(value="select *  from M_Level m where m.level4_code=?1 and m.Data_Granularity_ID=?2 and m.isLatest=1", nativeQuery = true)
	List<MLevel> getGramPanchayat(int districtCode, int granulartyId);
	
	
	@Query(value="SELECT DISTINCT  m.level2_name_e from M_Level m where m.level2_code=?1 and m.Data_Granularity_ID=?2 and m.isLatest=1", nativeQuery = true)
	String getMinistryName(int ministryId, int granularityId);
	@Query(value="SELECT DISTINCT  m.level3_name_e from M_Level m where m.level3_code=?1 and m.Data_Granularity_ID=?2 and m.isLatest=1", nativeQuery = true)
	String getDepartmentName(int deptId, int granularityId);
	
	@Query(value="select *  from M_Level m where m.level2_code=?1 and m.Data_Granularity_ID=?2 and m.isLatest=1", nativeQuery = true)
	List<MLevel> getDistrictPanchayat(int parseInt, int i);
	
}
