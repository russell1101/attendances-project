package web.employee.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import core.entity.AdminUser;
import core.entity.Employee;
import core.util.ApiResponse;
import web.employee.dao.EmployeeDao;
import web.employee.service.AdminUserService;


@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true") // ai產
public class AdminUserController {
	@Autowired
	private AdminUserService service;
	@Autowired
	private EmployeeDao dao;

	@PostMapping("login")
	public ApiResponse<AdminUser> login(@RequestBody AdminUser adminUser, HttpServletRequest req) {
		adminUser = service.login(adminUser);
		// 登入驗證成功
		if (adminUser != null) {
			if (req.getSession(false) != null) {
				// 變更Session ID
				req.changeSessionId();
			}
			HttpSession session = req.getSession();
			session.setAttribute("adminUser", adminUser);
			return ApiResponse.success(adminUser);
		}
		return ApiResponse.error("系統錯誤");
	}
	
	@GetMapping("logout")
	public void AdminUserLogout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
	}
	
	@GetMapping("manage")
	public ApiResponse<List<Employee>> manage() {
		List<Employee> employeeList = dao.selectAll();
		return ApiResponse.success(employeeList);
	}
}
