package web.cart.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductDto {
	private Long productId;
	private String productName;
	private String description;
	private Integer stock;
	private BigDecimal requiredPoints;
	private Integer version;
	private Timestamp releasedAt;
	private Timestamp removedAt;
	private String imageData;
	private Integer validDays;
	private Timestamp createdAt;
	private Timestamp updatedAt;
}