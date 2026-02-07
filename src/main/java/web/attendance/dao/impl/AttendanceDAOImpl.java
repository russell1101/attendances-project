package web.attendance.dao.impl;

import core.util.DBConnection;
import web.attendance.bean.AttendanceRecordVO;
import web.attendance.dao.AttendanceDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {

	@Override
	public boolean insert(AttendanceRecordVO record) {
		String sql = "INSERT INTO attendance_records (employee_id, work_date, clock_in_time, clock_in_status, points_awarded) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setLong(1, record.getEmployeeId());
			pstmt.setDate(2, record.getWorkDate());
			pstmt.setTime(3, record.getClockInTime());
			pstmt.setString(4, record.getClockInStatus());
			pstmt.setBigDecimal(5, record.getPointsAwarded());

			int rows = pstmt.executeUpdate();

			if (rows > 0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					record.setAttendanceId(rs.getLong(1));
				}
				rs.close();
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean update(AttendanceRecordVO record) {
		String sql = "UPDATE attendance_records SET clock_out_time = ?, clock_out_status = ?, points_awarded = ? " +
				"WHERE attendance_id = ?";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setTime(1, record.getClockOutTime());
			pstmt.setString(2, record.getClockOutStatus());
			pstmt.setBigDecimal(3, record.getPointsAwarded());
			pstmt.setLong(4, record.getAttendanceId());

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("[AttendanceDAO] 下班打卡成功 - Attendance ID: " + record.getAttendanceId());
				return true;
			}

		} catch (SQLException e) {
			System.err.println("[AttendanceDAO] 下班打卡失敗: " + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public AttendanceRecordVO findByEmployeeIdAndDate(Long employeeId, Date workDate) {
		String sql = "SELECT * FROM attendance_records WHERE employee_id = ? AND work_date = ?";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setLong(1, employeeId);
			pstmt.setDate(2, workDate);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return mapResultSetToVO(rs);
				}
			}

		} catch (SQLException e) {
			System.err.println("[AttendanceDAO] 查詢出勤紀錄失敗: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<AttendanceRecordVO> findByEmployeeId(Long employeeId) {
		List<AttendanceRecordVO> records = new ArrayList<>();
		String sql = "SELECT * FROM attendance_records WHERE employee_id = ? ORDER BY work_date DESC";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setLong(1, employeeId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					records.add(mapResultSetToVO(rs));
				}
			}

		} catch (SQLException e) {
			System.err.println("[AttendanceDAO] 查詢員工出勤紀錄失敗: " + e.getMessage());
			e.printStackTrace();
		}

		return records;
	}

	@Override
	public boolean deleteById(Long attendanceId) {
		String sql = "DELETE FROM attendance_records WHERE attendance_id = ?";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setLong(1, attendanceId);

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("[AttendanceDAO] 刪除出勤紀錄成功 - Attendance ID: " + attendanceId);
				return true;
			}

		} catch (SQLException e) {
			System.err.println("[AttendanceDAO] 刪除出勤紀錄失敗: " + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	// 將 ResultSet 轉換為 VO 物件
	private AttendanceRecordVO mapResultSetToVO(ResultSet rs) throws SQLException {
		AttendanceRecordVO record = new AttendanceRecordVO();
		record.setAttendanceId(rs.getLong("attendance_id"));
		record.setEmployeeId(rs.getLong("employee_id"));
		record.setWorkDate(rs.getDate("work_date"));
		record.setClockInTime(rs.getTime("clock_in_time"));
		record.setClockOutTime(rs.getTime("clock_out_time"));
		record.setClockInStatus(rs.getString("clock_in_status"));
		record.setClockOutStatus(rs.getString("clock_out_status"));
		record.setPointsAwarded(rs.getBigDecimal("points_awarded"));
		record.setCreatedAt(rs.getTimestamp("created_at"));
		record.setUpdatedAt(rs.getTimestamp("updated_at"));
		return record;
	}
}
