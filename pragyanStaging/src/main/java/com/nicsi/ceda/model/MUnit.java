package com.nicsi.ceda.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name="m_unit")
public class MUnit 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	@Column(nullable = true)
	private Integer unitId;
	@Column(nullable = true)
	private String unit_name_e_actual;
	private String unit_name_r_actual;
	private String unit_name_e;
	private String unit_name_r;
	@Column(precision = 18, scale = 5, nullable = true)
    private Double multiply_factor;
	@Column(nullable = true)
    private int sequence_no;
	@Column(nullable = true)
    private int modifiy_permission;
	@Column(nullable = true)
    private int delete_permission;
	@Column(nullable = true)
    private int status;
	@Column(nullable = true)
    private int entry_by_user_code;
	@Column(nullable = true)
    private int entry_by_user_type_code;
    private Date entry_date;
	@Column(nullable = true)
    private int modify_by_user_code;
	@Column(nullable = true)
    private int modify_by_user_type_code;
    private Date modify_date;
	
}
