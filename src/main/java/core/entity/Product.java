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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "stock", nullable = false)
	private Integer stock;

	@Column(name = "required_points", nullable = false)
	private BigDecimal requiredPoints;

	@Version
	@Column(name = "version", nullable = false)
	private Integer version;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "released_at", insertable = false)
	private Timestamp releasedAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "removed_at")
	private Timestamp removedAt;

	@Lob
	@Column(name = "image_data")
	@JsonIgnore 
	private byte[] imageData;

	@Column(name = "valid_days")
	private Integer validDays;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
}