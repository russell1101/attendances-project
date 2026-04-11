package core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import core.exception.BusinessException;

@Component
public class AdminInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			return true;
		}
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("adminUser") == null) {
//			String targetPath = request.getRequestURL().toString();
//			session.setAttribute("targetPath", targetPath);
			throw new BusinessException(-999, "請先登入");
		}

		return true;
	}
}