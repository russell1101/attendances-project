package web.chart.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;

import core.entity.Department;
import core.entity.Employee;
import web.chart.dao.ChartDao;
import web.chart.vo.Chart;

public class ChartDaoImpl implements ChartDao {
	
    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

	// 部門資訊
    @Override
    public List<Department> getDepartments() {
        String hql = "FROM Department";
        return session.createQuery(hql, Department.class).getResultList();
    }

	// 員工資訊
    @Override
    public List<Employee> getEmployees(Integer deptId) {
        if (deptId != null) {
            String hql = "FROM Employee e WHERE e.department.departmentId = :deptId";
            return session.createQuery(hql, Employee.class)
                          .setParameter("deptId", deptId)
                          .getResultList();
        } else {
            String hql = "FROM Employee";
            return session.createQuery(hql, Employee.class).getResultList();
        }
    }

	// 圓餅圖
	@Override
	public Chart getPie(String startDate, String endDate, Integer deptId) {
			StringBuilder hql = new StringBuilder();
			hql.append("SELECT ");
			hql.append("  SUM(CASE WHEN ar.clockInStatus = 'ON_TIME' THEN 1 ELSE 0 END), ");
			hql.append("  SUM(CASE WHEN ar.clockInStatus = 'LATE' THEN 1 ELSE 0 END), ");
			hql.append("  SUM(CASE WHEN ar.clockInStatus = 'MISSING' THEN 1 ELSE 0 END) ");
			hql.append("FROM AttendanceRecord ar ");
			hql.append("WHERE ar.workDate BETWEEN :startDate AND :endDate ");

			if (deptId != null) {
				hql.append("AND ar.employee.department.departmentId = :deptId ");
			}

			Query<Object[]> query = getSession().createQuery(hql.toString(), Object[].class);
			query.setParameter("startDate", Date.valueOf(startDate));
			query.setParameter("endDate", Date.valueOf(endDate));

			if (deptId != null) {
				query.setParameter("deptId", deptId);
			}

			Object[] result = query.getSingleResult();

			Chart chart = new Chart();
			chart.setOnTime(result[0] != null ? ((Number) result[0]).intValue() : 0);
			chart.setLate(result[1] != null ? ((Number) result[1]).intValue() : 0);
			chart.setAbsent(result[2] != null ? ((Number) result[2]).intValue() : 0);

			return chart;
	}

	// 橫向長條
	@Override
	public Chart getLateData(String startDate, String endDate) {
			String hql = "SELECT ar.employee.department.departmentName, COUNT(ar) " + "FROM AttendanceRecord ar "
					+ "WHERE ar.clockInStatus = 'LATE' " + "AND ar.workDate BETWEEN :startDate AND :endDate "
					+ "GROUP BY ar.employee.department.departmentName " + "ORDER BY COUNT(ar) ASC";

			Query<Object[]> query = getSession().createQuery(hql.toString(), Object[].class);
			query.setParameter("startDate", Date.valueOf(startDate));
			query.setParameter("endDate", Date.valueOf(endDate));

			List<Object[]> results = query.getResultList();

			List<String> names = new ArrayList<>();
			List<Integer> counts = new ArrayList<>();

			for (Object[] row : results) {
				names.add((String) row[0]);
				counts.add(((Number) row[1]).intValue());
			}

			Chart chart = new Chart();
			chart.setDeptName(names);
			chart.setLateCounts(counts);

			return chart;
	}

	// 長條圖
	@Override
	public Chart getWorkingTime(String startDate, String endDate, Integer empId) {
			String sql = "SELECT work_date, " 
					+ "TIMESTAMPDIFF(HOUR, clock_in_time, clock_out_time) AS workHour "
					+ "FROM attendance_records " 
					+ "WHERE employee_id = :empId "
					+ "AND work_date BETWEEN :startDate AND :endDate " 
					+ "AND clock_in_time IS NOT NULL "
					+ "AND clock_out_time IS NOT NULL " 
					+ "ORDER BY work_date";

			List<Object[]> results = getSession().createNativeQuery(sql)
					.setParameter("empId", empId)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate).getResultList();

			List<String> dates = new ArrayList<>();
			List<Integer> hours = new ArrayList<>();

			for (Object[] row : results) {
				dates.add(row[0].toString());
				hours.add(row[1] != null ? ((Number) row[1]).intValue() : 0);
			}

			Chart chart = new Chart();
			chart.setWorkingDates(dates);
			chart.setWorkingHours(hours);

