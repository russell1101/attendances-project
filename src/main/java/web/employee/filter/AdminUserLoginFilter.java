package web.employee.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import core.pojo.AdminUser;

@WebFilter("/adminUser/*")
public class AdminUserLoginFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		try {
			String path = req.getServletPath();
			if (path.contains("/adminUser/login-backend") || path.endsWith(".js")
					|| path.endsWith(".css")) { // 改用contains避免路徑出現異常排除登入頁面、js檔、css檔
				chain.doFilter(req, res);
			} else {
				HttpSession session = req.getSession();
				AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");
				if (adminUser != null) {
					chain.doFilter(req, res);
				} else {
					String targetPath = req.getRequestURL().toString();
					session.setAttribute("adminUserTargetPath", targetPath);
					req.getRequestDispatcher("login-backend.html").forward(req, res); // 直接跳轉⾄登⼊⾴
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
