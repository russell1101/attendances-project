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
			if (path.endsWith("login-backend.html") || path.endsWith(".js")) { // 不用startsWith改用endsWith並排除js檔，避免遞迴發生
				chain.doFilter(req, res);
			} else {
				HttpSession session = req.getSession();
				AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");
				if (adminUser != null) {
					chain.doFilter(req, res);
				} else {
					String targetPath = req.getRequestURL().toString();
					session.setAttribute("adminUserTargetPath", targetPath);
					res.sendRedirect(req.getContextPath() + "/adminUser/login-backend.html"); // 路徑確保從應⽤系統環境根路徑開始
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
