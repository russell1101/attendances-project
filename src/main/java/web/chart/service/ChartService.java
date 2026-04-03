package web.chart.service;

import java.util.List;

import core.entity.Department;
import core.entity.Employee;
import web.chart.vo.Chart;

public interface ChartService {
    // 圖表資料
	Chart getChartAllData(String startDate, String endDate, Long deptId, Long empId);
    
    // 下拉選單：部門
	List<Department> getDeptOptions(); 
    
    // 下拉選單：員工
	List<Employee> getEmpOptions(Long deptId);
    
    // 下載CSV 
	String getCsvString(String startDate, String endDate, Long deptId, Long empId);
}