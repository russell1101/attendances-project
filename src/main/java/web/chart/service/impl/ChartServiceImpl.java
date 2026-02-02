package web.chart.service.impl;

import javax.naming.NamingException;

import web.chart.bean.Chart;
import web.chart.dao.ChartDao;
import web.chart.dao.impl.ChartDaoImpl;
import web.chart.service.ChartService;

public class ChartServiceImpl implements ChartService {
	private ChartDao dao;
	
	public ChartServiceImpl() throws NamingException {
		dao = new ChartDaoImpl();
	}

	@Override
	public Chart getChartAllData(String startDate, String endDate, String deptId, String empId) {
		Chart chart = new Chart();
		
		// 圓餅圖
		Chart pie = dao.getPie(startDate, endDate, empId);
		chart.setOnTime(pie.getOnTime());
		chart.setLate(pie.getLate());
		chart.setAbsent(pie.getAbsent());
		
		// 橫長條
		Chart rowBar = dao.getLateData(startDate, endDate);
		chart.setDeptName(rowBar.getDeptName());
		chart.setLateCounts(rowBar.getLateCounts());
		
		// 直長條(選員工才查)
		if(empId !=null && !empId.isEmpty()) {
			Chart bar = dao.getWorkingTime(startDate, endDate, empId);
			chart.setWorkingDates(bar.getWorkingDates());
			chart.setWorkingHours(bar.getWorkingHours());
		}
		
		// 散佈圖(選員工才查)
		if(empId !=null && !empId.isEmpty()) {
			Chart scatter = dao.getCheckedStatus(startDate, endDate, deptId);
			chart.setCheckInTimes(scatter.getCheckInTimes());
			chart.setCheckOutTimes(scatter.getCheckOutTimes());
		}
		
		// 統整
		Chart summary = dao.getSummaryData(startDate, endDate, deptId);
		chart.setTotalLateCounts(summary.getTotalLateCounts());
        chart.setAttendRate(summary.getAttendRate());
        chart.setNoChecked(summary.getNoChecked());
		return chart;
	}
	
	
}
