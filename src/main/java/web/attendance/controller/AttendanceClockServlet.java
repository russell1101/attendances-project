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

@WebServlet("/attendance/clock")
public class AttendanceClockServlet extends HttpServlet {

	private AttendanceService service = new AttendanceServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		Long empId = null;
		if (session != null) {
			empId = (Long) session.getAttribute("employeeId");
		}

		// 檢查是否登入
		if (empId == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		int status = service.getTodayStatus(empId);
		req.setAttribute("status", status);

		req.getRequestDispatcher("/attendance-clock.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

		// 判斷是上班還是下班
		if ("clockIn".equals(action)) {
			msg = service.clockIn(empId);
			System.out.println("上班打卡: " + msg);
		} else if ("clockOut".equals(action)) {
			msg = service.clockOut(empId);
			System.out.println("下班打卡: " + msg);
		}

		req.setAttribute("message", msg);

		int status = service.getTodayStatus(empId);
		req.setAttribute("status", status);

		req.getRequestDispatcher("/attendance-clock.jsp").forward(req, resp);
	}
}
