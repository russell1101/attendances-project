package web.chart.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import core.pojo.Department;
import core.pojo.Employee;
import web.chart.dao.ChartDao;
import web.chart.vo.Chart;

public class ChartDaoImpl implements ChartDao {
    private DataSource ds;

    public ChartDaoImpl() throws NamingException {
    		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/attendances");
    }

    // 圓餅圖
    @Override
    public Chart getPie(String startDate, String endDate, Integer deptId) {
        String sql = "SELECT "
                + "SUM(CASE WHEN ar.clock_in_status = 'ON_TIME' THEN 1 ELSE 0 END) AS onTime, "
                + "SUM(CASE WHEN ar.clock_in_status = 'LATE' THEN 1 ELSE 0 END) AS late, "
                + "SUM(CASE WHEN ar.clock_in_status = 'MISSING' THEN 1 ELSE 0 END) AS absent "
                + "FROM attendance_records ar "
                + "JOIN employees e ON ar.employee_id = e.employee_id "
                + "WHERE ar.work_date BETWEEN ? AND ?";

        if (deptId != null) {
            sql += " AND e.department_id = ?";
        }

        try (
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            if (deptId != null) {
                pstmt.setInt(3, deptId);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Chart chart = new Chart();
                    chart.setOnTime(rs.getInt("onTime"));
                    chart.setLate(rs.getInt("late"));
                    chart.setAbsent(rs.getInt("absent"));
                    return chart;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 遲到排行
    @Override
    public Chart getLateData(String startDate, String endDate) {
        String sql = "SELECT d.department_name, COUNT(*) AS lateCount "
                + "FROM attendance_records ar "
                + "JOIN employees e ON ar.employee_id = e.employee_id "
                + "JOIN departments d ON e.department_id = d.department_id "
                + "WHERE ar.clock_in_status = 'LATE' "
                + "AND ar.work_date BETWEEN ? AND ? "
                + "GROUP BY d.department_name "
                + "ORDER BY lateCount ASC";

        try (
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<String> names = new ArrayList<>();
                List<Integer> counts = new ArrayList<>();

                while (rs.next()) {
                    names.add(rs.getString("department_name"));
                    counts.add(rs.getInt("lateCount"));
                }

                Chart chart = new Chart();
                chart.setDeptName(names);
                chart.setLateCounts(counts);
                return chart;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 工時統計(長條圖)
    @Override
    public Chart getWorkingTime(String startDate, String endDate, Integer empId) {
        String sql = "SELECT work_date, "
                + "TIMESTAMPDIFF(HOUR, clock_in_time, clock_out_time) AS workHour "
                + "FROM attendance_records "
                + "WHERE employee_id = ? "
                + "AND work_date BETWEEN ? AND ? "
                + "AND clock_in_time IS NOT NULL "
                + "AND clock_out_time IS NOT NULL "
                + "ORDER BY work_date";

        try (
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, empId);
            pstmt.setString(2, startDate);
            pstmt.setString(3, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<String> dates = new ArrayList<>();
                List<Integer> hours = new ArrayList<>();

                while (rs.next()) {
                    dates.add(rs.getString("work_date"));
                    hours.add(rs.getInt("workHour"));
                }

                Chart chart = new Chart();
                chart.setWorkingDates(dates);
                chart.setWorkingHours(hours);
                return chart;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 散佈圖(打卡分布圖)
    @Override
    public Chart getCheckedStatus(String startDate, String endDate, Integer empId) {
        String sql = "SELECT clock_in_time, clock_out_time "
                + "FROM attendance_records "
                + "WHERE employee_id = ? "
                + "AND work_date BETWEEN ? AND ? "
                + "ORDER BY work_date";

        try (
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, empId);
            pstmt.setString(2, startDate);
            pstmt.setString(3, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<String> inTimes = new ArrayList<>();
                List<String> outTimes = new ArrayList<>();

                while (rs.next()) {
                    String inTime = rs.getString("clock_in_time");
                    String outTime = rs.getString("clock_out_time");

                    if (inTime != null) {
                        inTimes.add(inTime);
                    }
                    if (outTime != null) {
                        outTimes.add(outTime);
                    }
                }

                Chart chart = new Chart();
                chart.setCheckInTimes(inTimes);
                chart.setCheckOutTimes(outTimes);
                return chart;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 統計摘要
    @Override
    public Chart getSummaryData(String startDate, String endDate, Integer deptId) {
        String sql = "SELECT "
                + "SUM(CASE WHEN ar.clock_in_status = 'LATE' THEN 1 ELSE 0 END) AS totalLate, "
                + "COUNT(*) AS total, "
                + "SUM(CASE WHEN ar.clock_in_status != 'MISSING' THEN 1 ELSE 0 END) AS attended, "
                + "SUM(CASE WHEN ar.clock_in_status = 'MISSING' THEN 1 ELSE 0 END) AS noChecked "
                + "FROM attendance_records ar "
                + "JOIN employees e ON ar.employee_id = e.employee_id "
                + "WHERE ar.work_date BETWEEN ? AND ?";

        if (deptId != null) {
            sql += " AND e.department_id = ?";
        }

        try (
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            if (deptId != null) {
                pstmt.setInt(3, deptId);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Chart chart = new Chart();
                    chart.setTotalLateCounts(rs.getInt("totalLate"));
                    chart.setNoChecked(rs.getInt("noChecked"));

                    int total = rs.getInt("total");
                    int attended = rs.getInt("attended");
                    if (total > 0) {
                        chart.setAttendRate(attended * 100.0 / total);
                    } else {
                        chart.setAttendRate(0);
                    }
                    return chart;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // 部門清單
    @Override
    public List<Department> getDepartments() {
        String sql = "SELECT department_id, department_name FROM departments";
        List<Department> list = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Department dept = new Department();
                dept.setDepartmentId(rs.getLong("department_id"));
                dept.setDepartmentName(rs.getString("department_name"));
                list.add(dept);
            }
            return list;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return null;
    }

    // 員工清單
    @Override
    public List<Employee> getEmployees(Integer deptId) {
        String sql = "SELECT employee_id, name FROM employees";
        if (deptId != null) {
            sql += " WHERE department_id = ?";
        }
        
        List<Employee> list = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (deptId != null) {
                pstmt.setInt(1, deptId);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setEmployeeId(rs.getLong("employee_id"));
                    emp.setName(rs.getString("name"));
                    list.add(emp);
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return list;
    }
    
    // 下載CSV
    @Override
    public List<Map<String, Object>> getAttendanceList(String startDate, String endDate, Integer deptId, Integer empId) {

        String sql = "SELECT e.name, d.department_name, ar.work_date, ar.clock_in_time, ar.clock_out_time, ar.clock_in_status " +
                     "FROM attendance_records ar " +
                     "JOIN employees e ON ar.employee_id = e.employee_id " +
                     "JOIN departments d ON e.department_id = d.department_id " +
                     "WHERE ar.work_date BETWEEN ? AND ? ";

        if (deptId != null) {
            sql += " AND e.department_id = ? ";
        }
        if (empId != null) {
            sql += " AND e.employee_id = ? ";
        }
        
        sql += " ORDER BY ar.work_date DESC ";

        List<Map<String, Object>> list = new ArrayList<>();
        
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            
            int paramIndex = 3;
            if (deptId != null) {
                pstmt.setInt(paramIndex, deptId);
                paramIndex++;
            }
            if (empId != null) {
                pstmt.setInt(paramIndex, empId);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", rs.getString("name"));
                    map.put("deptName", rs.getString("department_name"));
                    map.put("workDate", rs.getString("work_date"));
                    map.put("clockIn", rs.getString("clock_in_time"));
                    map.put("clockOut", rs.getString("clock_out_time"));
                    map.put("status", rs.getString("clock_in_status"));
                    list.add(map);
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}