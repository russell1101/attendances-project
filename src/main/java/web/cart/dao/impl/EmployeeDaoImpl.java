package web.cart.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import core.pojo.Employee;
import core.util.DBConnection;
import web.cart.dao.EmployeeDao;

public class EmployeeDaoImpl implements EmployeeDao {

	// 查找員工id
	@Override
	public Employee selectById(Long employeeId) {
		Employee employee = null;
		try (Connection conn = DBConnection.getConnection()) {
			employee = selectById(conn, employeeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

	// 查找員工id 傳入connection版
	@Override
	public Employee selectById(Connection conn, Long employeeId) {
		String sql = "SELECT * FROM employees WHERE employee_id = ?";
		Employee employee = null;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, employeeId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					employee = new Employee();
					employee.setEmployeeId(rs.getLong("employee_id"));
					employee.setName(rs.getString("name"));
					employee.setEmail(rs.getString("email"));
					employee.setPasswordHash(rs.getString("password_hash"));
					employee.setGoogleSub(rs.getString("google_sub"));
					employee.setHireDate(rs.getDate("hire_date"));
					employee.setCurrentPoints(rs.getBigDecimal("current_points"));
					employee.setDepartmentId(rs.getLong("department_id"));
					employee.setEmployeeStatusId(rs.getLong("employee_status_id"));
					employee.setIsActive(rs.getBoolean("is_active"));
					employee.setCreatedAt(rs.getTimestamp("created_at"));
					employee.setUpdatedAt(rs.getTimestamp("updated_at"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

	// 扣點
	@Override
	public int deductPoints(Connection conn, Long employeeId, BigDecimal cost) {
		String sql = "UPDATE employees SET current_points = current_points - ? WHERE employee_id = ? AND current_points >= ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setBigDecimal(1, cost);
			pstmt.setLong(2, employeeId);
			pstmt.setBigDecimal(3, cost);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}