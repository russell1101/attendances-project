package web.employee.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

@WebServlet("/employee/logout-front")
public class EmployeeLogoutController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JsonObject respBody = new JsonObject();
		if (req.getSession(false) != null) {
			req.getSession().invalidate();
			respBody.addProperty("success", true);
		}
		resp.setContentType("application/json");
		resp.getWriter().write(respBody.toString());
	}
}
