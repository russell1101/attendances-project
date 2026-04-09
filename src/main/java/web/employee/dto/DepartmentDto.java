package web.employee.dto;

import core.entity.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDto {
	private Long departmentId;
	private String departmentName;
	
	public DepartmentDto(Department dep) {
		if (dep != null) {
			this.departmentId = dep.getDepartmentId();
			this.departmentName = dep.getDepartmentName();
		}
	}

}
