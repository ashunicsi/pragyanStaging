package com.nicsi.ceda.model;


import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(name="dataPushRequest")
public class DataPushRequest 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private int id;
	private int projectCode;
	private int noOfAttempts;
	private String username;
	private Timestamp requestTime;
	private Timestamp actionTime;
	private String dataCleanBy;
	private int status;
	private String actionTakenBy;
	private int isRejected;
	private String rejectedMessage;
	private Date pushedDate;
	private String isPushCompleted;
	
	
}
