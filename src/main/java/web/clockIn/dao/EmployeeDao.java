package web.clockIn.dao;

import core.entity.Employee;

public interface EmployeeDao {
	Employee findById(Long employeeId);

	void update(Employee employee);
}
