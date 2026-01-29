package core.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ProductRedemption {
	private Long redemptionId;
	private Long employeeId;
	private Long productId;
	private Integer quantity;
	private BigDecimal pointsSpent;
	private Timestamp redeemedAt;

	public Long getRedemptionId() {
		return redemptionId;
	}

	public void setRedemptionId(Long redemptionId) {
		this.redemptionId = redemptionId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPointsSpent() {
		return pointsSpent;
	}

	public void setPointsSpent(BigDecimal pointsSpent) {
		this.pointsSpent = pointsSpent;
	}

	public Timestamp getRedeemedAt() {
		return redeemedAt;
	}

	public void setRedeemedAt(Timestamp redeemedAt) {
		this.redeemedAt = redeemedAt;
	}

}
