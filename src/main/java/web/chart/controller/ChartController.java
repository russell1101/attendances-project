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
		service = new ChartServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Gson gson = new Gson();
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		String empIdStr = req.getParameter("empId");
		String deptIdStr = req.getParameter("deptId");

		Integer empId = (empIdStr != null && !empIdStr.isEmpty()) ? Integer.parseInt(empIdStr) : null;
		Integer deptId = (deptIdStr != null && !deptIdStr.isEmpty()) ? Integer.parseInt(deptIdStr) : null;

		Chart chart = service.getChartAllData(startDate, endDate, deptId, empId);
		resp.setContentType("application/json;charset=UTF-8");
		resp.getWriter().write(gson.toJson(chart));
	}
}