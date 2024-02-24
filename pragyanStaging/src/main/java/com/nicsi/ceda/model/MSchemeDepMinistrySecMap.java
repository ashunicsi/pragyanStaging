package com.nicsi.ceda.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name="m_Scheme_Dep_ministry_Sec_map")
public class MSchemeDepMinistrySecMap 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	private String schemeName; 
	private int projectCode;
	private int newSecCode;
	private String newSecName;
	private int newMinistryId;
	private String newMinistryName;
	private int newDepCode;
	private String newDepName;
	@Column(nullable = true)
	private int sectorId;
	private String sectorName;
	private int ministryId;
	private String ministryName;
}
