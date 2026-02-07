package web.attendance.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

// 出勤紀錄的 bean
// 對應資料庫的 attendance_records 表
public class AttendanceRecordVO {

	private Long attendanceId;
	private Long employeeId;
	private Date workDate;
	private Time clockInTime;
	private Time clockOutTime;
	private String clockInStatus; // 上班狀態: ON_TIME, LATE, MISSING
	private String clockOutStatus; // 下班狀態: NORMAL, EARLY_LEAVE, MISSING
	private BigDecimal pointsAwarded; // 獎勵積分
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public AttendanceRecordVO() {
	}

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

	// 上班時間
	public Time getClockInTime() {
		return clockInTime;
	}

	public void setClockInTime(Time clockInTime) {
		this.clockInTime = clockInTime;
	}

	// 下班時間
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
