package com.nicsi.ceda.model;

import java.sql.Date;
import java.util.Calendar;

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
@Table(name="Dim_Time")
public class Dim_Time 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private Long id;
	@Column(name = "ddate")
	private Date ddate;
	@Column(name = "dday")
	private Integer dDay;
	@Column(name = "wkday", length = 20)
	private String wkDay;
	@Column(name = "weekdayshort", length = 5)
	private String weekDayShort;
	@Column(name = "weekno")
	private Integer weekNo;
	@Column(name = "monthnumber")
	private Integer monthNumber;
	@Column(name = "mthname", length = 20)
	private String mthName;
	@Column(name = "monthnameshort", length = 5)
	private String monthNameShort;
	@Column(name = "mmyyyy", length = 8)
	private String MMYYYY;
	@Column(name = "ddmmyyyy", length = 10)
	private String DDMMYYYY;
	@Column(name = "qrter")
	private Integer qrter;
	@Column(name = "yr")
	private Integer yr;
	@Column(name = "firstdateofweek")
	private Date firstDateOfWeek;
	@Column(name = "lastdateofweek")
	private Date lastDateOfWeek;
	@Column(name = "firstdateofmonth")
	private Date firstDateOfMonth;
	@Column(name = "lastdateofmonth")
	private Date lastDateOfMonth;
	@Column(name = "finyear")
	private Integer finYear;
	@Column(name = "finyearfull", length = 50)
	private String finYearFull;
	@Column(name = "finquarter")
	private Integer finquarter;
	@Column(name = "finmonthnumbersort")
	private Integer finMonthNumberSort;
}
