package com.nicsi.ceda.model;

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
@Table(name="schemeDetails")
public class SchemeDetails 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private Integer id;
	private Integer schemeId;
	private Integer sectorId;
	private Integer ministryId;
	private Integer departmentId;
	private String schemeType;
	private String schemeName;
	private String coordinatorName;
	private String cordinatorPhone;
	private String cordinatorEmail;
	private String hodName;
	private String hodPhone;
	private String hodEmail;
	private String projectAdminName;
	private String projectAdminPhone;
	private String projectAdminEmail;
	private String tooltip;
	private String description;
	private Integer dataGranularityId;
	private String launchDate;
	private String category;
	private String language;
	
	
	
}
