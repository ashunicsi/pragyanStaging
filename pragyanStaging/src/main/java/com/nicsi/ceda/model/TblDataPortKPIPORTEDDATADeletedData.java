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
@Table(name="TblDataPortKPIPORTEDDATADeletedData")
public class TblDataPortKPIPORTEDDATADeletedData 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	@Column(nullable = true)
	private Integer instanceId;
	@Column(nullable = true)
	private Integer secCode;
	@Column(nullable = true)
	private Integer ministryCode;
	private Integer deptCode;
	private Long projectCode;
	@Column(nullable = true)
	private Integer modeFrequencyId;
	@Column(nullable = true)
	private Integer atmpt;
	private Timestamp datadt;
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
	private Integer kpiId;
	@Column(precision = 18, scale = 5, nullable = true)
	private Double similarity;
	private Double kpiData;
	@Column(nullable = true)
	private Integer dataGroupId;
	@Column(nullable = true)
	private Integer isComplete;
	@Column(nullable = true)
	private Timestamp entrydt;
	@Column(nullable = true)
	private Integer migrated;
	private String username;
	Timestamp deletedDate;
}
