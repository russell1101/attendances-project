package web.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

@RestController
@RequestMapping("/frontUser/employee/login")
public class EmployeeLogoutController {

	@GetMapping("employeeLogout")
	@ResponseBody
	public void employeeLogout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
	}
}
