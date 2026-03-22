package web.clockIn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeProfileDto {
    private Long employeeId;
    private String name;
    private String email;
    private String departmentName;
    private String hireDate;
    private Integer currentPoints;
    private String statusName;
    private Boolean isActive;
}