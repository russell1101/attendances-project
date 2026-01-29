package core.pojo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Employee {
	private Long employeeId;
	private String name;
	private String email;
	private String passwordHash;
	private String googleSub;
	private Date hireDate;
	private BigDecimal currentPoints;
	private Long departmentId;
	private Long employeeStatusId;
	private Boolean isActive;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getGoogleSub() {
		return googleSub;
	}

	public void setGoogleSub(String googleSub) {
		this.googleSub = googleSub;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public BigDecimal getCurrentPoints() {
		return currentPoints;
	}

	public void setCurrentPoints(BigDecimal currentPoints) {
		this.currentPoints = currentPoints;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getEmployeeStatusId() {
		return employeeStatusId;
	}

	public void setEmployeeStatusId(Long employeeStatusId) {
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
