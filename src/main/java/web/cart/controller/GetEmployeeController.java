package web.cart.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import core.pojo.Employee;
import core.util.ProductApiResponse;
import web.cart.service.EmployeeService;
import web.cart.service.impl.EmployeeServiceImpl;

@WebServlet("/product/empProfile")
public class GetEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeService employeeService;

	@Override
	public void init() throws ServletException {
		try {
			employeeService = new EmployeeServiceImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

		ProductApiResponse<Employee> apiResponse = new ProductApiResponse<>();

		try {
			JsonObject jsonObject = gson.fromJson(req.getReader(), JsonObject.class);

			if (jsonObject == null || !jsonObject.has("employeeId")) {
				apiResponse.setSuccess(-1);
				apiResponse.setErrMsg("員工資料讀取失敗");
			} else {
				Long id = jsonObject.get("employeeId").getAsLong();
				Employee employee = employeeService.getEmployeeProfile(id);

				if (employee != null) {
					apiResponse.setSuccess(1);
					apiResponse.setData(employee);
				} else {
					apiResponse.setSuccess(0);
					apiResponse.setErrMsg("查無此員工");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse.setSuccess(-1);
			apiResponse.setErrMsg("系統錯誤: " + e.getMessage());
		}
		
		out.write(gson.toJson(apiResponse));
	}
}
