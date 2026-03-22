package web.employee.dao;

import java.util.List;
import core.entity.Employee;


public interface EmployeeDao {
	
	Employee selectByEmail(String email);
	
	List<Employee> selectAll();
}
