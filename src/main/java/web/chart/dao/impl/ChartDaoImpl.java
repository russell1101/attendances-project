package web.chart.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.chart.bean.Chart;
import web.chart.dao.ChartDao;

public class ChartDaoImpl implements ChartDao {
	private DataSource ds;
	
	public ChartDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/evn/jdbc/railway");
	}
	
	//圓餅圖
	@Override
	public Chart getPie(String startDate, String endDate, String deptId) {
		String sql = "SELECT "
	            + "SUM(CASE WHEN ar.clock_in_status = 'ON_TIME' THEN 1 ELSE 0 END) AS onTime, "
	            + "SUM(CASE WHEN ar.clock_in_status = 'LATE' THEN 1 ELSE 0 END) AS late, "
	            + "SUM(CASE WHEN ar.clock_in_status = 'MISSING' THEN 1 ELSE 0 END) AS absent "
	            + "FROM attendance_records ar "
	            + "JOIN employees e ON ar.employee_id = e.employee_id "
	            + "WHERE ar.work_date BETWEEN ? AND ?";
		
		if(deptId !=null && !deptId.isEmpty()) {
			sql +=" AND e.department_id = ?";
		}
		try(
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)
		){
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);
			if(deptId !=null && !deptId.isEmpty()) {
				pstmt.setString(3, deptId);
			}
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					Chart chart = new Chart();
					chart.setOnTime(rs.getInt("onTime"));
					chart.setLate(rs.getInt("late"));
					chart.setAbsent(rs.getInt("absent"));
					return chart;
					
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Chart();
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
		
		List<String> names = new ArrayList<String>();
		List<Integer> counts = new ArrayList<Integer>();
		
		try(
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)
		){
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);
				
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					names.add(rs.getString("department_name"));
					counts.add(rs.getInt("lateCount"));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		Chart chart = new Chart();
        chart.setDeptName(names);
        chart.setLateCounts(counts);
        return chart;
	}
	
	// 工時統計(長條圖)
	@Override
	public Chart getWorkingTime(String startDate, String endDate, String empId) {
		String sql = "Select work_date, "
				+ "TIMESTAMPDIFF(HOUR, clock_in_time, clock_out_time) AS workHour "
				+ "FROM attendance_records "
				+ "WHERE employee_id = ? "
				+ "AND work_date BETWEEN ? AND ? "
				+ "AND clock_in_time IS NOT NULL "
				+ "AND clock_out_time IS NOT NULL "
				+ "ORDER BY work_date";
		List<String> dates = new ArrayList<String>();
		List<Integer> hours = new ArrayList<Integer>();
		
		try(
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)					
		){
			pstmt.setString(1, empId);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);
			
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					dates.add(rs.getString("work_date"));
					hours.add(rs.getInt("workHour"));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		Chart chart = new Chart();
		chart.setWorkingDates(dates);
		chart.setWorkingHours(hours);
		
		return chart;
	}
	
	// 發散圖(打卡分布圖)
	@Override
	public Chart getCheckedStatus(String startDate, String endDate, String empId) {
		String sql = "SELECT clock_in_time, clock_on_time "
					+ "FROM attendeance_records "
					+ "WHERE emploee_id = ? "
					+ "AND work_date BETWEEN ? AND ?"
					+ "ORDER BY work_date";
		
		List<String> inTimes = new ArrayList<String>();
		List<String> outTimes = new ArrayList<String>();
		
		try (
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)
		){
			pstmt.setString(1, empId);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);
			
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					String inTime = rs.getString("clock_in_time");
					String outTime = rs.getString("clock_out_time");
					
					 if (inTime != null) {
		                 inTimes.add(inTime);
		             }
		             if (outTime != null) {
		                 outTimes.add(outTime);
		             }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Chart chart = new Chart();
		chart.setCheckInTimes(inTimes);
		chart.setCheckOutTimes(outTimes);
		return chart;
	}
	
	// 統計摘要
	@Override
	public Chart getSummaryData(String startDate, String endDate, String deptId) {
		String sql = "SELECT "
	            + "SUM(CASE WHEN ar.clock_in_status = 'LATE' THEN 1 ELSE 0 END) AS totalLate, "
	            + "COUNT(*) AS total, "
	            + "SUM(CASE WHEN ar.clock_in_status != 'MISSING' THEN 1 ELSE 0 END) AS attended, "
	            + "SUM(CASE WHEN ar.clock_in_status = 'MISSING' THEN 1 ELSE 0 END) AS noChecked "
	            + "FROM attendance_records ar "
	            + "JOIN employees e ON ar.employee_id = e.employee_id "
	            + "WHERE ar.work_date BETWEEN ? AND ?";
		if(deptId != null && !deptId.isEmpty()) {
			sql+=" AND e.department_id = ?";
		}
		
		try(
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)
		){
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);
			
			if(deptId !=null && !deptId.isEmpty()) {
				pstmt.setString(3, deptId);
			}
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					Chart chart = new Chart();
					chart.setTotalLateCounts(rs.getInt("totalLate"));
					chart.setNoChecked(rs.getInt("noChecked"));
					
					int total = rs.getInt("total");
					int attended = rs.getInt("attended");
					if(total > 0) {
						chart.setAttendRate(attended *100.0 / total);
					}else {
						chart.setAttendRate(0);
					}
					return chart;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Chart();
	}
	
}
