package web.employee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.entity.Employee;
import core.exception.BusinessException;
import web.employee.dao.EmployeeDao;
import web.employee.dto.EmployeeDto;
import web.employee.service.EmployeeService;
import web.employee.util.EmailUtil;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EmailUtil emailUtil;

	@Override
	public Employee login(Employee employee) {
		Employee returnEmployee = employeeDao.selectByEmail(employee.getEmail());
		// 判斷是否有此member
		if (returnEmployee == null) {
			throw new BusinessException("無此帳號");
		}
		
		// 判斷回傳密碼與輸入密碼是否一致
		if (passwordEncoder.matches(employee.getPasswordHash(), returnEmployee.getPasswordHash())) {
			throw new BusinessException("密碼錯誤");
		}

		// 判斷帳號是否停用
		if (!returnEmployee.getIsActive()) {
			throw new BusinessException("帳號停用");
		}

		// 判斷帳號是否停用(離職)
		if (returnEmployee.getEmployeeStatusId() == 2) {
			throw new BusinessException("帳號停用");
		}

		return returnEmployee;

	}
	
	@Override
	public int saveEmployee(Employee employee) {
		String randomPassword = RandomStringUtils.secureStrong().nextAlphanumeric(12);
		emailUtil.sendPasswordEmail(employee.getEmail(), randomPassword);
		employee.setPasswordHash(passwordEncoder.encode(randomPassword));
		return employeeDao.upsert(employee);
	}
	
	// 員工修改為停用狀態
	@Override
	public int deleteEmployee(Long id) {
		Employee employee = employeeDao.selectById(id);
		employee.setIsActive(false);
		return employeeDao.upsert(employee);
	}
	
	@Override
	public List<EmployeeDto> getAllEmployees() {
		List<Employee> employees = employeeDao.selectAll();
		List<EmployeeDto> employeesDto = new ArrayList<>();

		for (Employee emp : employees) {
			EmployeeDto dto = new EmployeeDto(emp);
			employeesDto.add(dto);
		}
		return employeesDto;
	}
}
