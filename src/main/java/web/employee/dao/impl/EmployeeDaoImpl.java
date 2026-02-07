package web.employee.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.pojo.Employee;
import core.util.DBConnection;
import web.employee.dao.EmployeeDao;


public class EmployeeDaoImpl implements EmployeeDao{

	@Override
	public Employee selectByEmail(String email) {
		String sql = "select * from employees where email = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Employee emp = new Employee();
					emp.setEmployeeId(rs.getLong("employee_id"));
					emp.setName(rs.getString("name"));
					emp.setEmail(rs.getString("email"));
					emp.setPasswordHash(rs.getString("password_hash"));
					emp.setGoogleSub(rs.getString("google_sub"));
					emp.setHireDate(rs.getDate("hire_date"));
					emp.setCurrentPoints(rs.getBigDecimal("current_points"));
					emp.setDepartmentId(rs.getLong("department_id"));
					emp.setEmployeeStatusId(rs.getLong("employee_status_id"));
					emp.setIsActive(rs.getBoolean("is_active"));
					emp.setCreatedAt(rs.getTimestamp("created_at"));
					emp.setUpdatedAt(rs.getTimestamp("updated_at"));
					return emp;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Employee> selectAll() {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement("select * from employees;");
				ResultSet rs = preparedStatement.executeQuery();) {
			List<Employee> list = new ArrayList<Employee>();
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEmployeeId(rs.getLong("employee_id"));
				emp.setName(rs.getString("name"));
				emp.setEmail(rs.getString("email"));
				emp.setPasswordHash(rs.getString("password_hash"));
				emp.setGoogleSub(rs.getString("google_sub"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setCurrentPoints(rs.getBigDecimal("current_points"));
				emp.setDepartmentId(rs.getLong("department_id"));
				emp.setEmployeeStatusId(rs.getLong("employee_status_id"));
				emp.setIsActive(rs.getBoolean("is_active"));
				emp.setCreatedAt(rs.getTimestamp("created_at"));
				emp.setUpdatedAt(rs.getTimestamp("updated_at"));
				list.add(emp);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
