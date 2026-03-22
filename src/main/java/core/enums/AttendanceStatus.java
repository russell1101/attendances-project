package core.enums;

public enum AttendanceStatus {
	ON_TIME("準時"), LATE("遲到"), EARLY_LEAVE("早退"), MISSING("缺卡");

	private final String description;

	AttendanceStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
