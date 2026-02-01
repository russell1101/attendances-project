package core.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product {
	private Long productId;
	private String productName;
	private Integer stock;
	private BigDecimal requiredPoints;
	private Integer version;
	private Timestamp releasedAt;
	private Timestamp removedAt;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private byte[] imageData;
	private String description;
	private Integer validDays;

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public BigDecimal getRequiredPoints() {
		return requiredPoints;
	}

	public void setRequiredPoints(BigDecimal requiredPoints) {
		this.requiredPoints = requiredPoints;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Timestamp getReleasedAt() {
		return releasedAt;
	}

	public void setReleasedAt(Timestamp releasedAt) {
		this.releasedAt = releasedAt;
	}

	public Timestamp getRemovedAt() {
		return removedAt;
	}

	public void setRemovedAt(Timestamp removedAt) {
		this.removedAt = removedAt;
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
