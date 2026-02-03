package web.chart.dao;

import web.chart.bean.Chart;

public interface ChartDao {
		// 查圓餅圖資料
		Chart getPie(String startDate, String endDate, String deptId);
		
		// 遲到排行
		Chart getLateData(String startDate, String endDate);
		
		// 工時統計
		Chart getWorkingTime(String startDate, String endDate, String empId);
		
		// 打卡紀錄
		Chart getCheckedStatus(String startDate, String endDate, String empId);
		
		// 統計紀錄
		Chart getSummaryData(String startDate, String endDate, String deptId);
}
