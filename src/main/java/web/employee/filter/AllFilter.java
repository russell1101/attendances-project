package web.employee.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AllFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// 強制瀏覽器不要快取設定「不准存、不准快取、每次都要問伺服器」
		res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
		// 針對舊版瀏覽器
		res.setHeader("Pragma", "no-cache");
		// 強制過期
		res.setDateHeader("Expires", 0);

		// CORS跨域請求
		res.setHeader("Access-Control-Allow-Origin", "*"); // 允許任何網域來訪
		res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // 後端接受的HTTP方法
		res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization"); // 允許前端協帶哪些標頭
		res.setHeader("Access-Control-Allow-Credentials", "true"); // 與「"Access-Control-Allow-Origin", "*"」為非法組合，之後得修正

		if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		chain.doFilter(req, res);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}
}
