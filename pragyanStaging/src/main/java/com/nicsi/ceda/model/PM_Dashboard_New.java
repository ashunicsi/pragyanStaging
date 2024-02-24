package com.nicsi.ceda.model;

import java.math.BigInteger;
import java.sql.Date;
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
@Table(name="PM_Dashboard_New")
public class PM_Dashboard_New 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	@Column(nullable = true, name="state_code")
	private int  state_code;
	private int  dept_code;
	private int  project_code;
	@Column(nullable = true, name="dist_code")
	private int  dist_code;
	@Column(nullable = true, name="sec_code")
	private int  sec_code;
	private int  yr;
	@Column(name="Fiscal_Year", length = 15)
	private String  Fiscal_Year;
	@Column(nullable = true, name="month")
	private int  month;
	@Column(name="month_name", length = 15)
	private String  month_name;
	private int  month_sort;
	private Date date;
	@Column(precision = 30, scale = 5)
	private Double  Value;
	private int  label;
	@Column(name="indicator", length = 4000)
	private String  indicator;
	@Column(name="unit", length = 40)
	private String  unit;
	@Column(name="description", length = 50)
	private String  description;
	@Column(name="State_Name", length = 4000)
	private String  State_Name;
	@Column(name="project_name", length = 4000)
	private String  project_name;
	@Column(name="district_name", length = 4000)
	private String  district_name;
	@Column(name="sector_name", length = 4000)
	private String  sector_name;
	@Column(name="dep_name", length = 4000)
	private String  dep_name;
	@Column(name="Indicator_new", length = 400)
	private String  Indicator_new;
	@Column(name="ST_NAME", length = 5)
	private String  ST_NAME;
	private int  Fiscal_month;
	private int  data_entity;
	private BigInteger  TotalPopulation;
	private Timestamp  LAST_UPDATED;
	@Column(name="Data_Type_Name", length = 20)
	private String  Data_Type_Name;
	private String  Aspirational_District;
	private int  Ministry_id;
	@Column(name="Ministry_Name", length = 4000)
	private String  Ministry_Name;
	private int  Reset_Flag;
	@Column(name="Reset_Frequency", length = 50)
	private String  Reset_Frequency;
	@Column(name="Actual_Flag", length = 20)
	private String  Actual_Flag;
	private int  New_Project_Code;
	@Column(name="Old_State_Code")
	private int  Old_State_Code;
	@Column(precision = 30, scale = 5)
	private Double  Current_Value;
	@Column(precision = 30, scale = 5)
	private Double  Cumulative_Value;
	private int  city_code;
	@Column(name="city_name", length = 4000)
	private String  city_name;
	private int  village_code;
	@Column(name="village_name", length = 4000)
	private String  village_name;
	private int  country_code;
	@Column(name="Country_name", length = 4000)
	private String  Country_name;
	@Column(name="granularity_id")
	private int  granularityId;
}
