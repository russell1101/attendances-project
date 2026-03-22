package web.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

@RestController
@RequestMapping("/admin/employee/login")
public class AdminUserLogoutController {

	@GetMapping("adminUserLogout")
	@ResponseBody
	public void adminUserLogout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
	}
}
