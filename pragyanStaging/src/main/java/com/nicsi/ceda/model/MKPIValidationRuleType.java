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
@Table(name="m_kpi_validation_ruleType")
public class MKPIValidationRuleType 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private int ruleTypeId;
	private String ruleTypeName;
	private int sequenceNo;
	private int status;
	private String ruleTypeName_R;
}
