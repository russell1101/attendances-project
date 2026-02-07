package web.employee.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import core.pojo.AdminUser;
import core.pojo.Employee;
import core.util.DBConnection;
import web.employee.dao.AdminUserDao;

public class AdminUserDaoImpl implements AdminUserDao {

	@Override
	public AdminUser selectByUsername(String username) {
		String sql = "select * from admin_users where username = ?";
		try (Connection conn = DBConnection.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, username);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					AdminUser adminUser = new AdminUser();
					adminUser.setAdminUserId(rs.getLong("admin_user_id"));
					adminUser.setUsername(rs.getString("username"));
					adminUser.setPasswordHash(rs.getString("password_hash"));
					adminUser.setDisplayName(rs.getString("display_name"));
					adminUser.setEmployeeId(rs.getLong("employee_id"));
					adminUser.setIsActive(rs.getBoolean("is_active"));
					adminUser.setCreatedAt(rs.getTimestamp("created_at"));
					adminUser.setUpdatedAt(rs.getTimestamp("updated_at"));
					return adminUser;
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
				emp.setPasswordHash(rs.getString("google_sub"));
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
