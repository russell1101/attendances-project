package web.cart.controller;

import web.cart.service.AttendanceService;
import web.cart.service.impl.AttendanceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 打卡功能 Servlet
 * 處理上班打卡和下班打卡
 */
@WebServlet("/attendance/clock")
public class AttendanceClockServlet extends HttpServlet {

    private AttendanceService service = new AttendanceServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 檢查使用者是否登入
        HttpSession session = req.getSession(false);
        Long empId = null;
        if (session != null) {
            empId = (Long) session.getAttribute("employeeId");
        }

        if (empId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 取得今天的打卡狀態
        int status = service.getTodayStatus(empId);
        req.setAttribute("status", status);

        // 轉發到打卡頁面
        req.getRequestDispatcher("/attendance-clock.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 檢查使用者是否登入
        HttpSession session = req.getSession(false);
        Long empId = null;
        if (session != null) {
            empId = (Long) session.getAttribute("employeeId");
        }

        if (empId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 取得使用者的動作（上班打卡或下班打卡）
        String action = req.getParameter("action");
        String msg = "";

        if ("clockIn".equals(action)) {
            msg = service.clockIn(empId);
        } else if ("clockOut".equals(action)) {
            msg = service.clockOut(empId);
        }

        // 設定訊息
        req.setAttribute("message", msg);

        // 重新取得打卡狀態
        int status = service.getTodayStatus(empId);
        req.setAttribute("status", status);

        // 轉發到打卡頁面
        req.getRequestDispatcher("/attendance-clock.jsp").forward(req, resp);
    }
}
