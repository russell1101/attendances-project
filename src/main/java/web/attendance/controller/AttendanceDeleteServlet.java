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

@WebServlet("/attendance/delete")
public class AttendanceDeleteServlet extends HttpServlet {

	private AttendanceService service = new AttendanceServiceImpl();

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

		String idStr = req.getParameter("attendanceId");

		if (idStr != null && !idStr.isEmpty()) {
			try {
				Long id = Long.parseLong(idStr);
				boolean ok = service.deleteRecord(id);

				if (ok) {
					session.setAttribute("message", "刪除成功");
				} else {
					session.setAttribute("error", "刪除失敗");
				}
			} catch (NumberFormatException e) {
				// ID格式錯誤
				session.setAttribute("error", "無效ID");
				e.printStackTrace();
			}
		}

		resp.sendRedirect(req.getContextPath() + "/attendance/list");
	}
}
