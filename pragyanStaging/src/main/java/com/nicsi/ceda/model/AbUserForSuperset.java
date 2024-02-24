package com.nicsi.ceda.model;

import java.sql.Timestamp;

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
@Table(name="ab_user")
public class AbUserForSuperset 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="mId", strategy = "increment")
	private int id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private Boolean active;
	private String email;
	private Timestamp lastLogin;
	private int loginCount;
	private int failLoginCount;
	private Timestamp createdOn;
	private Timestamp changedOn;
	private int createdByFk;
	private int changedByFk;
}
