package web.productManage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.entity.AdminUser;
import core.util.ApiResponse;

// TODO: [MOCK] 此 Controller 為開發測試用，正式上線前請移除，並確保 SpringMvcConfig 中 excludePathPatterns 也一併清除
@RestController
@RequestMapping("/admin/mock-login")
public class AdminMockLoginController {

	@GetMapping
	public ApiResponse<String> mockLogin(HttpServletRequest req) {
		AdminUser mockAdmin = new AdminUser();
		mockAdmin.setAdminUserId(0L);
		mockAdmin.setUsername("mock");
		mockAdmin.setDisplayName("開發測試帳號");
		mockAdmin.setIsActive(true);

		HttpSession session = req.getSession();
		session.setAttribute("adminUser", mockAdmin);

		return ApiResponse.success("mock login success");
	}
}
