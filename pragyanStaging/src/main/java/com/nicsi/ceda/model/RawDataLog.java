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
public class RawDataLog 
{
	private String sectorName;
	private int sectorCode;
	private String ministryName; 
	private int ministryCode; 
	private String departmentName; 
	private int departmentCode; 
	private String stateName; 
	private int stateCode; 
	private String districtName; 
	private int districCode; 
	private String blockName;
	private int blockCode; 
	private String villageName; 
	private int villageCode; 
	private Double kpiValue; 
	private String dataDate;
}
