package web.employee.controller;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import core.pojo.Employee;
import web.employee.dao.EmployeeDao;
import web.employee.dao.impl.EmployeeDaoImpl;

@WebServlet("/adminUser/adminUser-main-page")
public class AdminUserFetchDataController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeDao employeeDao;

	public AdminUserFetchDataController() throws NamingException {
		employeeDao = new EmployeeDaoImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Employee> employees = employeeDao.selectAll();
		JsonObject respBody = new JsonObject();
		boolean success = employees != null;
		Gson gson = new Gson();
		if (success) {
			respBody.add("data", gson.toJsonTree(employees));
		} else {
			respBody.addProperty("errMsg", "無員工資料");
		}
		respBody.addProperty("success", success);
		resp.setContentType("application/json");
		resp.getWriter().write(respBody.toString());
	}
}
