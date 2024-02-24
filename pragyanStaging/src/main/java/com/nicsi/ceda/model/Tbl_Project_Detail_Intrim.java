package com.nicsi.ceda.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
@Table(name="Tbl_Project_Detail_Intrim")
public class Tbl_Project_Detail_Intrim 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private Long id;
	private Integer Instance_Code;
	private Integer Instance_TypeCode_Sub;
	@Column(nullable = true)
	private Integer Instance_TypeCode_main;
	@Column(nullable = true)
	private Integer Instance_Lvl_Code;
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
	@Column(nullable = true)
	private Integer Level6_Code;
	@Column(nullable = true)
	private Integer Level7_Code;
	@Column(nullable = true)
	private Integer Level8_Code;
	@Column(nullable = true)
	private Integer Level9_Code;
	@Column(nullable = true)
	private Integer Level10_Code;
	private Integer sec_code;
	private Integer ministry_code;
	private Integer Dept_code;
	@Column(name = "Project_Code")
	private Integer projectCode;
	@Column(length = 100)
	private String Project_name_e;
	@Column(length = 150)
	private String Project_Name_R;
	@Column(length = 100)
	private String project_cord_name_e;
	@Column(length = 100)
	private String project_cord_email;
	@Column(length = 20)
	private String project_Cord_MobNo;
	@Column(length = 100)
	private String project_head_name_e;
	@Column(length = 100)
	private String project_head_email;
	@Column(length = 20)
	private String Project_Head_MobNo;
	Date Project_Launch_Date;
	@Column(nullable = true)
	private Integer Project_Data_Granularity_ID;
	@Column(nullable = true)
	private Integer Project_Data_Lvl_Code;
	@Column(length = 500)
	private String Project_Tooltip_E ;
	@Column(length = 550)
	private String Project_Tooltip_R;
	@Column(length = 1000)
	private String Project_Description_E;
	@Column(length = 1100)
	private String Project_Description_R;
	@Column(nullable = true)
	private Integer Project_Category_Id;
	@Column(nullable = true)
	private Integer DB_Server_Code;
	@Column(length = 100)
	private String DB_Server_Name;
	@Column(nullable = true)
	private Integer Modifiy_Permission;
	@Column(nullable = true)
	private Integer Delete_Permission;
	@Column(nullable = true)
	private Integer status;
	@Column(nullable = true)
	private Integer Lock_Status;
	@Column(nullable = true)
	private Integer Project_Approval_Status;
	@Column(nullable = true)
	
	private Timestamp Project_Approved_Date;
	@Column(nullable = true)
	private Integer Entry_by_User_Code;
	@Column(nullable = true)
	private Integer Entry_by_User_Type_Code;
	
	private Timestamp Entry_Date;
	@Column(nullable = true)
	private Integer Modify_by_User_Code;
	@Column(nullable = true)
	private Integer Modify_By_User_Type_Code;
	
	private Timestamp Modify_Date;
	@Column(length = 50)
	private String User_Sys_IP;
	@Column(nullable = true)
	private Integer Project_Master_Category_Id;
	@Column(length = 100)
	private String Project_Master_Category_Name;
	@Column(nullable = true)
	private Integer Major_Minor_Type;
	@Column(nullable = true)
	private Long Major_Proj_Code;
	private String project_Admin_Email;
	private String project_Admin_Name;
	private String project_Admin_MobNo;
	
	private String projectHeadDesignation;
	private String projectAdminDesignation;
	private String projectCordDesignation;
	
	@Column(nullable = true)
	private int calendarType;
	@Column(nullable = true)
	private int portingFrequency;
	@Column(nullable = true)
	private int portMode;
	@Column(nullable = true)
	private int waitingDays;
	@Column(nullable = true)
	private String sendEmail;
	private Date startDate;
	private String recpEmail;
	private String schemeLogoName;
	private String username;
	private Timestamp lastUpdateDate;
	private int isRejected;
	private Timestamp projectRejectionDate;
	private String rejectMessage;
	private String tokenKey;
	@Column(nullable = true)
	private Integer portLanguageId;
	/*
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name="ProjectDetailId")
	private List<Tbl_Project_KPI_Detail_Intrim> group = new ArrayList<Tbl_Project_KPI_Detail_Intrim>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Project_Detail_Intrim_Id")
	private Tbl_DataPort_KPI_PORTED_DATA kpiPortedData;
*/
}
