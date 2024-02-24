package com.nicsi.ceda.model;

import java.sql.Timestamp;

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
public class SchemeDetailsDTO 
{
	private int id;
	private String ministryName;
	private int ministryCode;
	private String departmentName;
	private int departmentCode;
	private String schemeName;
	private int schemeCode;
	private String cordEmail;
	private String headEmail;
	private String adminEmail;
	private String rejectMessage;
	private Timestamp projectRejectionDate;
	private int Project_Approval_Status;
	private Timestamp entryDate;
	private int isRejected;
	private int sectorCode;
	private String sectorName;
	
}
