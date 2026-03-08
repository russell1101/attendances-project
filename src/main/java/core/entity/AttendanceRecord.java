package core.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import core.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attendance_records")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "attendance_id")
	private Long attendanceId;
	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	@Column(name = "work_date")
	private Date workDate;
	
	@Column(name = "clock_in_time")
	private Time clockInTime;
	
	@Column(name = "clock_out_time")
	private Time clockOutTime;
	
	@Column(name = "clock_in_status")
	private String clockInStatus;
	
	@Column(name = "clock_out_status")
	private String clockOutStatus;
	
	@Column(name = "points_awarded", insertable = false, updatable = false)
	private BigDecimal pointsAwarded;
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;
	
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
	
	
	
}
