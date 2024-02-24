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
public class SchemeDataLogDTO 
{
	private String prevMonthLastDate;
	private String prevMonthFirstDate;
	private String monthName;
	private String finYear;
	private String portingFrequencyName;
	private String granularityName;
	private String scheduledOn;
	private String portedOn;
	private String status;
}
