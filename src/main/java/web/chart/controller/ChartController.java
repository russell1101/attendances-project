package web.chart.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

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
		
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		String empIdStr = req.getParameter("deptId");
		String deptIdStr = req.getParameter("deptId");
		 
		int empId = empIdStr !=null ? Integer.parseInt(empIdStr) : null;
		int deptId = deptIdStr !=null ? Integer.parseInt(deptIdStr) : null;
		
		Chart chart = service.getChartAllData(startDate, endDate, deptId, empId);
		
		resp.setContentType("application/json");
		
		
		Gson gson = new Gson();
		resp.getWriter().write(gson.toJson(chart));
	}
}
