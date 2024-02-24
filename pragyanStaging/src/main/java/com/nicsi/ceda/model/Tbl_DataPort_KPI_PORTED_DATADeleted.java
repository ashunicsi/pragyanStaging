package com.nicsi.ceda.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

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
@Table(name="Tbl_DataPort_KPI_PORTED_DATA_deleted")
public class Tbl_DataPort_KPI_PORTED_DATADeleted 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	@Column(nullable = true)
	private Integer Instance_Id;
	@Column(nullable = true)
	private Integer sec_code;
	@Column(nullable = true)
	private Integer Ministry_Code;
	private Integer dept_code;
	private Long projectCode;
	@Column(nullable = true)
	private Integer Mode_frequency_id;
	@Column(nullable = true)
	private Integer Atmpt;
	//@Temporal(TemporalType.TIMESTAMP)
	//private Calendar Datadt;
	//from data
	private Timestamp Datadt;
	@Column(name="Level1_Code")
	private Integer level1Code;
	@Column(nullable = true, name="Level2_Code")
	private Integer level2Code;
	@Column(nullable = true, name="Level3_Code")
	private Integer level3Code;
	@Column(nullable = true, name="Level4_Code")
	private Integer level4Code;
	@Column(nullable = true, name="Level5_Code")
	private Integer level5Code;
	@Column(nullable = true, name="Level6_Code")
	private Integer level6Code;
	@Column(nullable = true, name="Level7_Code")
	private Integer level7Code;
	@Column(nullable = true, name="Level8_Code")
	private Integer level8Code;
	@Column(nullable = true, name="Level9_Code")
	private Integer level9Code;
	@Column(nullable = true, name="Level10_Code")
	private Integer level10Code;
	@Column(nullable = true)
	private Integer Kpi_id;
	@Column(precision = 18, scale = 5, nullable = true)
	//@Type(type = "big_decimal")
	private Double similarity;
	private Double Kpi_Data;
	@Column(nullable = true)
	private Integer Data_Group_Id;
	@Column(nullable = true)
	private Integer IsComplete;
	@Column(nullable = true)
	private Timestamp entrydt;
	
	@Column(nullable = true)
	private Integer Migrated;
	//private Date fromDurationOfData;
	//private Date toDurationOfData;
	private String username;
}
