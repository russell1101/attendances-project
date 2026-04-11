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
import core.entity.Employee;
import core.util.ApiResponse;
import web.employee.service.EmployeeService;

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
			String targetPath = (String) session.getAttribute("targetPath");
			// 確人有無欲跳轉網址
			if (targetPath != null) {
				return new ApiResponse<>(1,targetPath,employee);
			}
			return ApiResponse.success(employee);
		}
		return ApiResponse.error("系統錯誤");
	}

	@GetMapping("logout")
	public void employeeLogout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
	}
	
	// 判斷所有頁面是否登入中
		@PostMapping("checkLogin")
		public ApiResponse<Employee> checkLogin(@RequestBody Map<String, String> reqData,HttpSession session) {
			Employee employee = (Employee) session.getAttribute("employee");
			// 如果未登入傳入當前網址
			if (employee == null) {
				session.setAttribute("targetPath", reqData.get("targetPath"));
		        return new ApiResponse<>(-999, "請重新登入", null); 
		    }
			return ApiResponse.success(employee);
		}
}
