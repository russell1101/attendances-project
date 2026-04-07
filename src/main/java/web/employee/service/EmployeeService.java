package web.employee.service;

import java.util.List;

import core.entity.Employee;
import web.employee.dto.EmployeeDto;

public interface EmployeeService {
	
	Employee login(Employee employee);
	
	List<EmployeeDto> getAllEmployees();
}
