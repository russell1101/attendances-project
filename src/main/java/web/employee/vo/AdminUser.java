package web.employee.vo;

import java.sql.Timestamp;

public class AdminUser {
	private Integer adminUserId;
    private String username;
    private String passwordHash;
    private String displayName;
    private Integer employeeId;
    private Boolean isActive; // 也可以用 Boolean，對應 TINYINT(1)
    private Timestamp createdAt;
    private Timestamp updatedAt;
	public Integer getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(Integer adminUserId) {
		this.adminUserId = adminUserId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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
