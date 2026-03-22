package web.employee.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import core.entity.Employee;


@WebFilter("/employee/*")
public class EmployeeLoginFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		JsonObject respbody = new JsonObject();
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		if (employee != null) {
			chain.doFilter(req, res);
		} else {
			String targetPath = req.getRequestURL().toString();
            session.setAttribute("employeeTargetPath", targetPath);
			respbody.addProperty("success", -999);
			respbody.addProperty("errMsg", "未登入");
			res.setContentType("application/json;charset=UTF-8");
			res.getWriter().write(respbody.toString());
		}
	}
}
