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
public class ApproveUserDTO 
{
	 private String name;
	 private String sectorName;
	 private String ministryName;
	 private String departmentName;
	 private String createdDate;
	 private String rejectedDate;
}
