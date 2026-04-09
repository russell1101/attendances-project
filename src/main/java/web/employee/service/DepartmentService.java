package web.employee.service;

import java.util.List;
import core.entity.Department;
import web.employee.dto.DepartmentDto;

public interface DepartmentService {

	int saveDepartment(Department department);

	int deleteDepartment(Long id);
	
	List<Department> getAllDepartments();
	
	// 只有id和name
	List<DepartmentDto> getAllDepartmentList();

}
