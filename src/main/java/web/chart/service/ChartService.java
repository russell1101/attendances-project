package web.chart.service;

import web.chart.vo.Chart;

public interface ChartService {
	Chart getChartAllData(String startDate, String endDate, Integer deptId, Integer empId);
}
