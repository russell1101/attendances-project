package web.employee.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import core.entity.Employee;
import core.util.ApiResponse;
import web.employee.service.EmployeeService;

@RestController
@RequestMapping("/frontUser/employee/login")
public class EmployeeLoginController {
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/employeeLogin")
	public ApiResponse<JsonObject> employeeLogin(@RequestBody Employee employee, HttpServletRequest req) {
		employee = employeeService.login(employee);
		JsonObject respBody = new JsonObject();
		boolean success = employee != null;
		// 登入驗證成功
		if (success) {
			if (req.getSession(false) != null) {
				// 變更Session ID
				req.changeSessionId();
			}
			HttpSession session = req.getSession();
			session.setAttribute("employee", employee);
			String targetPath = (String) session.getAttribute("employeeTargetPath");
			respBody.addProperty("location", targetPath);
			return ApiResponse.success(respBody, targetPath);
		}
		return ApiResponse.error("系統錯誤");
	}
}