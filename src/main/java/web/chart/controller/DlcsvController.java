package web.chart.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.chart.service.ChartService;
import web.chart.service.impl.ChartServiceImpl;

@WebServlet("/exportCsv")
public class DlcsvController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ChartService service;

    @Override
    public void init() throws ServletException {
        try {
            service = new ChartServiceImpl();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String empIdStr = req.getParameter("empId");
        String deptIdStr = req.getParameter("deptId");

        Integer empId = (empIdStr != null && !empIdStr.isEmpty()) ? Integer.parseInt(empIdStr) : null;
        Integer deptId = (deptIdStr != null && !deptIdStr.isEmpty()) ? Integer.parseInt(deptIdStr) : null;

        resp.setContentType("text/csv;charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=attendance_report.csv");
        String csvContent = service.getCsvString(startDate, endDate, deptId, empId);

        resp.getWriter().write(csvContent);
    }
}