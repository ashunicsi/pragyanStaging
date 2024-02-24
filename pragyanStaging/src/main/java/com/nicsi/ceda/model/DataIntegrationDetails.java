package com.nicsi.ceda.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataIntegrationDetails 
{
	private int id;
	private int calendarType;
	private int pFrequency;
	private int portMode;
	private Date startDate;
	private int waitingDays;
	private String sendEmail;
	private String recpEmail;
}
