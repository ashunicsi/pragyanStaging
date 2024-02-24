package com.nicsi.ceda.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
@Entity
@Table(name="RecvApiDataLogsToDisplay")
public class RecvApiDataLogsToDisplay 
{

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="mId", strategy = "increment")
	private int id;
	private String prevMonthFirstDate;
	private String prevMonthLastDate;
	private String monthName;
	private String finYear;
	private String portingFrequencyName;
	private String granularityName;
	private String scheduledOn;
	private String portedOn;
	private String status;
	private int projectCode;
	
	
}
