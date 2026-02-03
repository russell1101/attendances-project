package web.employee.controller;

import java.io.IOException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import web.employee.service.EmployeeService;
import web.employee.service.impl.EmployeeServiceImpl;
import web.employee.vo.Employee;

@WebServlet("/front-login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeService employeeService;

	@Override
	public void init() throws ServletException {
		try {
			employeeService = new EmployeeServiceImpl();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		Employee employee = gson.fromJson(req.getReader(), Employee.class);
		
		// 檢查傳入資料
		System.out.println(employee.getAccount());
		System.out.println(employee.getPasswordHash());
		
		// service層執行後回應
		Employee returnEmployee = employeeService.login(employee);
		JsonObject respBody = new JsonObject();
		
		// 回傳不是null即是有此member且密碼一致
		boolean success = returnEmployee != null;
		respBody.addProperty("success", success);

		// 登入驗證成功
		if (success) {
			if (req.getSession(false) != null) {
				// 變更Session ID
				req.changeSessionId();
			}
			// 此屬性物件即⽤來區分是否登⼊中
			req.getSession().setAttribute("employee", returnEmployee);
			// 建議前端跳轉到哪一頁
			String location = "http://127.0.0.1:5500/rci-cart-back-company-policy.html";
			respBody.addProperty("location", location);
		} else {
			// 登⼊失敗回傳訊息
			String errMsg = "使⽤者名稱或密碼錯誤";
			respBody.addProperty("errMsg", errMsg);
		}
		resp.setContentType("application/json");
		resp.getWriter().write(respBody.toString());
	}
}
