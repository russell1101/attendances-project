package core.pojo;

import java.sql.Timestamp;

public class GiftCardStatus {
	private Long giftCardStatusId;
	private String statusName;
	private Timestamp createdAt;

	public Long getGiftCardStatusId() {
		return giftCardStatusId;
	}

	public void setGiftCardStatusId(Long giftCardStatusId) {
		this.giftCardStatusId = giftCardStatusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}
