package web.chart.service.impl;

import java.util.List;
import java.util.Map;

import core.entity.Department;
import core.entity.Employee;
import web.chart.dao.ChartDao;
import web.chart.dao.impl.ChartDaoImpl;
import web.chart.service.ChartService;
import web.chart.vo.Chart;

public class ChartServiceImpl implements ChartService {
    
    private ChartDao dao = new ChartDaoImpl();

    @Override
    public List<Department> getDeptOptions() {
        return doInTransaction(session -> {
            dao.setSession(session); 
            return dao.getDepartments();
        });
    }

    @Override
    public List<Employee> getEmpOptions(Integer deptId) {
        return doInTransaction(session -> {
            dao.setSession(session);
            return dao.getEmployees(deptId);
        });
    }

    @Override
    public Chart getChartAllData(String startDate, String endDate, Integer deptId, Integer empId) {
        return doInTransaction(session -> {
            dao.setSession(session);  
            
            Chart chart = new Chart();

            Chart pie = dao.getPie(startDate, endDate, deptId);
            if (pie != null) {
                chart.setOnTime(pie.getOnTime());
                chart.setLate(pie.getLate());
                chart.setAbsent(pie.getAbsent());
            }

            Chart rowBar = dao.getLateData(startDate, endDate);
            if (rowBar != null) {
                chart.setDeptName(rowBar.getDeptName());
                chart.setLateCounts(rowBar.getLateCounts());
            }

            if (empId != null) {
                Chart bar = dao.getWorkingTime(startDate, endDate, empId);
                if (bar != null) {
                    chart.setWorkingDates(bar.getWorkingDates());
                    chart.setWorkingHours(bar.getWorkingHours());
                }

                Chart scatter = dao.getCheckedStatus(startDate, endDate, empId);
                if (scatter != null) {
                    chart.setCheckInTimes(scatter.getCheckInTimes());
                    chart.setCheckOutTimes(scatter.getCheckOutTimes());
                }
            }

            Chart summary = dao.getSummaryData(startDate, endDate, deptId);
            if (summary != null) {
                chart.setTotalLateCounts(summary.getTotalLateCounts());
                chart.setAttendRate(summary.getAttendRate());
                chart.setNoChecked(summary.getNoChecked());
            }

            return chart;
        });
    }

    @Override
    public String getCsvString(String startDate, String endDate, Integer deptId, Integer empId) {
        List<Map<String, Object>> data = doInTransaction(session -> {
            dao.setSession(session);
            return dao.getAttendanceList(startDate, endDate, deptId, empId);
        });

        StringBuilder sb = new StringBuilder();
        sb.append('\ufeff');
        sb.append("姓名,部門,日期,上班打卡,下班打卡,狀態\n");

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