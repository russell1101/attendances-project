package web.employee.dao;

import java.util.List;
import core.entity.Employee;

public interface EmployeeDao {
	
	int upsert(Employee employee);
	
	int deleteById(Long id);
	
	Employee selectById(Long id);
	
	Employee selectByEmail(String email);
	
	List<Employee> selectAll();
}
