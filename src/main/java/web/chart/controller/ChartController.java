package web.chart.controller;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import core.pojo.Department;
import core.pojo.Employee;
import web.chart.service.ChartService;
import web.chart.service.impl.ChartServiceImpl;
import web.chart.vo.Chart;

@WebServlet("/chart")
public class ChartController extends HttpServlet {
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
        String action = req.getParameter("action");
        
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String empIdStr = req.getParameter("empId");
        String deptIdStr = req.getParameter("deptId");

        Integer empId = (empIdStr != null && !empIdStr.isEmpty()) ? Integer.parseInt(empIdStr) : null;
        Integer deptId = (deptIdStr != null && !deptIdStr.isEmpty()) ? Integer.parseInt(deptIdStr) : null;


        if ("getDepts".equals(action)) {
            List<Department> depts = service.getDeptOptions();
            sendJson(resp, depts);
            return;
        }

        
        if ("getEmps".equals(action)) {
            List<Employee> emps = service.getEmpOptions(deptId);
            sendJson(resp, emps);
            return;
        }

        if ("exportCsv".equals(action)) {
            // 下載CSV 檔案
            resp.setContentType("text/csv;charset=UTF-8");
            resp.setHeader("Content-Disposition", "attachment; filename=attendance_report.csv");

            // 向 Service 拿處理好的 CSV
            String csvContent = service.getCsvString(startDate, endDate, deptId, empId);
            
            resp.getWriter().write(csvContent);
            return;
        }

        // 當前端沒有帶 action 參數時，預設執行原本的圖表查詢
        Chart chart = service.getChartAllData(startDate, endDate, deptId, empId);
        sendJson(resp, chart);
    }

    //  JSON 格式
    private void sendJson(HttpServletResponse resp, Object obj) throws IOException {
        Gson gson = new Gson();
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(gson.toJson(obj));
    }
}