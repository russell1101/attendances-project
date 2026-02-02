package web.chart.service;

import web.chart.bean.Chart;

public interface ChartService {
	Chart getChartAllData(String startDate, String endDate, String deptId, String empId);
}
