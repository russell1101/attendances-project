package core.enums;

public enum PointTransactionType {
	ATTENDANCE("打卡"), PRODUCT_REDEMPTION("兌換商品"), SYSTEM_ADJUSTMENT("系統調整"); // 預留手動補點

	private final String description;

	PointTransactionType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
