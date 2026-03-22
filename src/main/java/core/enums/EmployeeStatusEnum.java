package core.enums;

public enum EmployeeStatusEnum {
	ACTIVE(1L, "在職"), RESIGNED(2L, "離職");

	private final Long id;
	private final String description;

	EmployeeStatusEnum(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public static EmployeeStatusEnum fromId(Long id) {
		for (EmployeeStatusEnum status : EmployeeStatusEnum.values()) {
			if (status.getId().equals(id)) {
				return status;
			}
		}
		return null;
	}
}