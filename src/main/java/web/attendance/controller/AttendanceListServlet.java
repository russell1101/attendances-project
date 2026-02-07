package web.attendance.controller;

import web.attendance.bean.AttendanceRecordVO;
import web.attendance.service.AttendanceService;
import web.attendance.service.impl.AttendanceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

// 顯示員工的出勤紀錄列表
@WebServlet("/attendance/list")
public class AttendanceListServlet extends HttpServlet {

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

		if (empId == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		// 用 service 拿出這個員工的所有打卡紀錄
		List<AttendanceRecordVO> records = service.getRecordsByEmployeeId(empId);
		req.setAttribute("records", records);

		req.getRequestDispatcher("/attendance-list.jsp").forward(req, resp);
	}
}
