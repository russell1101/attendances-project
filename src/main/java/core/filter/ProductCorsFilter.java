package core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 此Filter 會攔截所有的請求 (/*)，並加上允許跨域的 Header
 */
@WebFilter("/*")
public class ProductCorsFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
		res.setHeader("Access-Control-Allow-Credentials", "true");

		if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		// 往下走
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}
}