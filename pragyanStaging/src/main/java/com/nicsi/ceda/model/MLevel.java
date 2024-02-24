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
@Table(name="m_level")
public class MLevel 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	private Integer Data_Level_ID;
	private Integer dataGranularityId;
	@Column(nullable = true)
	private Integer Level1Code;
	@Column(length = 100)
	private String Level1_name_e;
	@Column(length = 125 )
	private String Level1_name_r;
	@Column(nullable = true, name = "level2_Code",insertable = false, updatable = false)
	private Integer level2Code;
	@Column(length = 100, name = "Level2_name_e")
	private String level2NameE;
	@Column(length = 125, name = "Level2_name_r")
	private String level2NameR;
	@Column(nullable = true, name = "level3_Code")
	private Integer level3Code;
	@Column(length = 100, name = "level2_Code")
	private String level3NameE;
	@Column(length = 125, name = "Level3_name_r")
	private String level3NameR;
	@Column(nullable = true, name = "level4_Code")
	private Integer level4Code;
	@Column(length = 100, name = "Level4_name_e")
	private String level4NameE;
	@Column(length = 125, name = "Level4_name_r")
	private String level4NameR;
	@Column(nullable = true, name = "level5_Code")
	private Integer level5Code;
	@Column(length = 100, name = "Level5_name_e")
	private String level5NameE;
	@Column(length = 125, name = "Level5_name_r")
	private String level5NameR;
	@Column(nullable = true, name = "level6_Code")
	private Integer level6Code;
	@Column(length = 100, name = "Level6_name_e")
	private String level6NameE;
	@Column(length = 125, name = "Level6_name_r")
	private String level6NameR;
	@Column(nullable = true, name = "level7_Code")
	private Integer level7Code;
	@Column(length = 100, name = "Level7_name_e")
	private String level7NameE;
	@Column(length = 125, name = "Level7_name_r")
	private String level7NameR;
	@Column(nullable = true, name = "level8_Code")
	private Integer level8Code;
	@Column(length = 100, name = "Level8_name_e")
	private String level8NameE;
	@Column(length = 100, name = "Level8_name_r")
	private String level8NameR;
	@Column(nullable = true, name = "level9_Code")
	private Integer level9Code;
	@Column(length = 100, name = "Level9_name_e")
	private String level9NameE;
	@Column(length = 125, name = "Level9_name_r")
	private String level9NameR;
	@Column(nullable = true, name = "level10_Code")
	private Integer level10Code;
	@Column(length = 100, name = "Level10_name_e")
	private String level10NameE;
	@Column(length = 125, name = "Level10_name_r")
	private String level10NameR;
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
	private Boolean isLatest;
	@Column(nullable = true)
	private Integer Change_code;
	@Column(length = 100)
	private String Change_Remark;
}
