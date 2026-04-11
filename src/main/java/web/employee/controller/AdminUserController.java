package web.employee.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import core.entity.AdminUser;
import core.util.ApiResponse;
import web.employee.service.AdminUserService;

@RestController
@RequestMapping("admin")
public class AdminUserController {
	@Autowired
	private AdminUserService adminUserService;
	
	@PostMapping("login")
	public ApiResponse<AdminUser> login(@RequestBody AdminUser adminUser, HttpServletRequest req) {
		adminUser = adminUserService.login(adminUser);
		// 登入驗證成功
		if (adminUser != null) {
			if (req.getSession(false) != null) {
				// 變更Session ID
				req.changeSessionId();
			}
			HttpSession session = req.getSession();
			session.setAttribute("adminUser", adminUser);
			String targetPath = (String) session.getAttribute("targetPath");
			// 確人有無欲跳轉網址
			if (targetPath != null) {
				return new ApiResponse<>(1,targetPath,adminUser);
			}
			return ApiResponse.success(adminUser);
		}
		return ApiResponse.error("系統錯誤");
	}

	@GetMapping("logout")
	public void AdminUserLogout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
	}
	
	// 判斷所有頁面是否登入中
	@PostMapping("checkLogin")
	public ApiResponse<AdminUser> checkLogin(@RequestBody Map<String, String> reqData,HttpSession session) {
		AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");
		// 如果未登入傳入當前網址
		if (adminUser == null) {
			session.setAttribute("targetPath", reqData.get("targetPath"));
	        return new ApiResponse<>(-999, "請重新登入", null); 
	    }
		return ApiResponse.success(adminUser);
	}
}
