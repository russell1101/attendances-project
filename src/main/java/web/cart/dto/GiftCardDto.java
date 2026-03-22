package web.cart.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class GiftCardDto {
	private Long giftCardId;
	private String giftName;
	private String giftCode;
	private Long giftCardStatusId;
	private String status;
	private Timestamp exchangedAt;
	private Timestamp usedAt;
	private Timestamp expiresAt;
}