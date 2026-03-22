package web.employee.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import core.entity.AdminUser;

import core.util.ApiResponse;
import web.employee.service.AdminUserService;

@RestController
@RequestMapping("/admin/employee/login")
public class AdminUserLoginController {
	@Autowired
	private AdminUserService adminUserService;

	@PostMapping("/adminUserLogin")
	public ApiResponse<JsonObject> adminUserLogin(@RequestBody AdminUser adminUser, HttpServletRequest req) {
		adminUser = adminUserService.login(adminUser);
		JsonObject respBody = new JsonObject();
		boolean success = adminUser != null;
		// 登入驗證成功
		if (success) {
			if (req.getSession(false) != null) {
				// 變更Session ID
				req.changeSessionId();
			}
			HttpSession session = req.getSession();
			session.setAttribute("adminUser", adminUser);
			String targetPath = (String) session.getAttribute("adminUserTargetPath");
			respBody.addProperty("location", targetPath);
			return ApiResponse.success(respBody);
		}
		return ApiResponse.error("系統錯誤");
	}
}
