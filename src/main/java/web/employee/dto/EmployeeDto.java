package web.employee.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import core.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {
	private Long employeeId;
	private String name;
	private String email;
	private String googleSub;
	private Date hireDate;
	private BigDecimal currentPoints;
	private String departmentName;
	private String employeeStatus;
	private Boolean isActive;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public EmployeeDto(Employee emp) {
		if (emp != null) {
			this.employeeId = emp.getEmployeeId();
			this.name = emp.getName();
			this.email = emp.getEmail();
			this.googleSub = emp.getGoogleSub();
			this.hireDate = emp.getHireDate();
			this.currentPoints = emp.getCurrentPoints();
			this.isActive = emp.getIsActive();
			this.createdAt = emp.getCreatedAt();
			this.updatedAt = emp.getUpdatedAt();

			if (emp.getDepartment() != null) {
				this.departmentName = emp.getDepartment().getDepartmentName();
			}
			if (emp.getEmployeeStatus() != null) {
				this.employeeStatus = emp.getEmployeeStatus().getStatusName();
	        }
		}
	}
}
