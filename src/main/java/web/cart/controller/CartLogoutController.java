package web.cart.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import core.util.ApiResponse;

@RestController
public class CartLogoutController {

	// 前台員工登出
	@PostMapping("/frontUser/employee/component_logout")
	public ApiResponse<Void> employeeLogout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return ApiResponse.success(null);
	}

	// 後台管理員登出
	@PostMapping("/admin/component_logout")
	public ApiResponse<Void> adminLogout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return ApiResponse.success(null);
	}
}
