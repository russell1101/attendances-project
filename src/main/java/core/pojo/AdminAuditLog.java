package core.pojo;

import java.sql.Timestamp;

public class AdminAuditLog {
	private Long adminLogsId;
	private Long adminUserId;
	private String action;
	private String targetType;
	private Long targetId;
	private String summary;
	private Timestamp createdAt;

	public Long getAdminLogsId() {
		return adminLogsId;
	}

	public void setAdminLogsId(Long adminLogsId) {
		this.adminLogsId = adminLogsId;
	}

	public Long getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}
