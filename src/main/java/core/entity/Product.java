package core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "description")
	private String description;

	@Column(name = "stock")
	private Integer stock;

	@Column(name = "required_points")
	private BigDecimal requiredPoints;

	@Version
	@Column(name = "version")
	private Integer version;

	@Column(name = "released_at", insertable = false)
	private Timestamp releasedAt;

	@Column(name = "removed_at")
	private Timestamp removedAt;

	@Lob
	@Column(name = "image_data")
	private byte[] imageData;

	@Column(name = "valid_days")
	private Integer validDays;

	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;

	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}