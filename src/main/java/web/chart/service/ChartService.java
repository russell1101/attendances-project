package web.chart.service;

import java.util.List;

import core.entity.Department;
import core.entity.Employee;
import core.service.CoreService;
import web.chart.vo.Chart;

public interface ChartService extends CoreService {
    // 圖表資料
	Chart getChartAllData(String startDate, String endDate, Integer deptId, Integer empId);
    
    // 下拉選單：部門
	List<Department> getDeptOptions(); 
    
    // 下拉選單：員工
	List<Employee> getEmpOptions(Integer deptId);
    
    // 下載CSV 
	String getCsvString(String startDate, String endDate, Integer deptId, Integer empId);
}