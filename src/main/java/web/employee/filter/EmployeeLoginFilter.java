package web.employee.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import core.pojo.Employee;

@WebFilter("/employee/*")
public class EmployeeLoginFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		try {
			if (req.getServletPath().startsWith("/employee/login-front.html")) {
				chain.doFilter(req, res);
			} else {
				HttpSession session = req.getSession();
				Employee employee = (Employee) session.getAttribute("employee");
				if (employee != null) {
					chain.doFilter(req, res);
				} else {
					String targetPath = req.getRequestURL().toString();
					session.setAttribute("employeeTargetPath", targetPath);
					res.sendRedirect("login-front.html");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
