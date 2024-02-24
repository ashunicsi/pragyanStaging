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
@Table(name="ListOfSchemeForReport")
public class ListOfSchemeForReport
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private int id;
	private int ministryCode;
	private String ministryName;
	private int departmentCode;
	private String departmentName;
	private String schemeName;
    private int schemeCode;
    private String cordEmail;
    private String headEmail;
    private String adminEmail;
    private int status;
}
