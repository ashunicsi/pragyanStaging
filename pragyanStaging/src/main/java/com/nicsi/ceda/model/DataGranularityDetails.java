package com.nicsi.ceda.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.sun.istack.Nullable;

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
@Table(name="mDataGranularity")
public class DataGranularityDetails 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private int id;
	@Column(unique=true)
	int dataGranularityId;
	@Column(length = 250)
	private String Data_Granularity_Name_e;
	@Column(length = 275)
	private String Data_Granularity_Name_r;
	@Column(nullable = true)
	private int Max_Data_Level;
	@Column(nullable = true)
	private int Level1_Keyword_Id;
	@Column(nullable = true)
	private int Level2_Keyword_Id;
	@Column(nullable = true)
	private int Level3_Keyword_Id;
	@Column(nullable = true)
	private int Level4_Keyword_Id;
	@Column(nullable = true)
	private int Level5_Keyword_Id;
	@Column(nullable = true)
	private int Level6_Keyword_Id;
	@Column(nullable = true)
	private int Level7_Keyword_Id;
	@Column(nullable = true)
	private int Level8_Keyword_Id;
	@Column(nullable = true)
	private int Level9_Keyword_Id;
	@Column(nullable = true)
	private int Level10_Keyword_Id;
	@Column(nullable = true)
	private int isFixed;
	@Column(nullable = true)
	private int IS_Usercreation_Master;
	@Column(nullable = true)
	private int Modifiy_Permission;
	@Column(nullable = true)
	private int Delete_Permission;
	@Column(nullable = true)
	private int status;
	@Column(nullable = true)
	private int Entry_by_User_Code;
	@Column(nullable = true)
	private int Entry_by_User_Type_Code;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar Entry_Date;
	@Column(nullable = true)
	private Integer Modify_by_User_Code;
	@Column(nullable = true)
	@Nullable
	private Integer Modify_By_User_Type_Code;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar Modify_Date;
	
	
}
