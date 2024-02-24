package com.nicsi.ceda.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
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
@Table(name="MLevelOld")
public class MLevelOld 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	private Integer Data_Level_ID;
	private Integer dataGranularityID;
	@Column(nullable = true)
	private Integer Level1_code;
	@Column(length = 100)
	private String Level1_name_e;
	@Column(length = 125 )
	private String Level1_name_r;
	@Column(nullable = true, name = "level2_Code")
	private Integer level2_code;
	@Column(length = 100)
	private String Level2_name_e;
	@Column(length = 125)
	private String Level2_name_r;
	@Column(nullable = true)
	private Integer Level3_Code;
	@Column(length = 100)
	private String Level3_name_e;
	@Column(length = 125)
	private String Level3_name_r;
	@Column(nullable = true)
	private Integer Level4_code;
	@Column(length = 100)
	private String Level4_name_e;
	@Column(length = 125)
	private String Level4_name_r;
	@Column(nullable = true)
	private Integer Level5_code;
	@Column(length = 100)
	private String Level5_name_e;
	@Column(length = 125)
	private String Level5_name_r;
	@Column(nullable = true)
	private Integer Level6_code;
	@Column(length = 100)
	private String Level6_name_e;
	@Column(length = 125)
	private String Level6_name_r;
	@Column(nullable = true)
	private Integer Level7_code;
	@Column(length = 100)
	private String Level7_name_e;
	@Column(length = 125)
	private String Level7_name_r;
	@Column(nullable = true)
	private Integer Level8_code;
	@Column(length = 100)
	private String Level8_name_e;
	private String Level8_name_r;
	@Column(nullable = true)
	private Integer Level9_code;
	@Column(length = 100)
	private String Level9_name_e;
	@Column(length = 125)
	private String Level9_name_r;
	@Column(nullable = true)
	private Integer Level10_code;
	@Column(length = 100)
	private String Level10_name_e;
	@Column(length = 125)
	private String Level10_name_r;
	@Column(nullable = true)
	private Integer Is_Previous_Level;
	@Column(nullable = true)
	private Integer Instance_Code;
	@Column(nullable = true)
	private Integer Instance_Typecode_main;
	@Column(nullable = true)
	private Integer Instance_Typecode_Sub;
	@Column(nullable = true)
	private Integer Modifiy_Permission;
	@Column(nullable = true)
	private Integer Delete_Permission;
	@Column(nullable = true)
	private Integer Status;
	@Column(nullable = true)
	private Integer Entry_by_User_Code;
	@Column(nullable = true)
	private Integer Entry_by_User_Type_Code;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar Entry_Date;
	@Column(nullable = true)
	private Integer Modify_by_User_Code;
	@Column(nullable = true)
	private Integer Modify_By_User_Type_Code;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Calendar Modify_Date;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar Affected_DT_From;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar Affected_DT_To;
	@Type(type = "numeric_boolean")
	private Boolean Islatest;
	@Column(nullable = true)
	private Integer ChangeCode;
	@Column(length = 100)
	private String Change_Remark;
}
