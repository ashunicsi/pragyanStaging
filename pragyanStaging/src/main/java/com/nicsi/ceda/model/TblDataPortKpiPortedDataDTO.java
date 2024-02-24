package com.nicsi.ceda.model;

import java.sql.Timestamp;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TblDataPortKpiPortedDataDTO 
{
	private Long id;
	private Integer instanceId;
	private Integer secCode;
	private Integer ministryCode;
	private Integer deptCode;
	private Long projectCode;
	private Integer modeFrequencyId;
	private Integer atmpt;
	private Timestamp datadt;
	private Integer level1Code;
	private Integer level2Code;
	private Integer level3Code;
	private Integer level4Code;
	private Integer level5Code;
	private Integer level6Code;
	private Integer level7Code;
	private Integer level8Code;
	private Integer level9Code;
	private Integer level10Code;
	private Integer kpiId;
	private Double similarity;
	private Double kpiData;
	private Integer dataGroupId;
	private Integer isComplete;
	private Timestamp entrydt;
	private Integer migrated;
	
	private String username;
}
