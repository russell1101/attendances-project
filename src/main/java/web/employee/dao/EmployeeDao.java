package web.employee.dao;

import web.employee.vo.Employee;

public interface EmployeeDao {
	
	Employee selectByAccount(String account);
	
}
