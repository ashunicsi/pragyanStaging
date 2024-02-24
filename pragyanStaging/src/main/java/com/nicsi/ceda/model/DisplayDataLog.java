package com.nicsi.ceda.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DisplayDataLog 
{
	
	private String fromDate;
	private String toDate;
	private String monthName;
	private String finYear;
	private String portingFrequencyName;
	private String granularityName;
	private String scheduledOn;
	private String portedOn;
	private String status;
	private int projectCode;
}
