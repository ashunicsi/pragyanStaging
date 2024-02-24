package com.nicsi.ceda.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

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
public class TempTbl_Project_KPI_Detail_Intrim 
{
	private Integer instanceCode;
	private Integer instanceTypeCodemain;
	private Integer instanceTypeCodeSub;
	private Integer instanceLvlCode;
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
	private Integer secCode;
	private Integer ministryCode;
	private Integer deptCode;
	private Integer projectCode;
	private Integer kpiId;
	private Integer kpiIdActual;
	private String kpiNameE;
	private String kpiNameR;
	private Integer kpiUnitId;
	private String kpiUnitNameE;
	private String kpiUnitNameR;
	private String kpiTooltipE;
	private String kpiTooltipR;
	private Integer kpiCategoryId;
	private String kpiCategoryNameE;
	private Integer kpiPercentageId;
	private Integer kpiPercentageIdActual;
	private String kpiPercentageName;
	private Integer kpiDataTypeId;
	private String kpiDataTypeName;
	private Integer kpiDataDisplayTypeId;
	private String kpiDataDisplayTypeName;
	private Integer isDataReset;
	private Integer dataResetFrequencyId;
	private String dataResetFrequencyName;
	private Integer isDataAggregate;
	private double kpiDataMinLimit;
	private double kpiDataMaxLimit;
	private Character  kpiTypeStatus;
	private Integer isUpdated;
	private Integer modifiyPermission;
	private Integer deletePermission;
	private Integer status;
	private Integer lockStatus;
	private Integer projectApprovalStatus;
	private Calendar projectApprovedDate;
	private Integer entrybyUserCode;
	private Integer entrybyUserTypeCode;
	private Calendar entryDate;
	private Integer modifybyUserCode;
	private Integer modifyByUserTypeCode;
	private Calendar modifyDate;
	private String userSysIP;
}
