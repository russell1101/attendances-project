package web.cart.controller;

import core.pojo.AttendanceRecord;
import web.cart.dao.AttendanceDAO;
import web.cart.dao.impl.AttendanceDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * ?ºå‹¤ç´€?„æŸ¥è©?Servlet
 * é¡¯ç¤º?¡å·¥?„æ??‰å‡º?¤ç???
 */
@WebServlet("/attendance/list")
public class AttendanceListServlet extends HttpServlet {

    private AttendanceDAO dao = new AttendanceDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // æª¢æŸ¥ä½¿ç”¨?…æ˜¯?¦ç™»??
        HttpSession session = req.getSession(false);
        Long empId = null;
        if (session != null) {
            empId = (Long) session.getAttribute("employeeId");
        }

        if (empId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ?¥è©¢è©²å“¡å·¥ç??€?‰å‡º?¤ç???
        List<AttendanceRecord> records = dao.findByEmployeeId(empId);
        req.setAttribute("records", records);

        // è½‰ç™¼?°å‡º?¤ç??„å?è¡¨é???
        req.getRequestDispatcher("/attendance-list.jsp").forward(req, resp);
    }
}
