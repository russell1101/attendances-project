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
			String path = req.getServletPath();
			if (path.contains("/employee/login-front") || path.endsWith(".js") || path.endsWith(".css")) {
				chain.doFilter(req, res);
			} else {
				HttpSession session = req.getSession();
				Employee employee = (Employee) session.getAttribute("employee");
				if (employee != null) {
					chain.doFilter(req, res);
				} else {
					String targetPath = req.getRequestURL().toString();
					session.setAttribute("employeeTargetPath", targetPath);
					req.getRequestDispatcher("login-front.html").forward(req, res);;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
