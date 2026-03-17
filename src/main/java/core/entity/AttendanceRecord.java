package core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attendance_records", uniqueConstraints = {
		@UniqueConstraint(name = "uk_attendance_employee_date", columnNames = { "employee_id", "work_date" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "attendance_id")
	private Long attendanceId;

	@Column(name = "employee_id", nullable = false)
	private Long employeeId;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Column(name = "work_date", nullable = false)
	private Date workDate;

	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	@Column(name = "clock_in_time")
	private Time clockInTime;

	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	@Column(name = "clock_out_time")
	private Time clockOutTime;

	@Column(name = "clock_in_status")
	private String clockInStatus;

	@Column(name = "clock_out_status")
	private String clockOutStatus;

	@Column(name = "points_awarded", nullable = false)
	private BigDecimal pointsAwarded;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;

}