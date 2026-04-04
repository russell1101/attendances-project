package web.productManage.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RedemptionSummaryDto {
	private Long productId;
	private String productName;
	private Integer stock;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp removedAt;

	private Long totalIssued;    // 已發出兌換券總數
	private Long availableCount; // 可使用
	private Long usedCount;      // 已使用
	private Long expiredCount;   // 已過期

	// SELECT NEW 專用 constructor，null 安全處理 SUM 回傳值
	public RedemptionSummaryDto(Long productId, String productName, Integer stock, Timestamp removedAt,
			Long totalIssued, Long availableCount, Long usedCount, Long expiredCount) {
		this.productId = productId;
		this.productName = productName;
		this.stock = stock;
		this.removedAt = removedAt;
		this.totalIssued = totalIssued != null ? totalIssued : 0L;
		this.availableCount = availableCount != null ? availableCount : 0L;
		this.usedCount = usedCount != null ? usedCount : 0L;
		this.expiredCount = expiredCount != null ? expiredCount : 0L;
	}
}
