package com.nicsi.ceda.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="userActivities")
public class UserActivities 
{
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name="id", strategy = "increment")
	private int id;
	private String username;
	private String loginTime;
	private String ipAddress;
	private String macAddress;
	private String systemName;
	private String operationPerformend;
	private String action;
	public UserActivities() {
		super();
	}
	@Override
	public String toString() {
		return "UserActivities [id=" + id + ", username=" + username + ", loginTime=" + loginTime + ", ipAddress="
				+ ipAddress + ", macAddress=" + macAddress + ", systemName=" + systemName + ", operationPerformend="
				+ operationPerformend + ", action=" + action + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getOperationPerformend() {
		return operationPerformend;
	}
	public void setOperationPerformend(String operationPerformend) {
		this.operationPerformend = operationPerformend;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public UserActivities(int id, String username, String loginTime, String ipAddress, String macAddress,
			String systemName, String operationPerformend, String action) {
		super();
		this.id = id;
		this.username = username;
		this.loginTime = loginTime;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.systemName = systemName;
		this.operationPerformend = operationPerformend;
		this.action = action;
	}
	public UserActivities(String username, String loginTime, String ipAddress, String macAddress, String systemName,
			String operationPerformend, String action) {
		super();
		this.username = username;
		this.loginTime = loginTime;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.systemName = systemName;
		this.operationPerformend = operationPerformend;
		this.action = action;
	}
	
	
}
