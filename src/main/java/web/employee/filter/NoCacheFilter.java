package web.employee.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class NoCacheFilter extends HttpFilter{
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
		chain.doFilter(req, res);
	}
}
