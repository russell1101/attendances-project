package web.employee.dao;

import java.util.List;
import core.pojo.Employee;

public interface EmployeeDao {
	
	Employee selectByEmail(String email);
	
	List<Employee> selectAll();
}
