package web.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.entity.AdminUser;
import core.exception.BusinessException;
import web.employee.dao.AdminUserDao;
import web.employee.service.AdminUserService;

@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {
	@Autowired
	private AdminUserDao adminUserDao;

	@Override
	public AdminUser login(AdminUser adminUser) {
		AdminUser returnAdminUser = adminUserDao.selectByUsername(adminUser.getUsername());
		// 判斷是否有此member
		if (returnAdminUser == null) {
			throw new BusinessException("無此帳號");
		}
		// 判斷回傳密碼與輸入密碼是否一致
		if (!returnAdminUser.getPasswordHash().equals(adminUser.getPasswordHash())) {
			throw new BusinessException("密碼錯誤");
		}
		
		// 判斷帳號是否停用
		if (!returnAdminUser.getIsActive()) {
			throw new BusinessException("帳號停用");
		}
		return returnAdminUser;
	}

}
