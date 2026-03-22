package core.enums;

public enum GlobalSettingKeyEnum {
	GLOBAL_ON_TIME_BONUS("GLOBAL_ON_TIME_BONUS"), GLOBAL_LATE_PENALTY("GLOBAL_LATE_PENALTY");

	private final String key;

	GlobalSettingKeyEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}