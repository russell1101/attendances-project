package web.cart.service.impl;

import core.pojo.Employee;
import web.cart.dao.EmployeeDao;
import web.cart.dao.impl.EmployeeDaoImpl;
import web.cart.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeDao employeeDao;

	public EmployeeServiceImpl() throws Exception {
		employeeDao = new EmployeeDaoImpl();
	}

	@Override
	public Employee getEmployeeProfile(Long employeeId) {
		Employee employee = employeeDao.selectById(employeeId);

		// 移除密碼
		if (employee != null) {
			employee.setPasswordHash(null);
		}

		return employee;
	}
}