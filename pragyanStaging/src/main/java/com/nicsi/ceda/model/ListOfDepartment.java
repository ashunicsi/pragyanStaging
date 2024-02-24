package com.nicsi.ceda.model;

import java.util.List;

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
@Table(name="m_list_of_department")
public class ListOfDepartment 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="dId", strategy = "increment")
	private int dId;
	private String departmentName;
	private String departmentCode;
	private String language;
	private int status;
	
}
