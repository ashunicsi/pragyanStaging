package com.nicsi.ceda.model;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

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
@Table(name="KPI_PORTED_DATA_REPLICA")
public class KPI_PORTED_DATA_REPLICA 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	private int nationCode;
	@Column(nullable = true, name="Nation_Name", length =500)
	private String nationName;
	@Column(nullable = true, name="state_code")
	private int stateCode;
	@Column(nullable = true, name="State_Name", length = 4000)
	private String stateName;
	//@Column(nullable = true, name="State_NAME_Short", length = 5)
	//private String stateNameShort;
	@Column(nullable = true, name="dist_code")
	private int distCode;
	@Column(nullable = true, name="district_name", length = 4000)
	private String districtName;
	@Column(nullable = true, name="city_code")
	//GID-23; getLevel4Code and Name
	private int cityCode;
	@Column(nullable = true, name="city_name", length = 4000)
	private String cityName;
	@Column(nullable = true, name="village_code")
	//GID-23; getLevel5Code and Name
	private int villageCode;
	@Column(nullable = true, name="village_name", length = 4000)
	private String villageName;
	//level5
	@Column(nullable = true, name="panchayat_code")
	private int panchayatCode;
	@Column(nullable = true, name="panchayat_name", length = 4000)
	private String panchayatName;
	@Column(nullable = true, name="dept_code")
	private int deptCode;
	@Column(nullable = true, name="dep_name", length = 4000)
	private String depName;
	@Column(nullable = true, name="sec_code")
	private int secCode;
	@Column(nullable = true, name="sector_name", length = 4000)
	private String sectorName;
	@Column(nullable = true, name="new_project_code")
	private int newProjectCode;
	@Column(nullable = true, name="project_code")
	private int projectCode;
	@Column(nullable = true, name="project_name", length = 4000)
	private String projectName;
	
	@Column(nullable = true, name="Data_Date")
	private Date dataDate;
	@Column(nullable = true)
	private int yr;
	@Column(nullable = true)
	private int MMYYYY;
	@Column(nullable = true)
	private int month;
	@Column(name="month_name", length = 9)
	private String monthName;
	
	@Column(nullable = true, name="Mode_frequency_id")
	private int modeFrequencyId;
	@Column(nullable = true, name="data_frequency_name", length = 500)
	private String dataFrequencyName;
	@Column(nullable = true, name="KPI_ID")
	private int kpiId;
	@Column(nullable = true, length = 4000)
	private String indicator;
	@Column(nullable = true, name="Indicator_new", length = 4000)
	private String indicatorNew;//KPIName
	@Column(nullable = true, precision = 30, scale = 5)
	private Double value; //KPIValue
	private int label;
	@Column(nullable = true)
	private String unit;
	@Column(nullable = true, name="KPI_Category_Name_E", length = 200)
	private String kpiCategoryNameE;
	@Column(nullable = true, name="KPI_Type_ID")
	private int kpiTypeId;
	@Column(nullable = true, name="KPI_Percentage_ID")
	private int kpiPercentageId;
	@Column(nullable = true, name="Financial_Year", length = 15)
	private String financialYear;
	@Column(nullable = true, name="Financial_qrtr")
	private int financialQrtr;
	@Column(nullable = true, name="Financial_month_sort")
	private int financialMonthSort;
	@Column(nullable = true, name="description", length = 50)
	private String description;
	@Column(nullable = true, name="Is_cumulative", length = 2)
	private String isCumulative;
	@Column(nullable = true, name="TotalPopulation")
	private BigInteger  totalPopulation;
	@Column(nullable = true, name="darpanentrydt")
	private Timestamp darpanentrydt;
	@Column(nullable = true, name="LAST_UPDATED")
	private Timestamp lastUpdated;
	@Column(nullable = true, name="Ministry_ID")
	private int ministryId;
	@Column(nullable = true, name="Ministry_Name", length = 2000)
	private String ministryName;
	@Column(nullable = true, name="Is_Data_Reset")
	private Integer isDataReset;
	@Column(nullable = true, name="Reset_Frequency_ID")
	private int resetFrequencyId;
	@Column(nullable = true, name="Reset_Frequency_Name", length = 200)
	private String resetFrequencyName;
	@Column(nullable = true, name="KPI_Category_ID")
	private int kpiCategoryId;
	private String stateNameShort;
}
