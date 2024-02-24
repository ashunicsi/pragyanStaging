package com.nicsi.ceda.model;

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
@Table(name="dataCleanRequest")
public class DataCleanRequest 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="mId", strategy = "increment")
	private int id;
	private String username;
	private int projectCode;
	private Timestamp requestTime;
	private Timestamp actionTime;
	private String actionTakenBy;
	private int status;
	private int isRejected;
	private String rejectedMessage;
	
	
}
