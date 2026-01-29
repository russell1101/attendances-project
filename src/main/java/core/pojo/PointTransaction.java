package core.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PointTransaction {
	private Long pointTxnId;
	private Long employeeId;
	private BigDecimal pointsUpdate;
	private String reason;
	private String relatedType;
	private Long relatedId;
	private Timestamp createdAt;

	public Long getPointTxnId() {
		return pointTxnId;
	}

	public void setPointTxnId(Long pointTxnId) {
		this.pointTxnId = pointTxnId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public BigDecimal getPointsUpdate() {
		return pointsUpdate;
	}

	public void setPointsUpdate(BigDecimal pointsUpdate) {
		this.pointsUpdate = pointsUpdate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRelatedType() {
		return relatedType;
	}

	public void setRelatedType(String relatedType) {
		this.relatedType = relatedType;
	}

	public Long getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}
