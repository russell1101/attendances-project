package web.chart.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import core.entity.Department;
import core.entity.Employee;
import web.chart.vo.Chart;

public interface ChartDao {

	// 查圓餅圖資料
	Chart getPie(String startDate, String endDate, Long deptId);

	// 遲到排行
	Chart getLateData(String startDate, String endDate);

	// 工時統計
	Chart getWorkingTime(String startDate, String endDate, Long empId);

	// 打卡紀錄
	Chart getCheckedStatus(String startDate, String endDate, Long empId);

	// 統計紀錄
	Chart getSummaryData(String startDate, String endDate, Long deptId);

	// 獲取部門清單
	List<Department> getDepartments();

	// 獲取員工清單
	List<Employee> getEmployees(Long deptId);

	// 下載 CSV
	List<Map<String, Object>> getAttendanceList(String startDate, String endDate, Long deptId, Long empId);

}