package com.nicsi.ceda.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name="M_Sector")
public class Sector 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private int id;
	private int sectorId;
	@Column(length = 100)
	private String Sector_Name_e_actual;
	@Column(length = 125)
	private String Sector_Name_r_actual;
	@Column(length = 100)
	private String Sector_Name_e;
	@Column(length = 125)
	private String Sector_Name_r;
	@Column(nullable = true)
	private Integer Sector_Type;
	@Column(nullable = true)
	private Integer Sector_Wrapper_Id;
	@Column(nullable = true)
	private Integer Modifiy_Permission;
	@Column(nullable = true)
	private Integer Delete_Permission;
	@Column(nullable = true)
	private Integer status;
	@Column(nullable = true)
	private Integer Entry_by_User_Code;
	@Column(nullable = true)
	private Integer Entry_by_User_Type_Code;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar Entry_Date ;
	@Column(nullable = true)
	private Integer Modify_by_User_Code;
	@Column(nullable = true)
	private Integer Modify_By_User_Type_Code;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar Modify_Date;
	@Column(length = 15)
	private String Sector_Abbreviation_e;
	@Column(length = 20)
	private String Sector_Abbreviation_r;
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] Icon_Data;
	@Column(length = 60)
	private String Icon_Name;
	@Column(length = 40)
	private String Icon_Mime_Type;
}
