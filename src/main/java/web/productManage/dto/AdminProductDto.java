package web.productManage.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AdminProductDto {
	private Long productId;
	private String productName;
	private String description;
	private Integer stock;
	private BigDecimal requiredPoints;
	private Integer version;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp releasedAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp removedAt;

	// Base64 字串，對應 entity 的 byte[] imageData
	private String imageData;

	private Integer validDays;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp updatedAt;
}
