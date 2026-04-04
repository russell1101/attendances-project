package web.employee.controller;

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

import core.entity.Employee;
import core.util.ApiResponse;
import web.employee.service.EmployeeService;

@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RestController
@RequestMapping("frontUser/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService service;

	@PostMapping("login")
	public ApiResponse<Employee> login(@RequestBody Employee employee, HttpServletRequest req) {
		employee = service.login(employee);
		// 登入驗證成功
		if (employee != null) {
			if (req.getSession(false) != null) {
				// 變更Session ID
				req.changeSessionId();
			}
			HttpSession session = req.getSession();
			session.setAttribute("employee", employee);
			return ApiResponse.success(employee);
		}
		return ApiResponse.error("系統錯誤");
	}

	@GetMapping("logout")
	public void employeeLogout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
	}
	
	

}
