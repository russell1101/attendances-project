package core.pojo;

import java.sql.Timestamp;

public class GiftCard {
	private Long giftCardId;
	private String giftName;
	private Long employeeId;
	private Long redemptionId;
	private Long giftCardStatusId;
	private String giftCode;
	private Timestamp exchangedAt;
	private Timestamp usedAt;
	private Timestamp expiresAt;

	public Long getGiftCardId() {
		return giftCardId;
	}

	public void setGiftCardId(Long giftCardId) {
		this.giftCardId = giftCardId;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getRedemptionId() {
		return redemptionId;
	}

	public void setRedemptionId(Long redemptionId) {
		this.redemptionId = redemptionId;
	}

	public Long getGiftCardStatusId() {
		return giftCardStatusId;
	}

	public void setGiftCardStatusId(Long giftCardStatusId) {
		this.giftCardStatusId = giftCardStatusId;
	}

	public String getGiftCode() {
		return giftCode;
	}

	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}

	public Timestamp getExchangedAt() {
		return exchangedAt;
	}

	public void setExchangedAt(Timestamp exchangedAt) {
		this.exchangedAt = exchangedAt;
	}

	public Timestamp getUsedAt() {
		return usedAt;
	}

	public void setUsedAt(Timestamp usedAt) {
		this.usedAt = usedAt;
	}

	public Timestamp getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Timestamp expiresAt) {
		this.expiresAt = expiresAt;
	}

}