			return chart;
	}

	// 散佈圖
	@Override
	public Chart getCheckedStatus(String startDate, String endDate, Integer empId) {
			String hql = "SELECT ar.clockInTime, ar.clockOutTime " + "FROM AttendanceRecord ar "
					+ "WHERE ar.employee.employeeId = :empId " + "AND ar.workDate BETWEEN :startDate AND :endDate "
					+ "ORDER BY ar.workDate";

			Query<Object[]> query = getSession().createQuery(hql, Object[].class);
			query.setParameter("empId", empId);
			query.setParameter("startDate", Date.valueOf(startDate));
			query.setParameter("endDate", Date.valueOf(endDate));

			List<Object[]> results = query.getResultList();

			List<String> inTimes = new ArrayList<>();
			List<String> outTimes = new ArrayList<>();

			for (Object[] row : results) {
				if (row[0] != null)
					inTimes.add(row[0].toString());
				if (row[1] != null)
					outTimes.add(row[1].toString());
			}

			Chart chart = new Chart();
			chart.setCheckInTimes(inTimes);
			chart.setCheckOutTimes(outTimes);

			return chart;
	}

	// 統計數據
	@Override
	public Chart getSummaryData(String startDate, String endDate, Integer deptId) {
			StringBuilder hql = new StringBuilder();
			hql.append("SELECT ");
			hql.append("  SUM(CASE WHEN ar.clockInStatus = 'LATE' THEN 1 ELSE 0 END), ");
			hql.append("  COUNT(ar), ");
			hql.append("  SUM(CASE WHEN ar.clockInStatus <> 'MISSING' THEN 1 ELSE 0 END), ");
			hql.append("  SUM(CASE WHEN ar.clockInStatus = 'MISSING' THEN 1 ELSE 0 END) ");
			hql.append("FROM AttendanceRecord ar ");
			hql.append("WHERE ar.workDate BETWEEN :startDate AND :endDate ");

			if (deptId != null) {
				hql.append("AND ar.employee.department.departmentId = :deptId ");
			}

			Query<Object[]> query = getSession().createQuery(hql.toString(), Object[].class);
			query.setParameter("startDate", Date.valueOf(startDate));
			query.setParameter("endDate", Date.valueOf(endDate));

			if (deptId != null) {
				query.setParameter("deptId", deptId);
			}

			Object[] result = query.getSingleResult();

			Chart chart = new Chart();
			int totalLate = result[0] != null ? ((Number) result[0]).intValue() : 0;
			int total = result[1] != null ? ((Number) result[1]).intValue() : 0;
			int attended = result[2] != null ? ((Number) result[2]).intValue() : 0;
			int noChecked = result[3] != null ? ((Number) result[3]).intValue() : 0;

			chart.setTotalLateCounts(totalLate);
			chart.setNoChecked(noChecked);
			chart.setAttendRate(total > 0 ? attended * 100.0 / total : 0);

			return chart;
	}

	// 下載CSV
	@Override
	public List<Map<String, Object>> getAttendanceList(String startDate, String endDate, Integer deptId,
			Integer empId) {
			StringBuilder hql = new StringBuilder();
			hql.append("SELECT ar.employee.name, ");
			hql.append("       ar.employee.department.departmentName, ");
			hql.append("       ar.workDate, ");
			hql.append("       ar.clockInTime, ");
			hql.append("       ar.clockOutTime, ");
			hql.append("       ar.clockInStatus ");
			hql.append("FROM AttendanceRecord ar ");
			hql.append("WHERE ar.workDate BETWEEN :startDate AND :endDate ");

			if (deptId != null) {
				hql.append("AND ar.employee.department.departmentId = :deptId ");
			}
			if (empId != null) {
				hql.append("AND ar.employee.employeeId = :empId ");
			}
			hql.append("ORDER BY ar.workDate DESC");

			Query<Object[]> query = getSession().createQuery(hql.toString(), Object[].class);
			query.setParameter("startDate", Date.valueOf(startDate));
			query.setParameter("endDate", Date.valueOf(endDate));

			if (deptId != null)
				query.setParameter("deptId", deptId);
			if (empId != null)
				query.setParameter("empId", empId);

			List<Object[]> results = query.getResultList();
			List<Map<String, Object>> list = new ArrayList<>();

			for (Object[] row : results) {
				Map<String, Object> map = new HashMap<>();
				map.put("name", row[0]);
				map.put("deptName", row[1]);
				map.put("workDate", row[2] != null ? row[2].toString() : null);
				map.put("clockIn", row[3] != null ? row[3].toString() : null);
				map.put("clockOut", row[4] != null ? row[4].toString() : null);
				map.put("status", row[5]);
				list.add(map);
			}

			return list;
	}
}