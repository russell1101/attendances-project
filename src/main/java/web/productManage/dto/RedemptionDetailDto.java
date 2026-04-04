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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp expiresAt;

	private BigDecimal pointsSpent;

	// SELECT NEW 專用 constructor，giftCardStatusId 在此直接轉為 enum name
	public RedemptionDetailDto(Long giftCardId, String giftCode, String giftName, String employeeName,
			Long giftCardStatusId, Timestamp exchangedAt, Timestamp usedAt, Timestamp expiresAt,
			BigDecimal pointsSpent) {
		this.giftCardId = giftCardId;
		this.giftCode = giftCode;
		this.giftName = giftName;
		this.employeeName = employeeName;
		this.exchangedAt = exchangedAt;
		this.usedAt = usedAt;
		this.expiresAt = expiresAt;
		this.pointsSpent = pointsSpent;

		this.status = "UNKNOWN";
		if (giftCardStatusId != null) {
			for (GiftCardStatusEnum e : GiftCardStatusEnum.values()) {
				if (e.getId().equals(giftCardStatusId)) {
					this.status = e.name();
					break;
				}
			}
		}
	}
}
