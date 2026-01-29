package core.pojo;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

public class Department {
	private Long departmentId;
	private String departmentName;
	private Time workStartTime;
	private Time workEndTime;
	private BigDecimal onTimeBonusPoints;
	private BigDecimal latePenaltyPoints;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Time getWorkStartTime() {
		return workStartTime;
	}

	public void setWorkStartTime(Time workStartTime) {
		this.workStartTime = workStartTime;
	}

	public Time getWorkEndTime() {
		return workEndTime;
	}

	public void setWorkEndTime(Time workEndTime) {
		this.workEndTime = workEndTime;
	}

	public BigDecimal getOnTimeBonusPoints() {
		return onTimeBonusPoints;
	}

	public void setOnTimeBonusPoints(BigDecimal onTimeBonusPoints) {
		this.onTimeBonusPoints = onTimeBonusPoints;
	}

	public BigDecimal getLatePenaltyPoints() {
		return latePenaltyPoints;
	}

	public void setLatePenaltyPoints(BigDecimal latePenaltyPoints) {
		this.latePenaltyPoints = latePenaltyPoints;
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
