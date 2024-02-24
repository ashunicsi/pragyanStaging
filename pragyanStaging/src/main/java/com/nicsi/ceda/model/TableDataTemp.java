package com.nicsi.ceda.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table
public class TableDataTemp 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String instanceCode ; 
	private String sectorCode;
	private String ministryCode;
	private String departmentCode;
	private String projectCode;
	private String frequencyId;
	private String groupId;
	private String atmpt;
	private String dataDate;
	private String fromDurationOfData;
	private String toDurationOfData;
	private String status;
	private String errorCode ;
	private String kpiId;
	private String kpiValue;
	private String token;
	private String level1Code;
	private String level2Code;
	private String level3Code;
	private String level4Code;
	private String level5Code;
	private String level6Code;
	private String level7Code;
	private String level8Code;
	private String level9Code;
	private String level10Code;
	@Column(name="api_id")
	private int apiLanguageId;
	@Column(nullable = true)
	private Timestamp entryDate;
}
