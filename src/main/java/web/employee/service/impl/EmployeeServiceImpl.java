package web.employee.service.impl;

import javax.naming.NamingException;
import web.employee.dao.EmployeeDao;
import web.employee.dao.impl.EmployeeDaoImpl;
import web.employee.service.EmployeeService;
import web.employee.vo.Employee;

public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeDao employeeDao;

	public EmployeeServiceImpl() throws NamingException {
		employeeDao = new EmployeeDaoImpl();
	}

	@Override
	public Employee login(Employee employee) {
		if (employee.getAccount() == null || employee.getAccount().isEmpty()) {
			return null;
		}
		if (employee.getPasswordHash() == null || employee.getPasswordHash().isEmpty()) {
			return null;
		}
		Employee returnEmployee = employeeDao.selectByAccount(employee.getAccount());
		// 判斷是否有此member
		if (returnEmployee != null) {
			// 判斷回傳密碼與輸入密碼是否一致
			if (returnEmployee.getPasswordHash().equals(employee.getPasswordHash())) {
				return returnEmployee;
			}
		}
		return null;
	}
}
