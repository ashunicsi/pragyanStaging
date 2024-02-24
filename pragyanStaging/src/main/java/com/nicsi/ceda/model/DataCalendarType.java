package com.nicsi.ceda.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sun.istack.Nullable;

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
@Table(name="mDataCalendarType")
public class DataCalendarType 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private int data_Calendar_Type_Id;	
	private String dataCalendarTypeName;
	private int status;
	private String dataCalendarTypeNameR;
}
