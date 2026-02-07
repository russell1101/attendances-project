package web.attendance.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * 出勤紀錄 VO (Value Object)
 * 對應資料表 attendance_records
 */
public class AttendanceRecordVO {

	private Long attendanceId;
	private Long employeeId;
	private Date workDate;
	private Time clockInTime;
	private Time clockOutTime;
	private String clockInStatus; // ON_TIME, LATE, MISSING
	private String clockOutStatus; // NORMAL, EARLY_LEAVE, MISSING
	private BigDecimal pointsAwarded;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	// 建構子
	public AttendanceRecordVO() {
	}

	// Getters and Setters
	public Long getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(Long attendanceId) {
		this.attendanceId = attendanceId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public Time getClockInTime() {
		return clockInTime;
	}

	public void setClockInTime(Time clockInTime) {
		this.clockInTime = clockInTime;
	}

	public Time getClockOutTime() {
		return clockOutTime;
	}

	public void setClockOutTime(Time clockOutTime) {
		this.clockOutTime = clockOutTime;
	}

	public String getClockInStatus() {
		return clockInStatus;
	}

	public void setClockInStatus(String clockInStatus) {
		this.clockInStatus = clockInStatus;
	}

	public String getClockOutStatus() {
		return clockOutStatus;
	}

	public void setClockOutStatus(String clockOutStatus) {
		this.clockOutStatus = clockOutStatus;
	}

	public BigDecimal getPointsAwarded() {
		return pointsAwarded;
	}

	public void setPointsAwarded(BigDecimal pointsAwarded) {
		this.pointsAwarded = pointsAwarded;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
