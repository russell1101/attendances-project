package web.employee.dao;

import core.entity.AdminUser;

public interface AdminUserDao {
	
	AdminUser selectByUsername(String username);
}
