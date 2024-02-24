package com.nicsi.ceda.model;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.web.multipart.MultipartFile;

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
public class TempAddScheme 
{
	private int sectorCode;
	private int ministryCode;
	private int deptCode;
	private int schemeType;
	private String schemeName;
	private String   projectCordNameE;
	private String	projectCordEmail;
	private String	projectCordMobNo;
	private String	projectHeadNameE;
	private String	projectHeadEmail;
	private String	projectHeadMobNo;
	private String	projectAdminName;
	private String	projectAdminPhone;
	private String	projectAdminEmail;
	private String	projectTooltipE;
	private String	projectDescriptionE;
	private int 	projectDataGranularityID;
	private Date	projectLaunchDate;
	private int 	projectCategoryId;
	
	private int	portingFrequency;
	private int	portMode;
	private java.util.Date startDate;
	private int calendarType;
	private int waitingDays;
	private String sendEmail;
	private String recpEmail;
	
	private String projectHeadDesignation;
	private String projectAdminDesignation;
	private String projectCordDesignation;
	private MultipartFile schemeLogo;
	
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="ProjectDetailId")
	private List<Tbl_Project_KPI_Detail_Intrim> group = new ArrayList<Tbl_Project_KPI_Detail_Intrim>();
}
