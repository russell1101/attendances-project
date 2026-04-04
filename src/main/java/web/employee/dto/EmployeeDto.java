package web.employee.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
	private Long employeeId;
	private String name;
	private String email;
	private String googleSub;
	private Date hireDate;
	private BigDecimal currentPoints;
	private String departmentName;
	private String statusName;
	private Boolean isActive;
	private Timestamp createdAt;
	private Timestamp updatedAt;
}
