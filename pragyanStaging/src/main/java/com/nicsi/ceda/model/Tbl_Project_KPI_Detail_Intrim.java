package com.nicsi.ceda.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(name="tbl_project_kpi_detail_intrim")
public class Tbl_Project_KPI_Detail_Intrim 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private Integer id;
	private Integer instance_code;
	private Integer instance_TypeCode_main;
	@Column(nullable = true)
	private Integer instance_TypeCode_Sub;
	@Column(nullable = true)
	private Integer instance_Lvl_code;
	@Column(nullable = true)
	private Integer level1_code;
	@Column(nullable = true)
	private Integer level2_code;
	@Column(nullable = true)
	private Integer level3_code;
	@Column(nullable = true)
	private Integer level4_code;
	@Column(nullable = true)
	private Integer level5_code;
	@Column(nullable = true)
	private Integer level6_code;
	@Column(nullable = true)
	private Integer level7_code;
	@Column(nullable = true)
	private Integer level8_code;
	@Column(nullable = true)
	private Integer level9_code;
	@Column(nullable = true)
	private Integer level10_code;
	@Column(nullable = true)
	private Integer sec_code;
	@Column(nullable = true)
	private Integer ministry_code;
	@Column(nullable = true)
	private Integer dept_code;
	@Column(nullable = true)
	private Integer projectCode;
	@Column(nullable = true, name="kpi_id")
	private Integer kpiId;
	@Column(nullable = true)
	private Integer kpi_id_actual;
	@Column(length = 1000)
	private String kpi_name_e;
	@Column(length = 1000)
	private String kpi_name_r;
	@Column(nullable = true)
	private Integer kpi_Unit_id;
	@Column(length = 100)
	private String kpi_unit_name_e;
	@Column(length = 100)
	private String kpi_unit_name_r;


	@Column(length = 300, name="kpi_tooltip_e")
	private String kpiTooltipE;
	@Column(length = 300, name="kpi_tooltip_r")
	private String kpiTooltipR;
	@Column(nullable = true)
	private Integer kpi_category_id;
	private String kpi_category_name_e;
	private String kpi_category_name_r;
	@Column(nullable = true)
	private Integer kpi_percentage_id;
	@Column(nullable = true)
	private Integer kpi_percentage_id_actual;
	@Column(length = 100)
	private String kpi_percentage_name;
	@Column(nullable = true)
	private Integer kpi_data_type_id;
	@Column(length = 100)
	private String kpi_data_type_Name;
	@Column(nullable = true)
	private Integer kpi_data_display_type_id;
	@Column(length = 100)
	private String kpi_data_display_type_name;
	@Column(nullable = true)
	private Integer is_datareset;
	@Column(nullable = true)
	private Integer data_reset_frequency_id;
	@Column(length = 100)
	private String data_reset_frequency_name;
	@Column(nullable = true)
	private Integer is_data_aggregate;
	@Column(precision = 18, scale = 5, nullable = true)
	private double kpi_data_min_limit;
	@Column(precision = 18, scale = 5, nullable = true)
	//@Type(type = "big_decimal")
	private double kpi_data_max_limit;
	@Column(nullable = true)
	Character  kpi_type_status;
	@Column(nullable = true)
	private Integer is_updated;
	@Column(nullable = true)
	private Integer modifiy_permission;
	@Column(nullable = true)
	private Integer delete_permission;
	@Column(nullable = true)
	private Integer status;
	@Column(nullable = true)
	private Integer lock_status;
	@Column(nullable = true)
	private Integer project_approval_status;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar project_approved_date;
	@Column(nullable = true)
	private Integer entry_by_user_code;
	@Column(nullable = true)
	private Integer entry_by_user_type_code;
	private Timestamp entry_date;
	@Column(nullable = true)
	private Integer modify_by_user_code;
	@Column(nullable = true)
	private Integer modify_by_user_type_code;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar modify_date;
	@Column(length = 50)
	private String user_sys_ip;
	@Column(length = 5)
	private String allowNull;
	@Column(name="rule_type_id")
	private int ruleTypeId;
	@Column(name="operator_id")
	private int operatorId;
	private String username;
	private Timestamp lastUpdate;
	
	
}
