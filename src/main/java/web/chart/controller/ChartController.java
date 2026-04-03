package web.chart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.chart.service.ChartService;
import web.chart.vo.Chart;

@RestController
@RequestMapping("/admin/chart")
public class ChartController {
	@Autowired 
	private ChartService service;
	
	@GetMapping("/chartData")
	public Chart getChart(
			@RequestParam String startDate,
			@RequestParam String endDate,
			@RequestParam(required = false) Long empId,
			@RequestParam(required = false) Long deptId) {
		return service.getChartAllData(startDate, endDate, deptId, empId);
	}
}
