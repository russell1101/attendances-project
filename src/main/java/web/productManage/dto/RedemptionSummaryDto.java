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

	public RedemptionSummaryDto(long productId, String productName, int stock, java.util.Date removedAt,
			long totalIssued, long availableCount, long usedCount, long expiredCount) {
		this.productId = productId;
		this.productName = productName;
		this.stock = stock;
		this.removedAt = removedAt != null ? new Timestamp(removedAt.getTime()) : null;
		this.totalIssued = totalIssued;
		this.availableCount = availableCount;
		this.usedCount = usedCount;
		this.expiredCount = expiredCount;
	}
}
