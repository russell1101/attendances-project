package web.employee.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import web.employee.dao.EmployeeDao;
import web.employee.vo.Employee;

public class EmployeeDaoImpl implements EmployeeDao{
	private DataSource ds;

	public EmployeeDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/project_db");
	}

	@Override
	public Employee selectByAccount(String account) {
		String sql = "select * from employee where account = ?";
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, account);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Employee employee = new Employee();
					employee.setEmployeeId(rs.getInt("employee_id"));
					employee.setName(rs.getString("name"));
					employee.setAccount(rs.getString("account"));
					employee.setPasswordHash(rs.getString("password_hash"));
					employee.setHireDate(rs.getDate("hire_date"));
					employee.setCurrentPoints(rs.getInt("current_points"));
					employee.setDepartmentId(rs.getInt("department_id"));
					employee.setEmployeeStatusId(rs.getInt("employee_status_id"));
					employee.setIsActive(rs.getBoolean("is_active"));
					employee.setCreatedAt(rs.getTimestamp("created_at"));
					employee.setUpdatedAt(rs.getTimestamp("updated_at"));
					return employee;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
