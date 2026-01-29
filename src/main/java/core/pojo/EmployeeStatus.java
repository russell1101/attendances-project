package core.pojo;

import java.sql.Timestamp;

public class EmployeeStatus {
	private Long employeeStatusId;
	private String statusName;
	private Timestamp createdAt;

	public Long getEmployeeStatusId() {
		return employeeStatusId;
	}

	public void setEmployeeStatusId(Long employeeStatusId) {
		this.employeeStatusId = employeeStatusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}
