package web.employee.dao;

import core.pojo.AdminUser;

public interface AdminUserDao {
	
	AdminUser selectByUsername(String username);
}
