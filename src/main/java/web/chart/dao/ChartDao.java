package web.chart.dao;

import java.util.List;
import java.util.Map;

import core.pojo.Department;
import core.pojo.Employee;
import web.chart.vo.Chart;

public interface ChartDao {
		// 查圓餅圖資料
		Chart getPie(String startDate, String endDate, Integer deptId);
		
		// 遲到排行
		Chart getLateData(String startDate, String endDate);
		
		// 工時統計
		Chart getWorkingTime(String startDate, String endDate, Integer empId);
		
		// 打卡紀錄
		Chart getCheckedStatus(String startDate, String endDate, Integer empId);
		
		// 統計紀錄
		Chart getSummaryData(String startDate, String endDate, Integer deptId);
		
		// 獲取部門清單
	    List<Department> getDepartments();
	    
	    // 獲取員工清單
	    List<Employee> getEmployees(Integer deptId);
	    
	    // 下載 CSV 
	    List<Map<String, Object>> getAttendanceList(String startDate, String endDate, Integer deptId, Integer empId);
}
