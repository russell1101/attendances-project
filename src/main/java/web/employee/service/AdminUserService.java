package web.employee.service;

import core.entity.AdminUser;
import core.entity.Employee;

public interface AdminUserService {
	
	AdminUser login(AdminUser adminUser);
	
	int saveEmployee(Employee employee);
	
	int deleteEmployee(Long id);
}
