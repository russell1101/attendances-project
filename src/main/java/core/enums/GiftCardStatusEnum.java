package core.enums;

import lombok.Getter;

@Getter
public enum GiftCardStatusEnum {
	AVAILABLE(1L, "可使用"), USED(2L, "已使用"), EXPIRED(3L, "已過期");

	private final Long id;
	private final String description;

	GiftCardStatusEnum(Long id, String description) {
		this.id = id;
		this.description = description;
	}
}