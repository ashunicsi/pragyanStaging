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
@Table(name="m_list_of_organisation")
public class ListOfOrganisation 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="mId", strategy = "increment")
	private int oId;
	private String organisationName;
	private String organisationCode;
	private String language;
	private int status;
	
	
}
