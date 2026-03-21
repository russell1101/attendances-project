package web.clockIn.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.entity.Employee;
import core.util.ApiResponse;
import web.clockIn.dao.EmployeeDao;

@RestController
@RequestMapping("/frontUser")
public class MockLoginController {

	@Autowired
	private EmployeeDao employeeDao;

	/**
	 * 開發環境專用：模擬登入 API 用法： GET /frontUser/mock-login/1 (數字代表要登入的員工 ID)
	 */
	@GetMapping("/mock-login/{employeeId}")
	public ApiResponse<?> mockLogin(@PathVariable Long employeeId, HttpSession session) {

		// 1. 從資料庫撈取真實員工資料
		Employee employee = employeeDao.findById(employeeId);

		if (employee == null) {
			return ApiResponse.success("找不到該員工 ID，請確認資料庫是否有資料");
			// 若你的 ApiResponse 有 error 方法，也可改用 error()
		}

		// 2. 將物件強行塞入 Session，Key 必須與 Interceptor 規定的完全一致
		session.setAttribute("employee", employee);

		return ApiResponse.success("模擬登入成功！當前 Session 身分為：" + employee.getName());
	}
}