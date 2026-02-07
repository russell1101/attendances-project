package web.employee.dao;

import core.pojo.Employee;

public interface EmployeeDao {
	
	Employee selectByEmail(String email);
	
}
