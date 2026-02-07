package web.attendance.controller;

import web.attendance.service.AttendanceService;
import web.attendance.service.impl.AttendanceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 處理員工打卡的 servlet
@WebServlet("/attendance/clock")
public class AttendanceClockServlet extends HttpServlet {

	private AttendanceService service = new AttendanceServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");

		HttpSession session = req.getSession(false);
		Long empId = null;
		if (session != null) {
			empId = (Long) session.getAttribute("employeeId");
		}

		// 如果沒登入就跳到登入頁
		if (empId == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		// 取得今天的打卡狀態
		int status = service.getTodayStatus(empId);
		req.setAttribute("status", status);

		req.getRequestDispatcher("/attendance-clock.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");

		HttpSession session = req.getSession(false);
		Long empId = null;
		if (session != null) {
			empId = (Long) session.getAttribute("employeeId");
		}

		if (empId == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		String action = req.getParameter("action");
		String msg = "";

		// 看是要上班打卡還是下班打卡
		if ("clockIn".equals(action)) {
			msg = service.clockIn(empId);
			System.out.println("上班打卡: " + msg); // debug用
		} else if ("clockOut".equals(action)) {
			msg = service.clockOut(empId);
			System.out.println("下班打卡: " + msg); // debug用
		} else {
			msg = "無效的操作";
		}

		req.setAttribute("message", msg);

		// 更新狀態
		int status = service.getTodayStatus(empId);
		req.setAttribute("status", status);

		req.getRequestDispatcher("/attendance-clock.jsp").forward(req, resp);
	}
}
