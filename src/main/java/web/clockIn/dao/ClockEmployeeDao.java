package web.clockIn.dao;

import core.entity.Employee;

public interface ClockEmployeeDao {
	Employee findById(Long employeeId);

	void update(Employee employee);
}
