package web.employee.dao;

import java.util.List;
import core.entity.Employee;
import web.employee.dto.EmployeeDto;


public interface EmployeeDao {
	
	int upsert(Employee employee);
	
	int deleteById(Integer id);
	
	Employee selectById(Integer id);
	
	Employee selectByEmail(String email);
	
	List<EmployeeDto> selectAll();
}
