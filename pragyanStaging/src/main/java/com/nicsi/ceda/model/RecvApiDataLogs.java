package com.nicsi.ceda.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
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
@Table(name="recvApiDataLogs")
public class RecvApiDataLogs 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private int id;
	private int Instance_Code;
	private int Instance_TypeCode_main;
	private int Instance_TypeCode_Sub;
	private int Instance_Lvl_Code;
	@Column(nullable = true)
	private Integer Level1_Code;
	@Column(nullable = true)
	private Integer Level2_Code;
	@Column(nullable = true)
	private Integer Level3_Code;
	@Column(nullable = true)
	private Integer Level4_Code;
	@Column(nullable = true)
	private Integer Level5_Code;
	private int Level6_Code;
	private int Level7_Code;
	private int Level8_Code;
	private int Level9_Code;
	private int Level10_Code;
	private int Sec_Code;
	private int Ministry_Code;
	private int Dept_Code;
	private int projectCode;
	private Date Data_Port_Start_Date;
	private int Data_Port_Description_Id;
	private String Data_Port_Description_Name;
	private String Data_Port_Description_Detail;
	private Date Data_From_Date;
	private Time Data_From_Time;
	private Date Data_To_Date;
	private Time Data_To_Time;
	private Date Data_Port_Schedule_Date;
	private Time Data_Port_Schedule_Time;
	private Timestamp Data_Ported_DateTime;
	private int Status;
	private int footPrint_opted;
	private int footprint_given;
	private int Data_Group_ID;
	private int DataStatus;
	private int Entry_by_User_Code;
	private int Entry_by_User_Type_Code;
	private Date entryDate;
	private int Modify_by_User_Code;
	private int Modify_By_User_Type_Code;
	private Date Modify_Date;
	private String Legacy_Data;
	private int kpiId;
	@Column(nullable = true)
	private Double kpiData;
	
	
	
}
