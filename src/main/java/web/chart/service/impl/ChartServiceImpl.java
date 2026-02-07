package web.chart.service.impl;

import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

import core.pojo.Department;
import core.pojo.Employee;
import web.chart.dao.ChartDao;
import web.chart.dao.impl.ChartDaoImpl;
import web.chart.service.ChartService;
import web.chart.vo.Chart;

public class ChartServiceImpl implements ChartService {
	private ChartDao dao;

	public ChartServiceImpl() throws NamingException {
		dao = new ChartDaoImpl();
	}
	
	// 圖表
	@Override
	public Chart getChartAllData(String startDate, String endDate, Integer deptId, Integer empId) {
		Chart chart = new Chart();

		// 圓餅圖 (準時/遲到/曠職)
		Chart pie = dao.getPie(startDate, endDate, deptId);
		if (pie != null) {
			chart.setOnTime(pie.getOnTime());
			chart.setLate(pie.getLate());
			chart.setAbsent(pie.getAbsent());
		}

		// 橫向長條圖 (部門遲到排行)
		Chart rowBar = dao.getLateData(startDate, endDate);
		if (rowBar != null) {
			chart.setDeptName(rowBar.getDeptName());
			chart.setLateCounts(rowBar.getLateCounts());
		}

		// 員工個人圖表 (只有選定員工時才查詢)
		if (empId != null) {
			// 垂直長條圖 (每日工時)
			Chart bar = dao.getWorkingTime(startDate, endDate, empId);
			if (bar != null) {
				chart.setWorkingDates(bar.getWorkingDates());
				chart.setWorkingHours(bar.getWorkingHours());
			}

			// 散佈圖 (打卡時間點)
			Chart scatter = dao.getCheckedStatus(startDate, endDate, empId);
			if (scatter != null) {
				chart.setCheckInTimes(scatter.getCheckInTimes());
				chart.setCheckOutTimes(scatter.getCheckOutTimes());
			}
		}

		// 4. 統計數字看板 (總遲到次數、出席率、未打卡人數)
		Chart summary = dao.getSummaryData(startDate, endDate, deptId);
		if (summary != null) {
			chart.setTotalLateCounts(summary.getTotalLateCounts());
			chart.setAttendRate(summary.getAttendRate());
			chart.setNoChecked(summary.getNoChecked());
		}

		return chart;
	}

	// 取得所有部門
	@Override
	public List<Department> getDeptOptions() {
		return dao.getDepartments();
	}

	// 根據部門 ID 取得員工
	@Override
	public List<Employee> getEmpOptions(Integer deptId) {
		return dao.getEmployees(deptId);
	}

	// CSV
	@Override
	public String getCsvString(String startDate, String endDate, Integer deptId, Integer empId) {
		List<Map<String, Object>> data = dao.getAttendanceList(startDate, endDate, deptId, empId);

		StringBuilder sb = new StringBuilder();

		sb.append('\ufeff');

		// 標題
		sb.append("姓名,部門,日期,上班打卡,下班打卡,狀態\n");

		// 遍歷每一筆資料並組合成 CSV 格式
		if (data != null && !data.isEmpty()) {
			for (Map<String, Object> row : data) {
				sb.append(row.get("name") != null ? row.get("name") : "").append(",")
						.append(row.get("deptName") != null ? row.get("deptName") : "").append(",")
						.append(row.get("workDate") != null ? row.get("workDate") : "").append(",")
						.append(row.get("clockIn") != null ? row.get("clockIn") : "").append(",")
						.append(row.get("clockOut") != null ? row.get("clockOut") : "").append(",")
						.append(row.get("status") != null ? row.get("status") : "").append("\n");
			}
		}

		return sb.toString();
	}
}