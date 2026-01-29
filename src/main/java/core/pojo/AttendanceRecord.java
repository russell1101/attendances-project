package core.pojo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class AttendanceRecord {
	private Long attendanceId;
	private Long employeeId;
	private Date workDate;
	private Time clockInTime;
	private Time clockOutTime;
	private String clockInStatus;
	private String clockOutStatus;
	private BigDecimal pointsAwarded;
	private Timestamp createdAt;
	private Timestamp updatedAt;

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
