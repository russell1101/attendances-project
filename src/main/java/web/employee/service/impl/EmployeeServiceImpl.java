package web.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.entity.Employee;
import core.exception.BusinessException;
import web.employee.dao.EmployeeDao;
import web.employee.service.EmployeeService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public Employee login(Employee employee) {
		Employee returnEmployee = employeeDao.selectByEmail(employee.getEmail());
		// 判斷是否有此member
		if (returnEmployee == null) {
			throw new BusinessException("無此帳號");
		}

		// 判斷回傳密碼與輸入密碼是否一致
		if (returnEmployee.getPasswordHash().equals(employee.getPasswordHash())) {
			throw new BusinessException("密碼錯誤");
		}
		
		// 判斷帳號是否停用
		if (!returnEmployee.getIsActive()) {
			throw new BusinessException("帳號停用");
		}
		
		return returnEmployee;
	}
}
