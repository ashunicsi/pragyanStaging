package com.nicsi.ceda.model;

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
@Table(name="codesOfStates")
public class OldCodesOfStates 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private int id;
	@Column(unique=true)
	private int StateLGDCode;
	private String StateNameInEnglish;
	private String StateNameInHindi;
	private String StateNameInLocalLanguage;
	private String StateOrUT;
}
