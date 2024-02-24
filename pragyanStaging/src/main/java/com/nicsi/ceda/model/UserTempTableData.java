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
@Table(name="userTempTableData")
public class UserTempTableData 
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="Instance_Code")
	private int instanceCode ; 
	@Column(name="Sec_Code")
	private int sectorCode;
	@Column(name="Ministry_Code")
	private int ministryCode;
	@Column(name="Dept_Code")
	private int departmentCode;
	@Column(name="Project_Code")
	private int projectCode;
	@Column(name="Frequency_Id")
	private int frequencyId;
	@Column(name="Group_Id")
	private int groupId;
	private int atmpt;
	private Timestamp dataDate;
	private Timestamp fromDurationOfData;
	private Timestamp toDurationOfData;
	
	@Column(nullable = true, name = "Lvl1_Code")
	private Integer level1Code;
	@Column(nullable = true, name = "Lvl2_Code")
	private Integer level2Code;
	@Column(nullable = true, name = "Lvl3_Code")
	private Integer level3Code;
	@Column(nullable = true, name = "Lvl4_Code")
	private Integer level4Code;
	@Column(nullable = true, name = "Lvl5_Code")
	private Integer level5Code;
	@Column(nullable = true, name = "Lvl6_Code")
	private Integer level6Code;
	@Column(nullable = true, name = "Lvl7_Code")
	private Integer level7Code;
	@Column(nullable = true, name = "Lvl8_Code")
	private Integer level8Code;
	@Column(nullable = true, name = "Lvl9_Code")
	private Integer level9Code;
	@Column(nullable = true, name = "Lvl10_Code")
	private Integer level10Code;
	
	@Column(nullable = true, name = "kpi_Id")
	private int kpiId;
	@Column(nullable = true, name = "kpi_value")
	private Double kpiValue;
	
}
