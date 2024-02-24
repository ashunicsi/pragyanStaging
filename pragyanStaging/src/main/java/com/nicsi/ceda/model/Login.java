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
@Table(name="login")
public class Login 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="mId", strategy = "increment")
	private int id;
	private String name;
	private String emailId;
	private String password;
	private String createdDate;
	private String updatedDate;
	private int flag;
	private int noOfAttempts;
	private String isLocked;
	private String language;
	private String userType;
	private String displayName;
	 // userPermissio, 0 = public user, 1 = viewer, 2 = viewer and update, 3 = full access
	@Column(name="user_permission")
	private int userPermission;
	@Column(name="status")
	private int status;
	@Column(name="isSuperAdmin")
	private int isSuperAdmin;
	private String tokenKey;
	@Column(name="user_role_id")
	private int userRoleId;
	private String createdBy;
	public String username;
	private String updatedBy;
	@Column(name="isApproved", nullable = true)
	private Integer isApproved;
	
}
