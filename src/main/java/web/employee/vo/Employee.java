package web.employee.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class Employee {
	private Integer employeeId;
	private String name;
	private String account;
	private String passwordHash;
	private Date hireDate;
	private Integer currentPoints;
	private Integer departmentId;
	private Integer employeeStatusId;
	private Boolean isActive;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Integer getCurrentPoints() {
		return currentPoints;
	}

	public void setCurrentPoints(Integer currentPoints) {
		this.currentPoints = currentPoints;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getEmployeeStatusId() {
		return employeeStatusId;
	}

	public void setEmployeeStatusId(Integer employeeStatusId) {
		this.employeeStatusId = employeeStatusId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
