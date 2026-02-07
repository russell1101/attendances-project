package web.employee.dao;

import java.util.List;
import core.pojo.AdminUser;
import core.pojo.Employee;

public interface AdminUserDao {
	
	AdminUser selectByUsername(String username);
	
	List<Employee> selectAll();
}
