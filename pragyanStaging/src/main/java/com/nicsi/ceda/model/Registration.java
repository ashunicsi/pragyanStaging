package com.nicsi.ceda.model;

import java.sql.Date;

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
@Table(name="m_registration")
public class Registration 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private int id;
	private String name;
	private String email;
	private String mobileNumber;
	private String phoneNumber;
	private String designation;
	private int sectorCode;
	@Column(nullable = true)
	private int ministryCode;
	@Column(nullable = true)
	private int departmentCode;
	private int organasationCode;
	private String stateCode;
	private String districtCode;
	private Date entDt;
	private Date updtDt;
	private int status;
	private String feedback;
	private String language;
	private String registrationTime;
	private String tokenKey;
	private String encryptionKey;
	@Column(nullable = true)
	private Integer projectCode;
	
	
}
