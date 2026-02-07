package web.employee.service.impl;

import javax.naming.NamingException;

import core.pojo.AdminUser;
import web.employee.dao.AdminUserDao;
import web.employee.dao.impl.AdminUserDaoImpl;
import web.employee.service.AdminUserService;


public class AdminUserServiceImpl implements AdminUserService{
	private AdminUserDao adminUserDao;

	public AdminUserServiceImpl() throws NamingException {
		adminUserDao = new AdminUserDaoImpl();
	}
	
	@Override
	public AdminUser login(AdminUser adminUser) {
		if (adminUser.getUsername() == null || adminUser.getUsername().isEmpty()) {
			return null;
		}
		if (adminUser.getPasswordHash() == null || adminUser.getPasswordHash().isEmpty()) {
			return null;
		}
		AdminUser returnAdminUser = adminUserDao.selectByUsername(adminUser.getUsername());
		// 判斷是否有此member
		if (returnAdminUser != null) {
			// 判斷回傳密碼與輸入密碼是否一致
			if (returnAdminUser.getPasswordHash().equals(adminUser.getPasswordHash())) {
				return returnAdminUser;
			}
		}
		return null;
	}

	

}
