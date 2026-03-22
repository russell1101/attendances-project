package web.cart.dao;

import core.entity.Employee;

public interface CartEmployeeDao {
	Employee findById(Long employeeId);

	void update(Employee employee);
}