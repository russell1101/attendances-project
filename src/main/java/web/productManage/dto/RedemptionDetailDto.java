package web.productManage.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import core.enums.GiftCardStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RedemptionDetailDto {
	private Long giftCardId;
	private String giftCode;
	private String giftName;
	private String employeeName;
	private String status; // AVAILABLE / USED / EXPIRED

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp exchangedAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp usedAt;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Timestamp expiresAt;

	private BigDecimal pointsSpent;

	public RedemptionDetailDto(long giftCardId, String giftCode, String giftName, String employeeName,
			long giftCardStatusId, java.util.Date exchangedAt, java.util.Date usedAt, java.util.Date expiresAt,
			BigDecimal pointsSpent) {
		this.giftCardId = giftCardId;
		this.giftCode = giftCode;
		this.giftName = giftName;
		this.employeeName = employeeName;
		this.exchangedAt = exchangedAt != null ? new Timestamp(exchangedAt.getTime()) : null;
		this.usedAt = usedAt != null ? new Timestamp(usedAt.getTime()) : null;
		this.expiresAt = expiresAt != null ? new Timestamp(expiresAt.getTime()) : null;
		this.pointsSpent = pointsSpent;

		this.status = "UNKNOWN";
		for (GiftCardStatusEnum e : GiftCardStatusEnum.values()) {
			if (e.getId() == giftCardStatusId) {
				this.status = e.name();
				break;
			}
		}
	}
}
