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
@Table(name="m_KPI_TYPE")
public class KPI_TYPE 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="kpi_type_id", strategy = "increment")
	@Column(name="kpi_type_id")
	private int id;
	private String kpi_type_e;
	private String kpi_type_r;
	private int status;
	
}
