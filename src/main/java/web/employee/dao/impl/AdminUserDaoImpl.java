package web.employee.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import core.pojo.AdminUser;
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
}
