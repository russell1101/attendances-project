package web.cart.dao.impl;

import web.cart.dao.AttendanceDAO;
import core.pojo.AttendanceRecord;
import core.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

/**
 * 出勤紀錄 DAO 實作類別
 */
public class AttendanceDAOImpl implements AttendanceDAO {

    @Override
    public void insert(AttendanceRecord record) {
        String sql = "INSERT INTO attendance_records (employee_id, work_date, clock_in_time, " +
                "clock_in_status, points_awarded) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, record.getEmployeeId());
            pstmt.setDate(2, record.getWorkDate());
            pstmt.setTime(3, record.getClockInTime());
            pstmt.setString(4, record.getClockInStatus());
            pstmt.setBigDecimal(5, record.getPointsAwarded());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(AttendanceRecord record) {
        String sql = "UPDATE attendance_records SET clock_out_time = ?, clock_out_status = ?, " +
                "points_awarded = ? WHERE attendance_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTime(1, record.getClockOutTime());
            pstmt.setString(2, record.getClockOutStatus());
            pstmt.setBigDecimal(3, record.getPointsAwarded());
            pstmt.setLong(4, record.getAttendanceId());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AttendanceRecord> findByEmployeeId(Long employeeId) {
        String sql = "SELECT * FROM attendance_records WHERE employee_id = ? ORDER BY work_date DESC";
        List<AttendanceRecord> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, employeeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                AttendanceRecord record = new AttendanceRecord();
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

                list.add(record);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public AttendanceRecord findByEmployeeIdAndDate(Long employeeId, Date workDate) {
        String sql = "SELECT * FROM attendance_records WHERE employee_id = ? AND work_date = ?";
        AttendanceRecord record = null;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, employeeId);
            pstmt.setDate(2, workDate);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                record = new AttendanceRecord();
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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return record;
    }
}
