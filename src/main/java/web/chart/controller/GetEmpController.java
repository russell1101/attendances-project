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

import core.pojo.Employee;
import web.chart.service.ChartService;
import web.chart.service.impl.ChartServiceImpl;

@WebServlet("/getEmps")
public class GetEmpController extends HttpServlet {
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
    		
    		Gson gson = new Gson();
        String deptIdStr = req.getParameter("deptId");
        Integer deptId = (deptIdStr != null && !deptIdStr.isEmpty()) ? Integer.parseInt(deptIdStr) : null;
        
        List<Employee> emps = service.getEmpOptions(deptId);

        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(gson.toJson(emps));
    }
}