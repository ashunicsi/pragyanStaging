package com.nicsi.ceda.model;

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
public class KPIDetailsDTO 
{
	private int id;
	private int schemeCode;
	private int unitId;
	private int categoryId;
	private int dataValueId;
	private String initialization;
	private String kpiName;
	private int kpiId;
	private String tooltip;
	private String allowNull;
	private int ruleTypeId;
	private int operatorId;
	private int projectNumber;
	private int ministryCode;
	private int deptCode;
	private String ministryName;
	private String deptName;
	private String schemeName;
	private int sectorCode;
	private String kpi_data_display_type_name;
	private String username;
	
	
}
